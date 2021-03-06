package com.park.h5business.service.cardpay.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.park.h5business.config.PayMethodConfig;
import com.park.h5business.config.PayStatusConfig;
import com.park.h5business.dao.OrderInfoDao;
import com.park.h5business.dao.OrderPayRecordDao;
import com.park.h5business.dao.ParkDao;
import com.park.h5business.domain.OrderPayRecord;
import com.park.h5business.profile.URLProfile;
import com.park.h5business.request.UnifiedorderRequest;
import com.park.h5business.response.Response;
import com.park.h5business.response.CardPayResponse;
import com.park.h5business.tools.DateChangeTools;
import com.park.h5business.tools.DateTools;
import com.park.h5business.tools.HttpJsonTools;
import com.park.h5business.tools.MoneyTool;
import com.park.h5business.tools.SignTools;

/**
 * 
 * @author WangYuefei
 *
 */
public class CardPayThread implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private float unpayPrice;
	private String parkid;
	private String order_num_pay;
	private String trade_type; //两个值：WEIXIN ZHIFUBAO
	private String auth_code;
	private String ipAddress;
	
	private OrderInfoDao orderInfoDao;
	private OrderPayRecordDao orderPayRecordDao;
    private ParkDao parkDao;
    public void setOrderInfoDao(OrderInfoDao orderInfoDao) {
		this.orderInfoDao = orderInfoDao;
	}
    public void setOrderPayRecordDao(OrderPayRecordDao orderPayRecordDao) {
		this.orderPayRecordDao = orderPayRecordDao;
	}
	public void setParkDao(ParkDao parkDao) {
		this.parkDao = parkDao;
	}
	URLProfile urlProfile;
	public void setUrlProfile(URLProfile urlProfile) {
		this.urlProfile = urlProfile;
	}

	public CardPayThread(float unpayPrice, String parkid, String order_num_pay, String trade_type, String auth_code, String ipAddress) {
		super();
		this.unpayPrice = unpayPrice;
		this.order_num_pay = order_num_pay;
		this.parkid = parkid;
		this.trade_type = trade_type;
		this.auth_code = auth_code;
		this.ipAddress = ipAddress;
	}

	@Override
	public void run() {
		UnifiedorderRequest requestInfo = getRequestInfo();
		microPay(requestInfo);
	}
	
	/**
	 * 整理请求参数
	 * @return
	 */
	public UnifiedorderRequest getRequestInfo(){
		UnifiedorderRequest requestInfo = new UnifiedorderRequest();
		requestInfo.setBody("PARKFEE");
		requestInfo.setOut_trade_no(order_num_pay);
		requestInfo.setParkid(parkid);
		requestInfo.setTerminal_type("CDP");
		requestInfo.setTotal_fee(MoneyTool.fromYuanToFen(String.valueOf(unpayPrice)));
		requestInfo.setAuth_code(auth_code);
		requestInfo.setTrade_type(trade_type);
		requestInfo.setCreate_ip(ipAddress);
		return requestInfo;
	}
	
	/**
	 * 执行刷卡支付
	 * @param requestInfo
	 * @return
	 */
	public String microPay(UnifiedorderRequest requestInfo) {
		String requestURL = urlProfile.getUnifiedOrder() + "cardpay";
		// 获取车场的parkkey
		String key = parkDao.getParkKey(parkid);
		/**
		 * 签名认证  header中需要传入的参数
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("Timestamp", DateTools.nowDate());
		map.put("Authorization", SignTools.encrypt(DateTools.nowDate() + requestURL+key, key));
		String result = HttpJsonTools.HttpClientPost(requestURL, DateChangeTools.bean2gson(requestInfo), map);
		logger.error("** 刷卡支付支付 - 返回结果:" + result);
		if (null ==result ||  "".equals(result)) {
			logger.error("** 刷卡支付支付 - 返回结果为空。");
			return "FAIL";
		}
		Response resultResp = DateChangeTools.gson2bean(result, Response.class);
		// 判断返回结果
		if(null != resultResp && "2000".equals(resultResp.getResult_code())) {
			// 更新支付状态
			CardPayResponse cardPayResp = DateChangeTools.gson2bean(resultResp.getObject().toString(), CardPayResponse.class);
			// 更新订单信息
			int updPayInfoResult = updatePayInfo(cardPayResp);
			switch(updPayInfoResult) {
				case 1 : logger.info("** 刷卡支付支付 - 支付结果：支付成功，更新子订单成功，更新主订单失败。order_num_pay = " + order_num_pay); break;
				case 2 : logger.info("** 刷卡支付支付 - 支付结果：支付成功，更新子订单失败，更新主订单成功。order_num_pay = " + order_num_pay); break;
				case 3 : logger.info("** 刷卡支付支付 - 支付结果：支付成功，更新子订单信息成功。order_num_pay = " + order_num_pay); break;
				default : logger.info("** 刷卡支付支付 - 支付结果：支付成功，更新订单信息失败。order_num_pay = " + order_num_pay);
			}
			return "SUCCESS";
		}else if(null != resultResp && "10003".equals(resultResp.getResult_code())) {
			try {
				// 查询订单支付结果，每隔5s查询一次，30s超时
				for(int i = 0; i < 7; i++) {
					Thread.sleep(5000);
					boolean queryOrderResult = queryAndOffOrder(requestInfo, "queryorderfrom");
					if(queryOrderResult) {
						return "SUCCESS";
					}
				}
				offOrderLoop(requestInfo);
				return "FAIL";
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		offOrderLoop(requestInfo);
		return "FAIL";
	}
	
	/**
	 * 调取PayCenter查询/关闭订单
	 * @param requestInfo
	 */
	public boolean queryAndOffOrder(UnifiedorderRequest requestInfo, String urlSuf) {
		String logPre = "";
		if("queryorderfrom".equals(urlSuf)) {
			logPre = "** 刷卡支付查询";
		}else if("offorder".equals(urlSuf)) {
			logPre = "** 刷卡支付关单";
		}
		String requestURL = urlProfile.getUnifiedOrder() + urlSuf;
		// 获取车场的parkkey
		String key = parkDao.getParkKey(parkid);
		/**
		 * 签名认证  header中需要传入的参数
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("Timestamp", DateTools.nowDate());
		map.put("Authorization", SignTools.encrypt(DateTools.nowDate() + requestURL+key, key));
		String result = HttpJsonTools.HttpClientPost(requestURL, DateChangeTools.bean2gson(requestInfo), map);
		logger.error(logPre + " - 返回结果:" + result);
		if (null == result ||  "".equals(result)) {
			logger.error(logPre + " - 返回结果为空。");
			return false;
		}
		Response resultResp = DateChangeTools.gson2bean(result, Response.class);
		// 返回结果为成功，根据业务逻辑执行不同操作
		if(null != resultResp && "2000".equals(resultResp.getResult_code())) {
			if("queryorderfrom".equals(urlSuf)) {
				CardPayResponse cardPayResp = DateChangeTools.gson2bean(resultResp.getObject().toString(), CardPayResponse.class);
				// 更新订单信息
				int updPayInfoResult = updatePayInfo(cardPayResp);
				switch(updPayInfoResult) {
					case 1 : logger.info(logPre + " - 查询结果：支付成功，更新子订单成功，更新主订单失败。order_num_pay = " + order_num_pay); break;
					case 2 : logger.info(logPre + " - 查询结果：支付成功，更新子订单失败，更新主订单成功。order_num_pay = " + order_num_pay); break;
					case 3 : logger.info(logPre + " - 查询结果：支付成功，更新子订单信息成功。order_num_pay = " + order_num_pay); break;
					default : logger.info(logPre + " - 查询结果：支付成功，更新订单信息失败。order_num_pay = " + order_num_pay);
				}
			}
			if("offorder".equals(urlSuf)) {
				int updStaResult = orderPayRecordDao.updateStatus(order_num_pay, PayStatusConfig.NPAYOFINT);
				if(0 >= updStaResult) {
					logger.info(logPre + " - 关单成功，更新子订单状态为FAIL的时候修改失败。");
				}else {
					logger.info(logPre + " - 关单成功，更新子订单状态为FAIL的时候修改成功。");
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 用来执行offOrder的方法
	 * @param requestInfo
	 */
	public void offOrderLoop(UnifiedorderRequest requestInfo) {
		try {
			Thread.sleep(15000);
			boolean offOrderResult = queryAndOffOrder(requestInfo, "offorder");
			if(!offOrderResult) {
				Thread.sleep(5000);
				queryAndOffOrder(requestInfo, "offorder");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public int updatePayInfo(CardPayResponse cardPayResp) {
		logger.info("** 刷卡支付 - 更新订单信息 - 参数：" + DateChangeTools.bean2gson(cardPayResp));
		int result = 0;
		// 整理更新子订单的参数
		String success_time = "";
		try {
			if("WEIXIN".equals(trade_type)) {
				success_time = String.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").parse(cardPayResp.getTime_end()).getTime()).substring(0, 10);
			}else if("ZHIFUBAO".equals(trade_type)){
				success_time = String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cardPayResp.getTime_end()).getTime()).substring(0, 10);
			}else {
				success_time = String.valueOf(DateTools.phpNowDate());
			}
		} catch (ParseException e) {
			logger.info("** 刷卡支付 - 更新订单信息 - 时间格式转换异常！自行创建时间。。。" + cardPayResp.getOut_trade_no());
			success_time = String.valueOf(DateTools.phpNowDate());
			e.printStackTrace();
		} finally {
			Map<String, String> recParamMap = new HashMap<String, String>();
			recParamMap.put("order_num_pay", order_num_pay);
			recParamMap.put("pay_status", PayStatusConfig.YPAYOFINT);
			recParamMap.put("update_time", success_time);
			recParamMap.put("describe", "通过扫枪扫码方式付停车费，付款金额为" + MoneyTool.fromFenToYuan(String.valueOf(cardPayResp.getTotal_fee())) + "元");
			recParamMap.put("openid", cardPayResp.getOpenid());
			recParamMap.put("transaction_id", cardPayResp.getTransaction_id());
			int updOrdRecResult = orderPayRecordDao.updateOrderPayRecord(recParamMap);
			if(0 >= updOrdRecResult) {
				logger.info("** 刷卡支付完成 - 更新子订单信息失败！order_num_pay = " + order_num_pay);
				result += 1;
			}
			// 整理更新主订单的参数
			OrderPayRecord orderPayRecord = orderPayRecordDao.getOrderPayRecord(order_num_pay);
			Map<String, String> ordParamMap = new HashMap<String, String>();
			ordParamMap.put("order_num", orderPayRecord.getOrder_num());
			ordParamMap.put("cost_before", String.valueOf(orderPayRecord.getCost_before()));
			ordParamMap.put("cost_after", String.valueOf(orderPayRecord.getCost_after()));
			ordParamMap.put("discount_amount", String.valueOf(orderPayRecord.getDiscount_amount()));
			ordParamMap.put("prepay_amount", String.valueOf(orderPayRecord.getCost_after()));
			ordParamMap.put("prepay_time", success_time);
			ordParamMap.put("pay_method", String.valueOf(PayMethodConfig.ONLINE));
			int updOrdResult = orderInfoDao.updateOrderPayInfo(ordParamMap);
			if(0 >= updOrdResult) {
				logger.info("** 刷卡支付完成 - 更新主订单信息失败！order_num_pay = " + order_num_pay);
				result += 2;
			}
			return result;
		}
		
	}
	
}

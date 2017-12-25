package com.park.scanpay.service.cardpay.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.park.base.common.HttpTools;
import com.park.base.common.constants.PublicKeyConstants;
import com.park.scanpay.config.PayMethodConfig;
import com.park.scanpay.config.PayStatusConfig;
import com.park.scanpay.config.ProjectConfig;
import com.park.scanpay.domain.OrderInfo;
import com.park.scanpay.domain.OrderPayRecord;
import com.park.scanpay.profile.URLProfile;
import com.park.scanpay.request.UnifiedorderRequest;
import com.park.scanpay.response.CardPayResponse;
import com.park.scanpay.response.Response;
import com.park.scanpay.service.CloudCoreService;
import com.park.scanpay.tools.DateChangeTools;
import com.park.scanpay.tools.DateTools;
import com.park.scanpay.tools.MoneyTool;

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
	
	private CloudCoreService cloudCoreService;
	
    public void setCloudCoreService(CloudCoreService cloudCoreService) {
		this.cloudCoreService = cloudCoreService;
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
		String result = HttpTools.pidPost(requestInfo, requestURL, PublicKeyConstants.PayCenter, ProjectConfig.ScanPay);
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
		String result = HttpTools.pidPost(requestInfo, requestURL, PublicKeyConstants.PayCenter, ProjectConfig.ScanPay);
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
					case 1 : logger.info(logPre + " - 查询结果：支付成功，更新子订单信息成功，更新主订单信息失败。order_num_pay = " + order_num_pay); break;
					case 2 : logger.info(logPre + " - 查询结果：支付成功，更新主订单金额信息成功，更新主订单基本信息、子订单信息失败。order_num_pay = " + order_num_pay); break;
					case 3 : logger.info(logPre + " - 查询结果：支付成功，更新子订单信息、主订单金额信息成功，更新主订单基本信息失败。order_num_pay = " + order_num_pay); break;
					case 4 : logger.info(logPre + " - 查询结果：支付成功，更新主订单基本信息成功，更新主订单金额信息、子订单信息失败。order_num_pay = " + order_num_pay); break;
					case 5 : logger.info(logPre + " - 查询结果：支付成功，更新主订单基本信息、子订单信息成功，更新主订单金额信息失败。order_num_pay = " + order_num_pay); break;
					case 6 : logger.info(logPre + " - 查询结果：支付成功，更新主订单基本信息、金额信息成功，更新子订单信息失败。order_num_pay = " + order_num_pay); break;
					case 7 : logger.info(logPre + " - 查询结果：支付成功，更新订单信息成功。order_num_pay = " + order_num_pay); break;
					default : logger.info(logPre + " - 查询结果：支付成功，更新订单信息失败。order_num_pay = " + order_num_pay);
				}
			}
			if("offorder".equals(urlSuf)) {
				int updStaResult = cloudCoreService.modifyOrderPayRecordStatus(order_num_pay, PayStatusConfig.NPAYOFINT);
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
			recParamMap.put("tradeNo", order_num_pay);
			recParamMap.put("payTime", success_time);
			recParamMap.put("describe", "通过扫枪扫码方式付停车费，付款金额为" + MoneyTool.fromFenToYuan(String.valueOf(cardPayResp.getTotal_fee())) + "元");
			int modifyOrderRecordStatus = cloudCoreService.modifyOrderPayRecordStatus(order_num_pay, PayStatusConfig.YPAYOFINT);
			int modifyOrderRecodeBasic = cloudCoreService.modifyOrderPayRecordBasic(recParamMap);
			if(0 >= modifyOrderRecordStatus && 0 >= modifyOrderRecodeBasic) {
				logger.info("** 刷卡支付完成 - 更新子订单信息失败！order_num_pay = " + order_num_pay);
			}else {
				result += 1;
			}
			// 整理更新主订单的参数
			OrderPayRecord orderPayRecord = cloudCoreService.findOrderPayRecordByTradeNo(order_num_pay);
			Map<String, String> ordParamMap = new HashMap<String, String>();
			String order_num = orderPayRecord.getOrderNum();
			ordParamMap.put("orderNum", order_num);
			ordParamMap.put("prepayTime", success_time);
			ordParamMap.put("payMethod", String.valueOf(PayMethodConfig.ONLINE));
			// 查询订单原信息
			OrderInfo orderInfo = cloudCoreService.findOrderInfoByOrderNum(order_num);
			if(null == orderInfo) {
				logger.info("** 刷卡支付完成 - 更新主订单信息时查询信息失败！order_num_pay = " + order_num_pay);
				return result;
			}
			// 整理金额信息
			ordParamMap.put("costBefore", String.valueOf(new BigDecimal(orderPayRecord.getCostBefore()).add(new BigDecimal(orderInfo.getCostBefore()))));
			ordParamMap.put("costAfter",String.valueOf(new BigDecimal(orderPayRecord.getCostAfter()).add(new BigDecimal(orderInfo.getCostAfter()))));
			ordParamMap.put("discountAmount", String.valueOf(new BigDecimal(orderPayRecord.getDiscountAmount()).add(new BigDecimal(orderInfo.getDiscountAmount()))));
			ordParamMap.put("prepayAmount", String.valueOf(new BigDecimal(orderPayRecord.getCostAfter()).add(new BigDecimal(orderInfo.getPrepayAmount()))));
			// 修改订单金额
			int modifyOrderPriceResult = cloudCoreService.modifyOrderPrice(ordParamMap);
			if(0 >= modifyOrderPriceResult) {
				logger.info("** 刷卡支付完成 - 更新主订单金额信息失败！order_num_pay = " + order_num_pay);
			}else {
				result += 2;
			}
			// 修改订单基本信息
			int modifyOrderBasicResult = cloudCoreService.modifyOrderBasic(ordParamMap);
			if(0 >= modifyOrderBasicResult) {
				logger.info("** 刷卡支付完成 - 更新主订单基本信息失败！order_num_pay = " + order_num_pay);
			}else {
				result += 4;
			}
			return result;
		}
		
	}
	
}

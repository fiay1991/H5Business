package com.park.h5business.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.park.h5business.config.PHPConfig;
import com.park.h5business.dao.ParkDao;
import com.park.h5business.profile.URLProfile;
import com.park.h5business.request.UpdateOrderRequest;
import com.park.h5business.response.OrderResponse;
import com.park.h5business.response.PHPResponse;
import com.park.h5business.service.PHPCommService;
import com.park.h5business.tools.DateChangeTools;
import com.park.h5business.tools.DateTools;
import com.park.h5business.tools.HttpJsonTools;
import com.park.h5business.tools.SignTools;
import com.park.h5business.vo.ScanpayVO;
@Repository(value="phpCommServiceImpl")
public class PHPCommServiceImpl implements PHPCommService {
    private Logger logger =Logger.getLogger(getClass());
    
    @Resource(name="parkDaoImpl")
    private ParkDao parkDao;
    
    @Autowired
    URLProfile urlProfile;
	@Override
	public OrderResponse getOrder(ScanpayVO scanpayVO) {
		String plate="";
		try {
			plate = URLEncoder.encode(scanpayVO.getPlate(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String requestURL = urlProfile.getPHPSERVER()+"local/order2.php";
		String params ="parkid="+scanpayVO.getParkid()+"&license_plate="+plate+"&orderid="+scanpayVO.getOrderid()
				+"&cardid="+scanpayVO.getTicketid();
		// 获取车场的parkkey
		String key =parkDao.getParkKey(scanpayVO.getParkid());
		/**
		 * 签名认证  header中需要传入的参数
		 */
		Map<String, String> map =new HashMap<String, String>();
		map.put("Timestamp", DateTools.nowDate());
		map.put("Authorization", SignTools.encrypt(DateTools.nowDate()+requestURL+"?"+params+key,
				key));
		
		String result =HttpJsonTools.HttpClientGet(requestURL, params,map);
		logger.info("根据条件cardid="+scanpayVO.getTicketid()+" & 车牌号="+plate+"查询车辆的账单状态：** "+result);
		/**
		 * --------测试数据
		 */
		
//		result="{'code':'200','msg':'','data':{'orderid':'"+String.valueOf((int) ((Math.random() * 10 + 1) * 10000000))+"','local_orderid':'15040515185243219'"
//				+ ",'is_user':'0','status':'2','pay_type':'1','total_price':'0.01','payed_price':'0.01',"
//				+ "'discount_price':'0','unpay_price':'0.01','total_count':'8355','pay_time':'1428218355'}"
//				+ "}";
		/**
		 * --------
		 */
		PHPResponse response =null;
		OrderResponse orderResponse =null;
		if (!"".equals(result)) {
			response=DateChangeTools.gson2bean(result, PHPResponse.class);
			if ("200".equals(response.getCode())) {
				String data=response.getData().toString();
				data =data.replace("local_orderid=,", "local_orderid='',");//如果local_orderid的值为空 则gson解析的时候报错 
				orderResponse=DateChangeTools.gson2bean(data, OrderResponse.class);
			}
		}
		return orderResponse;
	}
    /**
     * 支付成功后调用
     * @param order_num 订单编号
     * @param cost_after实付费(单位：元 实际应该交的费用[去掉优惠券后应该交的的金额])
     * @param uid用户id
     * @param balance余额交的t金额（单位：元）
     * @return
     */
	@Override
	public String updateOrder(String order_num,String cost_after,int uid,float balance){
		String requestURL = urlProfile.getPHPORDERSERVER()+"memv2/order/returnurl.php";
		UpdateOrderRequest updateOrderRequest =new UpdateOrderRequest();
		updateOrderRequest.setOrder_num(order_num);
		updateOrderRequest.setUid(uid);
		updateOrderRequest.setBalance(balance);
		updateOrderRequest.setCost_after(cost_after);
		
		String result =HttpJsonTools.HttpClientPost(requestURL,DateChangeTools.bean2gson(updateOrderRequest),getMap(requestURL));
		logger.info("支付成功后更改订单状态返回结果 *** "+result);
		if (null ==result ||  "".equals(result)) {
			logger.error("*** 支付成功后更改订单状态返回结果为空");
			result="";
		}
		return result;	
	}
	/**
	 * 微信下单后调用--修改订单的一些信息
	 * @param out_trade_no 
	 * @param total_fee
	 * @return
	 */
	@Override
	public String weixinnotify(String out_trade_no,String total_fee,String prepayid){
		String requestURL = urlProfile.getPHPORDERSERVER()+"memv2/pay/h5motify.php";
		String params ="out_trade_no="+out_trade_no+"&total_fee="+total_fee+"&prepayid="+prepayid;
		String result =HttpJsonTools.HttpClientGet(requestURL, params,getMap(requestURL+"?"+params));
		logger.info("修改订单返回结果: *** "+result);
		if (null ==result ||  "".equals(result)) {
			logger.error("*** 修改订单返回结果为空"+result);
			result="";
		}
		return result;
	}
	/**
	 * 模拟app 调用接口需要传入heard的参数
	 * @param url
	 * @return
	 */
	public Map<String, String> getMap(String url){
		Map<String, String> map =new HashMap<String, String>();
		map.put("Appid", PHPConfig.APP_ID);
		map.put("Timestamp", DateTools.nowDate());
		map.put("Authorization", SignTools.encrypt(DateTools.nowDate()+url+PHPConfig.APP_KEY,
				PHPConfig.APP_KEY));
		return map; 
	}
	
}

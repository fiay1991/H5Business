package com.park.scanpay.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.park.base.common.HttpTools;
import com.park.base.common.constants.PublicKeyConstants;
import com.park.scanpay.config.ProjectConfig;
import com.park.scanpay.profile.URLProfile;
import com.park.scanpay.request.FindOrderRequest;
import com.park.scanpay.request.UnifiedorderRequest;
import com.park.scanpay.response.FindOrderResponse;
import com.park.scanpay.response.Response;
import com.park.scanpay.service.UnifiedOrderService;
import com.park.scanpay.tools.DateChangeTools;
import com.park.scanpay.tools.MoneyTool;
import com.park.scanpay.tools.StringTool;

@Repository(value="unifiedOrderServiceImpl")
public class UnifiedOrderServiceImpl implements UnifiedOrderService {
	private Logger logger =Logger.getLogger(getClass());
	
	@Autowired
	URLProfile urlProfile;
	
	@Override
	public String WEIXINunifiedOrder(String openid,String orderid, String parkid, float free) {
		
		
		UnifiedorderRequest unifiedorderRequest =new UnifiedorderRequest();
		unifiedorderRequest.setBody("PARKFEE");
		unifiedorderRequest.setOut_trade_no(orderid);
		unifiedorderRequest.setParkid(parkid);
		unifiedorderRequest.setTerminal_type("H5P");
		unifiedorderRequest.setTotal_fee(MoneyTool.fromYuanToFen(String.valueOf(free)));
		unifiedorderRequest.setTrade_type("WEIXIN");
		unifiedorderRequest.setOpenid(openid);
		
		return unifiedOrder(unifiedorderRequest);
	}
	
	@Override
	public String AliPayunifiedOrder(String orderid, String parkid, float free, String userid, String accesstoken) {

		UnifiedorderRequest unifiedorderRequest =new UnifiedorderRequest();
		unifiedorderRequest.setBody("PARKFEE");
		unifiedorderRequest.setOut_trade_no(orderid);
		unifiedorderRequest.setParkid(parkid);
		unifiedorderRequest.setTerminal_type("H5P");
		unifiedorderRequest.setTotal_fee(MoneyTool.fromYuanToFen(String.valueOf(free)));
		unifiedorderRequest.setTrade_type("ZHIFUBAO");
		unifiedorderRequest.setUserid(userid);
		unifiedorderRequest.setAccesstoken(accesstoken);
		unifiedorderRequest.setReturn_url(urlProfile.getRETURNURL());

		return unifiedOrder(unifiedorderRequest);
	}

	public String unifiedOrder(UnifiedorderRequest unifiedorderRequest){
		String requestURL = urlProfile.getUnifiedOrder()+"unifiedorder";
		String result =HttpTools.pidPost(unifiedorderRequest, requestURL, PublicKeyConstants.PayCenter, ProjectConfig.ScanPay);
		logger.error("*** 统一下单功能返回结果:"+result);
		if (null ==result ||  "".equals(result)) {
			logger.error("*** 统一下单功能返回结果为空");
			result="";
		}
		return result;
	}
	
	public String findOrder(String orderid){
		FindOrderRequest fRequest =new FindOrderRequest();
		fRequest.setOut_trade_no(orderid);
		fRequest.setTrade_type("ZHIFUBAO");
		String requestURL = urlProfile.getFINDORDER()+"queryorder";
		String result =HttpTools.pidPost(fRequest, requestURL, PublicKeyConstants.PayCenter, ProjectConfig.ScanPay);
		logger.info("支付宝查询订单返回结果："+result);
		if (null ==result ||  "".equals(result)) {
			logger.error("*** 支付宝查询订单功能返回结果为空");
			result="FAIL";
		}else {
			 Response rs =DateChangeTools.gson2bean(result, Response.class);
			 if (null !=rs && "2000".equals(rs.getResult_code())) {
				 FindOrderResponse fResponse =DateChangeTools.gson2bean(rs.getObject().toString(), FindOrderResponse.class);
				 if (StringTool.isNotNull(fResponse)) {
					 return fResponse.getPay_result();					
				}
			 }
		}
		return "FAIL";
	}

//	public String findOrder(String orderid){
//		FindOrderRequest fRequest =new FindOrderRequest();
//		fRequest.setOut_trade_no(orderid);
//		fRequest.setTrade_type("ZHIFUBAO");
//		String requestURL = urlProfile.getFINDORDER()+"queryorder";
//		/**
//		 * 签名认证  header中需要传入的参数
//		 */
//		Map<String, String> map =new HashMap<String, String>();
//		String result =HttpJsonTools.HttpClientPost(requestURL, DateChangeTools.bean2gson(fRequest),map);
//		logger.info("支付宝查询订单返回结果："+result);
//		if (null ==result ||  "".equals(result)) {
//			logger.error("*** 支付宝查询订单功能返回结果为空");
//			result="FAIL";
//		}else {
//			 Response rs =DateChangeTools.gson2bean(result, Response.class);
//			 if (null !=rs && "2000".equals(rs.getResult_code())) {
//				 FindOrderResponse fResponse =DateChangeTools.gson2bean(rs.getObject().toString(), FindOrderResponse.class);
//				 if (StringTool.isNotNull(fResponse)) {
//					 return fResponse.getPay_result();					
//				}
//			 }
//		}
//		return "FAIL";
//	}

//	public String unifiedOrder2(UnifiedorderRequest unifiedorderRequest,String parkid){
//	String requestURL = urlProfile.getUnifiedOrder()+"unifiedorder";
//	// 获取车场的parkkey
////	String key =parkDao.getParkKey(parkid);
//	String key = "e5532f5de1d4368f4265e49ef067420a";
//	/**
//	 * 签名认证  header中需要传入的参数
//	 */
//	Map<String, String> map =new HashMap<String, String>();
//	map.put("Timestamp", DateTools.nowDate());
//	map.put("Authorization", SignTools.encrypt(DateTools.nowDate()+requestURL+key,key));
//	String result =HttpJsonTools.HttpClientPost(requestURL, DateChangeTools.bean2gson(unifiedorderRequest),map);
//	logger.error("*** 统一下单功能返回结果:"+result);
//	if (null ==result ||  "".equals(result)) {
//		logger.error("*** 统一下单功能返回结果为空");
//		result="";
//	}
//	return result;
//}
	
}

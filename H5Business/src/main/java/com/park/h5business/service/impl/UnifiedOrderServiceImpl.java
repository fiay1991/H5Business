package com.park.h5business.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.park.h5business.dao.ParkDao;
import com.park.h5business.profile.URLProfile;
import com.park.h5business.request.FindOrderRequest;
import com.park.h5business.request.UnifiedorderRequest;
import com.park.h5business.response.FindOrderResponse;
import com.park.h5business.response.Response;
import com.park.h5business.service.UnifiedOrderService;
import com.park.h5business.tools.DateChangeTools;
import com.park.h5business.tools.DateTools;
import com.park.h5business.tools.HttpJsonTools;
import com.park.h5business.tools.MoneyTool;
import com.park.h5business.tools.SignTools;
import com.park.h5business.tools.StringTool;
@Repository(value="unifiedOrderServiceImpl")
public class UnifiedOrderServiceImpl implements UnifiedOrderService {
	private Logger logger =Logger.getLogger(getClass());
	
	@Resource(name="parkDaoImpl")
    private ParkDao parkDao;
	
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
		
		return unifiedOrder(unifiedorderRequest, parkid);
	}
	
	@Override
	public String AliPayunifiedOrder(String orderid, String parkid, float free) {

		UnifiedorderRequest unifiedorderRequest =new UnifiedorderRequest();
		unifiedorderRequest.setBody("PARKFEE");
		unifiedorderRequest.setOut_trade_no(orderid);
		unifiedorderRequest.setParkid(parkid);
		unifiedorderRequest.setTerminal_type("H5P");
		unifiedorderRequest.setTotal_fee(MoneyTool.fromYuanToFen(String.valueOf(free)));
		unifiedorderRequest.setTrade_type("ZHIFUBAO");
		unifiedorderRequest.setReturn_url(urlProfile.getRETURNURL());

		return unifiedOrder(unifiedorderRequest, parkid);
	}

	public String unifiedOrder(UnifiedorderRequest unifiedorderRequest,String parkid){
		String requestURL = urlProfile.getUnifiedOrder()+"unifiedorder";
		// 获取车场的parkkey
		String key =parkDao.getParkKey(parkid);
		/**
		 * 签名认证  header中需要传入的参数
		 */
		Map<String, String> map =new HashMap<String, String>();
		map.put("Timestamp", DateTools.nowDate());
		map.put("Authorization", SignTools.encrypt(DateTools.nowDate()+requestURL+key,key));
		String result =HttpJsonTools.HttpClientPost(requestURL, DateChangeTools.bean2gson(unifiedorderRequest),map);
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
		/**
		 * 签名认证  header中需要传入的参数
		 */
		Map<String, String> map =new HashMap<String, String>();
		String result =HttpJsonTools.HttpClientPost(requestURL, DateChangeTools.bean2gson(fRequest),map);
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

}

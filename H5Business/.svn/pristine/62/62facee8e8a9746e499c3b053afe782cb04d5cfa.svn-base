package com.park.scanpay.service;

import org.springframework.stereotype.Repository;

@Repository(value="unifiedOrderService")
public interface UnifiedOrderService {
	
	public String WEIXINunifiedOrder(String openid,String orderid, String parkid, float free);
	
	public String AliPayunifiedOrder(String orderid, String parkid, float free, String userid, String accesstoken);
	
	public String findOrder(String orderid);
	
}

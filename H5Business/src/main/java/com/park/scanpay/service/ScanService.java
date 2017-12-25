package com.park.scanpay.service;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

@Repository(value="scanService")
public interface ScanService {

	public String updateOrder(String order_num,String price,String openid,String prepayid,Model model);

	public String scanpay(String parkid,String ticketid,String plate,String ver,String useragent,Model model);
	
	public String alipayupdateOrder(String order_num,String price,Model model);
}

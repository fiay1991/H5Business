package com.park.scanpay.service;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
@Repository(value="distributeService")
public interface BusinessService {
	
	public String redirectURL(String self_url);
	
	public String creatOrder(String order_num,float total_price,float unpay_price,float discount_amount, String code);
	
	public boolean getUnifiedorder(String alicode, String code,String orderid,String parkid,float free,Model model);
	
	public String generateOpenid(String alicode, String code);

}

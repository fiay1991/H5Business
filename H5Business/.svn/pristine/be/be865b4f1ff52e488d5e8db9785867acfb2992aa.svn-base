package com.park.h5business.service;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
@Repository(value="distributeService")
public interface BusinessService {
	
	public String redirectURL(String urlparams);
	
	public String creatOrder(String order_num,float total_price,float unpay_price,float discount_amount, String code);
	
	public boolean getUnifiedorder(String code,String orderid,String parkid,float free,Model model);

}

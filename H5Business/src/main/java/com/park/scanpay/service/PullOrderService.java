package com.park.scanpay.service;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
@Repository(value="pullOrderService")
public interface PullOrderService {
	
	public String pullorder(String alicode, String code,String parkid, String ticketid, String plate,
			String ver,String useragent,Model model); 
   
	
}

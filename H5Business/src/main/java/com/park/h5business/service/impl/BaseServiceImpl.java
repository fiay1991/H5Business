package com.park.h5business.service.impl;

import java.math.BigDecimal;

import com.park.h5business.domain.OrderPayRecord;
import com.park.h5business.tools.DateTools;
import com.park.h5business.tools.OrderNumTools;

public class BaseServiceImpl {
	
	
	public OrderPayRecord  getOrderPayRecord(String order_num,float total_price,float unpay_price,float discount_amount){
		OrderPayRecord orderPayRecord =new OrderPayRecord();
		orderPayRecord.setOrder_num(order_num);
		String order_num_pay=OrderNumTools.creatOrderNum();
		orderPayRecord.setOrder_num_pay(order_num_pay);
		orderPayRecord.setCost_after(new BigDecimal(unpay_price));
		orderPayRecord.setCost_before(new BigDecimal(total_price));
		orderPayRecord.setDiscount_amount(new BigDecimal(discount_amount));
		orderPayRecord.setCreate_time((int) (DateTools.phpNowDate()));
		orderPayRecord.setUpdate_time((int) (DateTools.phpNowDate()));
		orderPayRecord.setPay_status(1);
		return orderPayRecord;
	}
	
}

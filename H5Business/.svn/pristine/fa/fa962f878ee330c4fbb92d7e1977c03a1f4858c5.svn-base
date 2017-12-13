package com.park.h5business.service.cardpay;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository(value="cardPayService")
public interface CardPayService {

	/**
	 * 刷卡支付下单方法
	 * @param paramMap
	 * @return
	 */
	String pay(Map<String, String> paramMap);

	/**
	 * 刷卡支付查询支付结果
	 * @param order_num_pay
	 * @return
	 */
	String query(String order_num_pay);
	
}

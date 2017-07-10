package com.park.h5business.mapper;

import org.springframework.stereotype.Repository;

import com.park.h5business.domain.OrderPayRecord;

@Repository(value="orderPayRecordMapper")
public interface OrderPayRecordMapper {

	public int add(OrderPayRecord orderPayRecord);
	
	public String getParkId(String order_num);
	
	/**
	 * 根据订单号查询订单实体 add by fangct at 20170608 
	 * @param order_num
	 * @return
	 */
	public OrderPayRecord getOrderPayRecord(String order_num);
}

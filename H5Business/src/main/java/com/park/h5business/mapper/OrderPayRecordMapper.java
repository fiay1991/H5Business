package com.park.h5business.mapper;

import java.util.Map;

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

	/**
	 * 根据order_num_pay查询支付结果
	 * @param order_num_pay
	 * @return
	 */
	public Integer getStatusByOrderNumPay(String order_num_pay);

	/**
	 * 修改子订单支付状态
	 * @param order_num_pay
	 * @param status
	 * @return
	 */
	public int updateStatus(Map<String, String> paramMap);

	/**
	 * 修改子订单的支付信息
	 * @param paramMap
	 * @return
	 */
	public int updateOrderPayRecord(Map<String, String> paramMap);
}

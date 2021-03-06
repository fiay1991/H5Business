package com.park.h5business.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.park.h5business.domain.OrderInfo;
import com.park.h5business.vo.ScanpayVO;
@Repository(value="orderInfoDao")
public interface OrderInfoDao {
	
	/**
	 * 根据 车场id 票号  车牌  获取订单信息
	 * @param scanpayVO
	 * @return
	 */
	public OrderInfo getOrderInfo(ScanpayVO scanpayVO);
	
	/**
	 * 通过plate获取parkid
	 * @param plate
	 * @return
	 */
	public String getParkIdByPlate(String plate);

	/**
	 * 通过parkid和platenumber查询订单
	 * @param paramMap
	 * @return
	 */
	public String getOrder(Map<String, String> paramMap);

	/**
	 * 通过order_num查询ScanpayVO
	 * @param order_num
	 * @return
	 */
	public ScanpayVO getScanpayvoByOrdernum(String order_num);

	/**
	 * 修改主订单支付信息
	 * @param ordParamMap
	 * @return
	 */
	public int updateOrderPayInfo(Map<String, String> paramMap);

}

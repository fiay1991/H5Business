package com.park.h5business.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.park.h5business.domain.OrderInfo;
import com.park.h5business.vo.ScanpayVO;

@Repository(value="orderInfoMapper")
public interface OrderInfoMapper {
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

}

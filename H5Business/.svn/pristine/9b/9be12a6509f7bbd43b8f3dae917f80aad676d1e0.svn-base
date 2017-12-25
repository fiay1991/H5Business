package com.park.scanpay.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.park.scanpay.domain.OrderInfo;
import com.park.scanpay.domain.OrderPayRecord;
import com.park.scanpay.vo.ScanpayVO;

/**
 * 
 * @author WangYuefei
 * @function 调用CloudCore
 * 
 */
@Repository(value="cloudCoreService")
public interface CloudCoreService {

	/**
	 * 通过parkid和plate查询订单
	 * @param parkid
	 * @param plate
	 * @return
	 */
	OrderInfo findOrderByPlate(String parkid, String plate);

	/**
	 * 通过order_num查询订单部分信息
	 * @param order_num
	 * @return
	 */
	ScanpayVO findScanVOByOrderNum(String order_num);

	/**
	 * 通过order_num查询订单详情
	 * @param order_num
	 * @return
	 */
	OrderInfo findOrderInfoByOrderNum(String order_num);

	/**
	 * 更新主订单金额信息
	 * @param ordParamMap
	 * @return
	 */
	int modifyOrderPrice(Map<String, String> ordParamMap);

	/**
	 * 更新主订单基本信息
	 * @param ordParamMap
	 * @return
	 */
	int modifyOrderBasic(Map<String, String> ordParamMap);

	/**
	 * 根据tradeNo查询orderPayRecord
	 * @param order_num
	 * @return
	 */
	OrderPayRecord findOrderPayRecordByTradeNo(String order_num);

	/**
	 * 根据tradeNo修改orderPayRecord的状态
	 * @param order_num_pay
	 * @param npayofint
	 * @return
	 */
	int modifyOrderPayRecordStatus(String order_num_pay, String npayofint);

	/**
	 * 根据tradeNo修改orderPayRecord基本信息
	 * @param recParamMap
	 * @return
	 */
	int modifyOrderPayRecordBasic(Map<String, String> recParamMap);

	/**
	 * 新增orderPayRecord
	 * @param orderPayRecord
	 * @return
	 */
	int addOrderPayRecord(OrderPayRecord orderPayRecord);

}

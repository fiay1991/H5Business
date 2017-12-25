package com.park.scanpay.service;

import org.springframework.stereotype.Repository;

import com.park.scanpay.response.OrderResponse;
import com.park.scanpay.vo.ScanpayVO;

@Repository(value="phpCommService")
public interface PHPCommService {
	/**
	 * 获取云平台订单信息
	 * @param parkid 停车场id
	 * @param license_plate 车牌
	 * @param orderid 云端订单号
	 * @param cardid 纸票id
	 * @return
	 */
	public OrderResponse getOrder(ScanpayVO scanpayVO);
	
	public String updateOrder(String order_num,String cost_after,int uid,float balance);
	
	public String weixinnotify(String out_trade_no,String total_fee,String prepayid);

}

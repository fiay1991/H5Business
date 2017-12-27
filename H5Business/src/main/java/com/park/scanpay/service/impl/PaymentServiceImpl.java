package com.park.scanpay.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.park.base.common.DataChangeTools;
import com.park.scanpay.domain.OrderEnterDomain;
import com.park.scanpay.domain.OrderPayRecordDomain;
import com.park.scanpay.domain.response.ObjectResponse;
import com.park.scanpay.domain.response.Response;
import com.park.scanpay.profile.CCProfile;
import com.park.scanpay.service.PaymentService;
import com.park.scanpay.tools.DateTools;
import com.park.scanpay.tools.HttpUtils;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="paymentServiceImpl")
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private CCProfile ccProfile;
	
	@Override
	@Transactional
	public String updateOrder(String tradeNo, String price) {
		// 修改交易流水状态
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("tradeNo", tradeNo);
		paramMap.put("oldPayStatus", "1");
		paramMap.put("newPayStatus", "2");
		paramMap.put("payTime", String.valueOf(DateTools.phpNowDate()));
		String updateTradeResult = HttpUtils.post2CloudCore(paramMap, ccProfile.getModOrderPayRecordStatus());
		Response response = DataChangeTools.gson2bean(updateTradeResult, Response.class);
		if(response == null || !response.getCode().equals("200")) {
			return "failed";
		}
		// 查询交易流水
		paramMap = new HashMap<String, String>();
		paramMap.put("tradeNo", tradeNo);
		String findTradeResult = HttpUtils.post2CloudCore(paramMap, ccProfile.getFindOrderPayRecord());
		ObjectResponse objectResponse = DataChangeTools.gson2bean(findTradeResult, ObjectResponse.class);
		if(objectResponse == null || !objectResponse.getCode().equals("200")) {
			return "failed";
		}
		String tradeJson = DataChangeTools.bean2gson(objectResponse.getData());
		OrderPayRecordDomain orderPayRecord = DataChangeTools.gson2bean(tradeJson, OrderPayRecordDomain.class);
		// 查询订单
		paramMap = new HashMap<String, String>();
		paramMap.put("orderNum", orderPayRecord.getOrderNum());
		String orderResult = HttpUtils.post2CloudCore(paramMap, ccProfile.getFindOrderByOrderNum());
		ObjectResponse objOrderResponse = DataChangeTools.gson2bean(orderResult, ObjectResponse.class);
		if(objOrderResponse == null || !objOrderResponse.getCode().equals("200")) {
			return "failed";
		}
		String orderJson = DataChangeTools.bean2gson(objOrderResponse.getData());
		if(null == orderJson || "".equals(orderJson)) {
			return "failed";
		}
		OrderEnterDomain orderEnter = DataChangeTools.gson2bean(orderJson, OrderEnterDomain.class);
		// 修改订单
		String orderCostBefore = orderEnter.getCostBefore();
		String orderCostAfter = orderEnter.getCostAfter();
		String orderDiscount = orderEnter.getDiscountAmount();
		String tradeCostBefore = orderPayRecord.getCostBefore();
		String tradeCostAfter = orderPayRecord.getCostAfter();
		String tradeDiscount = orderPayRecord.getDiscountAmount();
		if(null == orderCostBefore || "".equals(orderCostBefore)) {
			paramMap.put("costBefore", tradeCostBefore);
		}else {
			BigDecimal orderCB = new BigDecimal(orderCostBefore);
			BigDecimal tradeCB = new BigDecimal(tradeCostBefore);
			paramMap.put("costBefore", orderCB.add(tradeCB).toString());
		}
		if(null == orderCostAfter || "".equals(orderCostAfter)) {
			paramMap.put("costAfter", tradeCostAfter);
		}else {
			BigDecimal orderCA = new BigDecimal(orderCostAfter);
			BigDecimal tradeCA = new BigDecimal(tradeCostAfter);
			paramMap.put("costAfter", orderCA.add(tradeCA).toString());
		}
		if(null == orderDiscount || "".equals(orderDiscount)) {
			paramMap.put("discountAmount", tradeDiscount);
		}else {
			BigDecimal orderDA = new BigDecimal(orderDiscount);
			BigDecimal tradeDA = new BigDecimal(tradeDiscount);
			paramMap.put("discountAmount", orderDA.add(tradeDA).toString());
		}
		paramMap.put("prepayAmount", orderPayRecord.getCostAfter());
		paramMap.put("prepayTime", orderPayRecord.getPayTime());
		String updateOrderResult = HttpUtils.post2CloudCore(paramMap, ccProfile.getModOrderPriceInfo());
		ObjectResponse objUOResponse = DataChangeTools.gson2bean(updateOrderResult, ObjectResponse.class);
		if(objUOResponse == null || !objUOResponse.getCode().equals("200")) {
			return "failed";
		}
		paramMap = new HashMap<String, String>();
		paramMap.put("orderNum", orderPayRecord.getOrderNum());
		paramMap.put("toTradeNo", tradeNo);
		paramMap.put("newStatus", "1");
		HttpUtils.post2CloudCore(paramMap, ccProfile.getModOrderBasicInfo());
		return "success";
	}

}

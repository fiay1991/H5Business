package com.park.scanpay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.park.base.common.DataChangeTools;
import com.park.base.common.ResultTools;
import com.park.base.common.domain.ObjectResponse;
import com.park.scanpay.domain.OrderInfo;
import com.park.scanpay.domain.OrderPayRecord;
import com.park.scanpay.profile.CCProfile;
import com.park.scanpay.service.CloudCoreService;
import com.park.scanpay.tools.HttpUtils;
import com.park.scanpay.vo.ScanpayVO;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="cloudCoreServiceImpl")
public class CloudCoreServiceImpl implements CloudCoreService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CCProfile ccProfile;

	@Override
	public OrderInfo findOrderByPlate(String parkid, String plate) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("parkId", parkid);
		paramMap.put("plateNumber", plate);
		paramMap.put("serviceStatus", "1");
		String result = HttpUtils.post2CloudCore(paramMap, ccProfile.getFindOrderByCarInfo());
		ObjectResponse objectListResponse = ResultTools.getObjectListResponse(result, OrderInfo.class);
		String code = objectListResponse.getCode();
		if(!"200".equals(code)) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<OrderInfo> orderInfoList = (List<OrderInfo>) objectListResponse.getData();
		return orderInfoList.get(0);
	}

	@Override
	public ScanpayVO findScanVOByOrderNum(String order_num) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderNum", order_num);
		String result = HttpUtils.post2CloudCore(paramMap, ccProfile.getFindOrderByOrderNum());
		ObjectResponse objectResponse = ResultTools.getObjectResponse(result, OrderInfo.class);
		if(!"200".equals(objectResponse.getCode())) {
			return null;
		}
		OrderInfo orderInfo = (OrderInfo) objectResponse.getData();
		ScanpayVO scanpayVO = new ScanpayVO();
		scanpayVO.setOrderid(orderInfo.getOrderNum());
		scanpayVO.setParkid(orderInfo.getParkId());
		scanpayVO.setPlate(orderInfo.getPlateNumber());
		scanpayVO.setTicketid(orderInfo.getTicketId());
		return scanpayVO;
	}

	@Override
	public OrderInfo findOrderInfoByOrderNum(String order_num) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderNum", order_num);
		String result = HttpUtils.post2CloudCore(paramMap, ccProfile.getFindOrderByOrderNum());
		ObjectResponse objectResponse = ResultTools.getObjectResponse(result, OrderInfo.class);
		if(!"200".equals(objectResponse.getCode())) {
			return null;
		}
		return (OrderInfo) objectResponse.getData();
	}

	@Override
	public int modifyOrderPrice(Map<String, String> ordParamMap) {
		String result = HttpUtils.post2CloudCore(ordParamMap, ccProfile.getModOrderPriceInfo());
		ObjectResponse objectResponse = ResultTools.getObjectResponse(result, String.class);
		if(!"200".equals(objectResponse.getCode())) {
			return -1;
		}
		return 1;
	}

	@Override
	public int modifyOrderBasic(Map<String, String> ordParamMap) {
		String result = HttpUtils.post2CloudCore(ordParamMap, ccProfile.getModOrderBasicInfo());
		ObjectResponse objectResponse = ResultTools.getObjectResponse(result, String.class);
		if(!"200".equals(objectResponse.getCode())) {
			return -1;
		}
		return 1;
	}

	@Override
	public OrderPayRecord findOrderPayRecordByTradeNo(String order_num) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("tradeNo", order_num);
		String result = HttpUtils.post2CloudCore(paramMap, ccProfile.getFindOrderPayRecord());
		ObjectResponse objectResponse = ResultTools.getObjectResponse(result, OrderInfo.class);
		if(!"200".equals(objectResponse.getCode())) {
			return null;
		}
		return (OrderPayRecord) objectResponse.getData();
	}

	@Override
	public int modifyOrderPayRecordStatus(String order_num_pay, String npayofint) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("tradeNo", order_num_pay);
		paramMap.put("newPayStatus", npayofint);
		String result = HttpUtils.post2CloudCore(paramMap, ccProfile.getModOrderPayRecordStatus());
		Map<String, String> resultMap = DataChangeTools.json2Map(result);
		if(!"200".equals(resultMap.get("code"))) {
			logger.info("** CloudCore - 修改子订单状态失败：" + resultMap.get("msg"));
			return -1;
		}
		return 1;
	}

	@Override
	public int modifyOrderPayRecordBasic(Map<String, String> recParamMap) {
		String result = HttpUtils.post2CloudCore(recParamMap, ccProfile.getModOrderPayRecordBasic());
		Map<String, String> resultMap = DataChangeTools.json2Map(result);
		if(!"200".equals(resultMap.get("code"))) {
			logger.info("** CloudCore - 修改子订单状态失败：" + resultMap.get("msg"));
			return -1;
		}
		return 1;
	}

	@Override
	public int addOrderPayRecord(OrderPayRecord orderPayRecord) {
		String result = HttpUtils.post2CloudCore(orderPayRecord, ccProfile.getAddOrderPayRecord());
		Map<String, String> resultMap = DataChangeTools.json2Map(result);
		if(!"200".equals(resultMap.get("code"))) {
			logger.info("** CloudCore - 修改子订单状态失败：" + resultMap.get("msg"));
			return -1;
		}
		return 1;
	}
	
}

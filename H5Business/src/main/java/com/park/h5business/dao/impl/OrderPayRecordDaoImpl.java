package com.park.h5business.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.h5business.dao.OrderPayRecordDao;
import com.park.h5business.domain.OrderPayRecord;
import com.park.h5business.mapper.OrderPayRecordMapper;
@Repository(value="orderPayRecordDaoImpl")
public class OrderPayRecordDaoImpl implements OrderPayRecordDao {
	@Resource(name="orderPayRecordMapper")
	private OrderPayRecordMapper orderPayRecordMapper;
	@Override
	public int add(OrderPayRecord orderPayRecord) {
		return orderPayRecordMapper.add(orderPayRecord);
	}

	public String getParkId(String order_num){
		return orderPayRecordMapper.getParkId(order_num);
	}

	public OrderPayRecord getOrderPayRecord(String order_num) {
		return orderPayRecordMapper.getOrderPayRecord(order_num);
	}
}

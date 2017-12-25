package com.park.scanpay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.scanpay.dao.impl.DemoDaoImpl;
import com.park.scanpay.domain.Demo;
import com.park.scanpay.service.DemoService;

/**
 * @author liuyang
 * 时间：2016年3月24日
 * 功能：
 * 备注：
 */
@Repository(value = "DemoServiceImpl")
public class DemoServiceImpl implements DemoService {
	
	@Resource(name = "DemoDaoImpl")
	private DemoDaoImpl demoDaoImpl;

	@Override
	public List<Demo> findAll() {
		return demoDaoImpl.findAll();
	}

}

package com.park.scanpay.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.scanpay.domain.Demo;


/**
 * @author liuyang 时间：2016年3月25日 功能： 备注：
 */
@Repository(value = "DemoMapper")
public interface DemoMapper {
	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<Demo> findAll();
}

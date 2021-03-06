package com.park.scanpay.mapper.noplate;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.park.scanpay.domain.noplate.NoPlateRecord;
@Repository(value="noPlateMapper")
public interface NoPlateMapper {
	
	/**
	 * 记录无牌车进场信息
	 * @param noPlateRecord
	 * @return
	 */
	public int insert(NoPlateRecord noPlateRecord);
	
	/**
	 * 更新出场信息
	 * @param plate
	 * @return
	 */
	public int update(NoPlateRecord noPlateRecord);

	/**
	 * 查询
	 * @param openid
	 * @return
	 */
	public NoPlateRecord select(String openid);
	
	/**
	 * 查询开关闸状态
	 * @param params
	 * @return
	 */
	public String findOpenStauts(Map<String,String> params);

}

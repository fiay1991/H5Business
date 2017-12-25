package com.park.scanpay.dao.noplate;

import org.springframework.stereotype.Repository;

import com.park.scanpay.domain.noplate.NoPlateRecord;
@Repository(value="noPlateDao")
public interface NoPlateDao {
	
	/**
	 * 记录无牌车进场信息
	 * @param noPlateRecord
	 * @return
	 */
	public int insertEnterInfo(NoPlateRecord noPlateRecord);
	
	/**
	 * 更新出场信息
	 * @param plate
	 * @return
	 */
	public int updateExitInfo(NoPlateRecord noPlateRecord);

	/**
	 * 查询
	 * @param openid
	 * @return
	 */
	public NoPlateRecord selectTempPlateInfo(String openid);

}

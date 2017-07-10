package com.park.h5business.dao;

import org.springframework.stereotype.Repository;

@Repository(value="parkDao")
public interface ParkDao {

	/**
	 * 根据车场id查询车场的key
	 * @param parkid
	 * @return
	 */
	public String getParkKey(String parkid);
}

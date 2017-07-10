package com.park.h5business.mapper;

import org.springframework.stereotype.Repository;

@Repository(value="parkMapper")
public interface  ParkMapper {
	/**
	 * 根据车场id查询车场的key
	 * @param parkid
	 * @return
	 */
	public String getParkKey(String parkid);
	
}

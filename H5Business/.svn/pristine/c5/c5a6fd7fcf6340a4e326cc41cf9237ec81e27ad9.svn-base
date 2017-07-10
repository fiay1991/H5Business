package com.park.h5business.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.h5business.dao.ParkDao;
import com.park.h5business.mapper.ParkMapper;
@Repository(value="parkDaoImpl")
public class ParkDaoImpl implements ParkDao {

	@Resource(name="parkMapper")
	private ParkMapper parkMapper;
	@Override
	public String getParkKey(String parkid) {
		
		return parkMapper.getParkKey(parkid);
	}

}

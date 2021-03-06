/**
 * 
 */
package com.park.scanpay.dao.impl.noplate;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.scanpay.dao.noplate.NoPlateDao;
import com.park.scanpay.domain.noplate.NoPlateRecord;
import com.park.scanpay.mapper.noplate.NoPlateMapper;

/**
 * @author fangct 
 * created on 2017年12月15日
 */
@Repository(value="noPlateDaoImpl")
public class NoPlateDaoImpl implements NoPlateDao {

	@Resource(name="noPlateMapper")
	private NoPlateMapper noPlateMapper;
	@Override
	public int updateInfo(NoPlateRecord noPlateRecord) {
		return noPlateMapper.update(noPlateRecord);
	}

	@Override
	public int insertEnterInfo(NoPlateRecord noPlateRecord) {
		noPlateMapper.insert(noPlateRecord);
		return noPlateRecord.getId();
	}

	@Override
	public NoPlateRecord selectTempPlateInfo(String openid) {
		return noPlateMapper.select(openid);
	}
	@Override
	public String findOpenStauts(Map<String,String> params){
		return noPlateMapper.findOpenStauts(params);
	}
}

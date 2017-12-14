package com.park.h5business.service.aliparking;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/14
 * @function 支付宝停车系统SPI接口
 */
@Repository(value="spiService")
public interface SPIService {

	/**
	 * 获取车牌号
	 * @param auth_code
	 * @param car_id
	 * @return
	 */
	String getCarNumber(String auth_code, String car_id);

}

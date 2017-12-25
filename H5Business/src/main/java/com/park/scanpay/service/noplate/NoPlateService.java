/**
 * 
 */
package com.park.scanpay.service.noplate;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

/**
 * @author fangct created on 2017年12月15日
 */
@Repository(value = "noPlateService")
public interface NoPlateService {

	/**
	 * 跳转到无车牌进/出
	 * @param parkid
	 * @param type
	 * @param channelid
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String redirect2NoPlateEnEx(String parkid, String type, String channelid, String ver, String useragent, Model model);
	/**
	 * 跳转到输车牌
	 * @param parkid
	 * @param type
	 * @param channelid
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String redirect2GetPlate(String parkid, String useragent, Model model);
	/**
	 * 无牌车进场
	 * @param code
	 * @param parkid
	 * @param channelid
	 * @param type
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String entering(String alicode, String code, String parkid, String channelid, String type, String ver, String useragent,
			Model model);
	/**
	 * 无牌车出场
	 * @param code
	 * @param parkid
	 * @param channelid
	 * @param type
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String exiting(String alicode, String code, String parkid, String channelid, String type, String ver, String useragent,
			Model model);
}

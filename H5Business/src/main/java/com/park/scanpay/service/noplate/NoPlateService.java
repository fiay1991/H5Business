/**
 * 
 */
package com.park.scanpay.service.noplate;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

/**
 * @author fangct create on 2017年12月15日
 */
@Repository(value = "noPlateService")
public interface NoPlateService {

	/**
	 * 跳转到无车牌进/出
	 * 
	 * @param parkid
	 * @param type
	 * @param channelid
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String redirect2NoPlateEnEx(String parkid, String type, String channelid, String ver, String useragent,
			Model model);

	/**
	 * 跳转到输车牌
	 * 
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
	 * 
	 * @param code
	 * @param parkid
	 * @param channelid
	 * @param type
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String entering(String alicode, String code, String parkid, String channelid, String type, String ver,
			String useragent, Model model);

	/**
	 * 无牌车出场
	 * 
	 * @param code
	 * @param parkid
	 * @param channelid
	 * @param type
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String exiting(String alicode, String code, String parkid, String channelid, String type, String ver,
			String useragent, Model model);

	/**
	 * 无牌车入场开闸命令下发
	 * 
	 * @param parkid
	 * @param channelId
	 * @param parkName
	 * @param tempPlate
	 * @param enterTime
	 * @param openid
	 * @param model
	 * @return
	 */
	public String sendEnter(String parkid, String en_channelId, String parkName, String tempPlate, String enterTime,
			String openid, Model model);

	/**
	 * 无牌车离场开闸命令下发
	 * 
	 * @param parkid
	 * @param ex_channelId
	 * @param tempPlate
	 * @param exitTime
	 * @param openid
	 * @param model
	 * @return
	 */
	public String sendExit(String parkid, String ex_channelId, String tempPlate, String exitTime, String openid,
			Model model);

	/**
	 * 无牌车预缴费，根据openid获取车牌
	 * 
	 * @param code
	 * @param parkid
	 * @param ver
	 * @param useragent
	 * @param model
	 * @return
	 */
	public String noPlatePrepay(String alicode, String code, String parkid, String useragent, Model model);
}

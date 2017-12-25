/**
 * 
 */
package com.park.scanpay.tools;

/**
 * @author fangct 
 * created on 2017年12月15日
 */
public class TempPlateTools {
	
	private static final String prefix = "临P";
	
	/**
	 * 生成临时车牌，规则：prefix + 当前秒
	 * @return
	 */
	public static String createTempPlate(){
		String tempPlate = prefix + DateTools.phpNowDate();
		return tempPlate;
	}
}

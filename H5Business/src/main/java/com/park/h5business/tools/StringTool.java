package com.park.h5business.tools;


public class StringTool {
	
	public static boolean isNotNull(Object object){
		if (null == object) {
			return false;
		}
		return true;
	}
	
	public static boolean isNotNull(String parm){
		if (null == parm || "".equals(parm)) {
			return false;
		}
		return true;
	}

}

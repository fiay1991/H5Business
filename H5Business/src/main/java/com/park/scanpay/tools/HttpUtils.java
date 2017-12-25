package com.park.scanpay.tools;

import com.park.base.common.HttpTools;
import com.park.base.common.constants.PublicKeyConstants;
import com.park.scanpay.config.ProjectConfig;

/**
 * 
 * @author WangYuefei
 * @function 继承baseCommon中的HttpTools
 * 
 */
public class HttpUtils extends HttpTools {

	// 向CloudCore发送请求
	public static String post2CloudCore(Object params, String url) {
		return HttpTools.pidPost(params, url, PublicKeyConstants.CloudCore, ProjectConfig.ScanPay);
	}
	
	// 向PayCenter发送请求
	public static String post2PayCenter(Object params, String url) {
		return HttpTools.pidPost(params, url, PublicKeyConstants.PayCenter, ProjectConfig.ScanPay);
	}
	
}

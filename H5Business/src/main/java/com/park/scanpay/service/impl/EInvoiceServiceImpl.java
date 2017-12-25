package com.park.scanpay.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.base.common.HttpTools;
import com.park.scanpay.config.EInvoiceConfig;
import com.park.scanpay.profile.URLProfile;
import com.park.scanpay.response.EIPublicResponse;
import com.park.scanpay.service.EInvoiceService;
import com.park.scanpay.tools.BackResultTools;
import com.park.scanpay.tools.Base64Tools;
import com.park.scanpay.tools.DateChangeTools;
import com.park.scanpay.tools.SignTools;
import com.park.scanpay.tools.StringTool;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="einvoiceServiceImpl")
public class EInvoiceServiceImpl implements EInvoiceService {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private URLProfile urlProfile;
	@Override
	public String getCode(String orderNum,Model model,HttpServletResponse response) {
		logger.info("请求开发票的接口.....");
		String msg="抱歉开票失败，请稍后重试。";//默认错误信息提示
		String page="h5_einvoiceerror";//默认错误信息提示
		if (StringTool.isNotNull(orderNum)) {
			
		    /**
		     * 调取开票的接口
		     */
			try {
				String result =getEInvioceUrl(orderNum);
				logger.info("请求开票接口返回结果："+result);
				if (StringUtils.isNotBlank(result)) {
					 EIPublicResponse eIPublicResponse=DateChangeTools.gson2bean(result, EIPublicResponse.class);
					 if ("200".equals(eIPublicResponse.getCode())) {
						 String urlBase64 =eIPublicResponse.getData().getInvoiceUrl();
						 logger.info("请求开票接口返回结果url："+Base64Tools.decode(urlBase64));
						 response.sendRedirect(Base64Tools.decode(urlBase64));
						// return "redirect:"+Base64Tools.decode(urlBase64);
						 return null;
					 }else {
						 return BackResultTools.setValue(page, msg,orderNum,"order_num", model);
					 }
				}
			} catch (Exception e) {
				e.printStackTrace();
				//msg+="(服务器异常)";
				return BackResultTools.setValue(page, msg,orderNum,"order_num", model);
			}
		}else {
			logger.error("参数为空.."+"orderNum="+orderNum);
			//msg+="(参数为空)";
			return BackResultTools.setValue(page, msg,orderNum,"order_num", model);
		}
		return null;
	}
	
	
	public String getEInvioceUrl(String tradeNo){
		String requestUrl =urlProfile.getEINVOICE();
		Map<String, String> params =new HashMap<String, String>();
		params.put("tradeNo", tradeNo);
		Map<String,String> headers = getHeaders(params,requestUrl);
		String sparams =DateChangeTools.bean2gson(params);
		String result=HttpTools.HttpClientPost(requestUrl, sparams, headers);
		return result;
		
	}
	/**
	 * 签名认证  header中需要传入的参数
	 */
	public Map<String, String> getHeaders(Map<String, String> params,String url){  
	    String time = String.valueOf(System.currentTimeMillis()/1000);
	    String projectId = EInvoiceConfig.P_ID;
		Map<String, String> headers = new HashMap<String, String>(); 
		String linkparams=DateChangeTools.createLinkString(params);
		System.out.println("加密之前："+time+url+"?"+linkparams);
		String authorization=SignTools.encrypt(time+url+"?"+linkparams,EInvoiceConfig.P_KEY);
		headers.put("Timestamp",time);  
		headers.put("Authorization",authorization);  
		headers.put("Projectid",projectId);  
		return headers;  
	}  
	

}

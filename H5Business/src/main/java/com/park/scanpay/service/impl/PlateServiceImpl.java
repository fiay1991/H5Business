package com.park.scanpay.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.scanpay.factory.DestributeFactory;
import com.park.scanpay.profile.URLProfile;
import com.park.scanpay.service.BusinessService;
import com.park.scanpay.service.PlateService;
import com.park.scanpay.tools.BackResultTools;
import com.park.scanpay.tools.StringTool;

@Repository(value="plateServiceImpl")
public class PlateServiceImpl implements PlateService{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	URLProfile urlProfile;
	
	@Resource(name="destributeFactory")
	private DestributeFactory destributeFactory;
	
	@Override
	public String platepay(String parkid, String plate, String useragent, Model model) {
		String msg="抱歉支付失败，请稍后重试";//默认错误信息提示
		String page="h5_error";//默认跳转页面
		/*** 验证参数 */
		if (null == plate || "".equals(plate) || 
				null == parkid || "".equals(parkid)) {
			logger.error("参数为空.. plate="+plate);
			msg+="(参数为空)";
			return BackResultTools.setValue(page, msg, model);
		}
		String urlparams="";
		try {
			if (StringTool.isNotNull(plate)) {
				plate = URLEncoder.encode(plate, "UTF-8");
			}
			useragent = URLEncoder.encode(useragent, "UTF-8");
			urlparams = "parkid="+parkid+"&plate="+plate+"&useragent="+useragent;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BusinessService bs = destributeFactory.create(useragent);
		String self_url=urlProfile.getREDIRURL()+"?"+urlparams;
		return "redirect:"+bs.redirectURL(self_url);
	}
}

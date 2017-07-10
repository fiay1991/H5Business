package com.park.h5business.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.h5business.dao.OrderInfoDao;
import com.park.h5business.factory.DestributeFactory;
import com.park.h5business.service.BusinessService;
import com.park.h5business.service.PlateService;
import com.park.h5business.tools.BackResultTools;
import com.park.h5business.tools.StringTool;

@Repository(value="plateServiceImpl")
public class PlateServiceImpl implements PlateService{
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="destributeFactory")
	private DestributeFactory destributeFactory;
	
	@Resource(name="orderInfoDaoImpl")
	private OrderInfoDao orderInfoDao;
	
	@Override
	public String platepay(String parkid, String plate, String useragent, Model model) {
		String msg="抱歉支付失败，请稍后重试";//默认错误信息提示
		String page="h5_error";//默认跳转页面
		/*** 验证参数 */
		if (null == plate || "".equals(plate)) {
			logger.error("参数为空.. plate="+plate);
			msg+="(参数为空)";
			return BackResultTools.setValue(page, msg, model);
		}
		//判断parkid是否为空，如果为空，从数据库查询parkid
		if(parkid == null || parkid.equals("") || parkid.equals("null")){
			parkid = orderInfoDao.getParkIdByPlate(plate);
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
		return "redirect:"+bs.redirectURL(urlparams);
	}
}

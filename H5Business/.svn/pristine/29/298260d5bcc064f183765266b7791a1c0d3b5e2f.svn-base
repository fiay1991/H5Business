package com.park.scanpay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.scanpay.config.constants.NoPlateConstants;
import com.park.scanpay.service.PullOrderService;
import com.park.scanpay.service.impl.noplate.NoPlateServiceImpl;

@Controller
@RequestMapping("/pull")
public class PullOrderController {
	
	@Resource(name="pullOrderServiceImpl")
	private PullOrderService pullOrderService;
	
	@Resource(name="noPlateServiceImpl")
	private NoPlateServiceImpl noPlateServiceImpl;
	
	/**
	 * 扫码二维码   根据情况跳转到相应得页面 
	 * 本请求为get请求  
	 * @return
	 */
	@RequestMapping(value="/pullorder",produces = "text/html;charset=UTF-8")
	public String pullorder(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String ticketid=request.getParameter("ticketid");//票/卡号
		String plate =request.getParameter("plate");//车牌号
		String ver=request.getParameter("ver");//版本号
		String code=request.getParameter("code");//微信的code
		String alicode = request.getParameter("auth_code");//支付宝的授权code
		String useragent =request.getParameter("useragent");
		/**
		 * 根据订单情况处理
		 */
		String result =pullOrderService.pullorder(alicode, code,parkid, ticketid, plate, ver,useragent,model);
		return result;	
	}
	
	/**
	 * 扫码二维码   无牌车进出场处理 
	 * 本请求为get请求  
	 * @return
	 */
	@RequestMapping(value="/noplate",produces = "text/html;charset=UTF-8")
	public String noplate(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String channelid=request.getParameter("channelid");//票/卡号
		String type =request.getParameter("type");//无牌车进/出类型
		String ver=request.getParameter("ver");//版本号
		String code=request.getParameter("code");//微信的code
		String alicode = request.getParameter("auth_code");//支付宝的授权code
		String useragent =request.getParameter("useragent");
		/**
		 * 根据订单情况处理
		 */
		if((NoPlateConstants.进场.getValue() + "").equals(type)){
			return noPlateServiceImpl.entering(alicode, code,parkid, channelid, type, ver,useragent,model);
		}else if((NoPlateConstants.出场.getValue() + "").equals(type)){
			return noPlateServiceImpl.exiting(alicode, code,parkid, channelid, type, ver,useragent,model);
		}else{
			return noPlateServiceImpl.noPlatePrepay(alicode, code, parkid, useragent, model);
		}
	}
}

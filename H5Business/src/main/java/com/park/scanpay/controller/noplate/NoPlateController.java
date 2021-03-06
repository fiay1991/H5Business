package com.park.scanpay.controller.noplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.scanpay.service.impl.noplate.NoPlateServiceImpl;

@Controller
@RequestMapping("/noplate")
public class NoPlateController {
	
	@Resource(name="noPlateServiceImpl")
	private NoPlateServiceImpl noPlateServiceImpl;
	
	/**
	 * 无牌车进场下发
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sendenter",produces = "text/html;charset=UTF-8")
	public String sendenter(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String en_channelId=request.getParameter("channelId");
		String parkName =request.getParameter("parkName");
		String tempPlate=request.getParameter("tempPlate");
		String enterTime=request.getParameter("enterTime");
		String openid=request.getParameter("openid");
		
		return noPlateServiceImpl.sendEnter(parkid, en_channelId, parkName, tempPlate, enterTime, openid, model);
	}
	
	/**
	 * 无牌车出场下发
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sendexit",produces = "text/html;charset=UTF-8")
	public String sendexit(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String ex_channelId=request.getParameter("channelId");//通道编号
		String tempPlate =request.getParameter("tempPlate");//临时车牌号
		String exitTime=request.getParameter("exitTime");//出场时间
		String openid=request.getParameter("openid");
		
		return noPlateServiceImpl.sendExit(parkid, ex_channelId, tempPlate, exitTime, openid, model);	
	}
	
}

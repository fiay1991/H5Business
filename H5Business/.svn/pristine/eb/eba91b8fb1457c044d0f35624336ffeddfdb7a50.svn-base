package com.park.h5business.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.h5business.service.PullOrderService;

@Controller
@RequestMapping("/pull")
public class PullOrderController {
	
	@Resource(name="pullOrderServiceImpl")
	private PullOrderService pullOrderService;
	
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
		String useragent =request.getParameter("useragent");
		/**
		 * 根据订单情况处理
		 */
		String result =pullOrderService.pullorder(code,parkid, ticketid, plate, ver,useragent,model);
		return result;	
	}
	
}

package com.park.scanpay.controller.noplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.scanpay.service.PullOrderService;

@Controller
@RequestMapping("/noplate")
public class NoPlateController {
	
	@Resource(name="pullOrderServiceImpl")
	private PullOrderService pullOrderService;
	
	/**
	 * 扫码二维码   无牌车进场
	 * 本请求为get请求  
	 * @return
	 */
	@RequestMapping(value="/entering",produces = "text/html;charset=UTF-8")
	public String enter(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String ticketid=request.getParameter("ticketid");//票/卡号
		String plate =request.getParameter("plate");//车牌号
		String ver=request.getParameter("ver");//版本号
		String code=request.getParameter("code");//微信的code
		String alicode=request.getParameter("auth_code");//支付宝的auth_code
		String useragent =request.getParameter("useragent");
		/**
		 * 根据订单情况处理
		 */
		String result =pullOrderService.pullorder(alicode, code,parkid, ticketid, plate, ver,useragent,model);
		return result;	
	}
	
	/**
	 * 扫码二维码   无牌车出场处理 
	 * 本请求为get请求  
	 * @return
	 */
	@RequestMapping(value="/exiting",produces = "text/html;charset=UTF-8")
	public String exit(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String ticketid=request.getParameter("ticketid");//票/卡号
		String plate =request.getParameter("plate");//车牌号
		String ver=request.getParameter("ver");//版本号
		String code=request.getParameter("code");//微信的code
		String alicode=request.getParameter("auth_code");//支付宝的auth_code
		String useragent =request.getParameter("useragent");
		/**
		 * 根据订单情况处理
		 */
		String result =pullOrderService.pullorder(alicode, code,parkid, ticketid, plate, ver,useragent,model);
		return result;	
	}
	
}

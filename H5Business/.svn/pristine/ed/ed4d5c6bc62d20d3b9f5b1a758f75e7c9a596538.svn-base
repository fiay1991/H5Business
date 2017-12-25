package com.park.scanpay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.scanpay.service.EInvoiceService;

@Controller
@RequestMapping("/einvoice")
public class EInvoiceController {
	@Resource(name="einvoiceServiceImpl")
	private EInvoiceService eInvoiceService;
	/**
	 * 开具发票
	 * 本请求为get请求  
	 * @return
	 */
	@RequestMapping(value="/getCode",produces = "text/html;charset=UTF-8")
	public String getCode(HttpServletRequest request,HttpServletResponse response,Model model){
		/**
		 * 获取参数
		 */
		String order_num =request.getParameter("order_num");// 停车场id
		String result=eInvoiceService.getCode(order_num,model,response);
		return result;	
	}
	/**
	 * 开票失败后返回到成功页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/success")
	public String getSuccess(HttpServletRequest request,Model model){
		String order_num =request.getParameter("order_num");// 
		model.addAttribute("order_num",order_num);
		return "h5_success";
	}
}

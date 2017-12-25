package com.park.scanpay.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 通用页面跳转
 * @author WangYuefei
 *
 */
@Controller
public class ForwardController {
	
	@RequestMapping(value="/plate",produces = "text/html;charset=UTF-8")
	public String toPlate(HttpServletRequest request, Model model){
		String parkid = request.getParameter("parkid");
		model.addAttribute("parkid", parkid);
		return "h5_plate";
	}
	
	@RequestMapping(value="/platenew",produces = "text/html;charset=UTF-8")
	public String toPlateNew(HttpServletRequest request, Model model){
		String parkid = request.getParameter("parkid");
		model.addAttribute("parkid", parkid);
		return "h5_platenew";
	}
	
	@RequestMapping(value="/noplate",produces = "text/html;charset=UTF-8")
	public String toNoPlate(HttpServletRequest request, Model model){
		String plate = request.getParameter("plate");
		model.addAttribute("plate", plate);
		return "h5_noplate";
	}
}

package com.park.scanpay.controller.aliparking;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.scanpay.service.PlateService;
import com.park.scanpay.service.aliparking.SPIService;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/14
 * @function 支付宝停车系统SPI接口
 * 
 */
@Controller
@RequestMapping(value="/payment")
public class SPIController {
	
	@Resource(name="spiServiceImpl")
	private SPIService spiService;
	
	@Resource(name ="plateServiceImpl")
	private PlateService plateService;

	@RequestMapping(value="/query", produces="text/html;charset=UTF-8")
	public String paymentQuery(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 从请求中获取auth_code和车辆ID
		String auth_code = request.getParameter("auth_code");
		String car_id = request.getParameter("car_id");
		System.out.println("** 支付宝停车调用SPI接口，参数：auth_code = " + auth_code 
				+ "，car_id = " + car_id + "，parking_id = " + request.getParameter("parking_id"));
		String carNumber = spiService.getCarNumber(auth_code, car_id);
		String userAgent = "pre_Alipay_next";
		if(null == carNumber || "".equals(carNumber)) {
			return plateService.platepay("", carNumber, userAgent, model);
		}
		return null;
	}
	
}

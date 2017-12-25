package com.park.scanpay.controller.cardpay;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.scanpay.service.cardpay.CardPayService;

/**
 * 
 * @author WangYuefei
 *
 */
@Controller
@RequestMapping("/cardpay")
public class CardPayController {
	
	@Resource(name="cardPayServiceImpl")
	private CardPayService cardPayService;
	
	/**
	 * 刷卡支付下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/pay", produces="text/html;charset=UTF-8")
	public String pay(HttpServletRequest request,HttpServletResponse response) {
		String order_num = request.getParameter("order_num");
		String auth_code = request.getParameter("auth_code");
		String pay_way = request.getParameter("pay_way");
		String ipAddress = request.getRemoteAddr();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("order_num", order_num);
		paramMap.put("auth_code", auth_code);
		paramMap.put("pay_way", pay_way);
		paramMap.put("ipAddress", ipAddress);
		return cardPayService.pay(paramMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/query", produces="text/html;charset=UTF-8")
	public String query(HttpServletRequest request,HttpServletResponse response) {
		String order_num_pay = request.getParameter("order_num_pay");
		return cardPayService.query(order_num_pay);
	}
	
}

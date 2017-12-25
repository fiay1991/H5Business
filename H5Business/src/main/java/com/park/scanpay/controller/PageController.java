package com.park.scanpay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 通用页面跳转
 * @author Eric
 *
 */
@Controller
public class PageController {
	@RequestMapping("/{pageDir}_{pageName}")
	public String toPage(@PathVariable String pageDir,@PathVariable String pageName){
		return pageDir+"_"+pageName;// -> /h5_success.html
	}
}

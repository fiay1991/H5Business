package com.park.scanpay.tools;

import java.util.Map;

import org.springframework.ui.Model;

public class BackResultTools {
	/**
	 * 返回
	 * @param page  返回的页面
	 * @param object 返回的对象
	 * @param object_name 返回的对象名称
	 * @return
	 */
	public static String setValue(String page,Object object,String object_name,Model model){
		model.addAttribute(object_name,object);
		return page;
	}
	/**
	 * 返回
	 * @param page  返回的页面
	 * @param object 返回的对象
	 * @param object_name 返回的对象名称
	 * @return
	 */
	public static String setValue(String page,Map<String, Object> map,Model model){
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			model.addAttribute(entry.getKey(),entry.getValue());
		}
		return page;
	}
	/**
	 * 返回
	 * @param page  返回的页面
	 * @param msg   返回的信息
	 * @return
	 */
	public static String setValue(String page,String msg,Model model){
		model.addAttribute("msg",msg);
		return page;
	}
	
	/**
	 * 返回
	 * @param page  返回的页面
	 * @param msg   返回的信息
	 * @return
	 */
	public static String setValue(String page,String msg,Object object,String object_name,Model model){
		model.addAttribute("msg",msg);
		model.addAttribute(object_name,object);
		return page;
	}
	/**
	 * 返回
	 * @param page  返回的页面
	 * @return
	 */
	public static String setValue(String page){
		return page;
	}

}

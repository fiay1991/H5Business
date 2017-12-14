package com.park.h5business.tools;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DateChangeTools{
	private static Logger logger = Logger.getLogger(DateChangeTools.class);
	
	/***
	 * gson 转bean
	 * @param params
	 * @param object
	 * @param gson
	 * @return
	 */
	public static <T> T gson2bean(String params,Class<T> clazz){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			 gson =new GsonBuilder().serializeNulls().create();
		try {
			return gson.fromJson(params, clazz);
		} catch (Exception e) {
			logger.error("***  请求参数转换错误");
			logger.error("***  参数为"+params);
			logger.error("** "+e);
			return null;
		}
	}
	
	/***
	 * gson 转list
	 * @param params
	 * @param object
	 * @param gson
	 * @return
	 */
	public static <T> T gson2List(String params,Type type){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		try {
			return gson.fromJson(params, type);
		} catch (Exception e) {
			logger.error("***  请求参数转换错误");
			logger.error("** "+e);
			return null;
		}
	}
	/**
	 * bean转gson
	 * @param <T>
	 */
	public static <T> String bean2gson(Object object){
		Gson gson =new Gson();
		return gson.toJson(object);
	}
	
	/**
	 * fastjson   json 转bean
	 * @return 
	 */
//	public static <T> T fastjson2bean(String params,Class<T> clazz){
//		return 	JSON.parseObject(params, clazz);
//	}
//	/**
//	 * fastjson   bean转json 
//	 */
//	public static <T> String bean2json(Object object){
//		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
//	}
	/** 
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串  
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}else {
				if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
					prestr = prestr + key + "=" + value;
				} else {
					prestr = prestr + key + "=" + value + "&";
				}
			}
		}
		return prestr;
	}
	/** 
     * 将json格式封装的字符串数据转换成java中的Map数据 
	 * @param <T>
     * @return 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Map<String, String> json2Map(String params) {
        Gson gson =new Gson();
        return (Map)gson.fromJson(params, new TypeToken<T>() {}.getType());
    }

	/**
	 * bean转有序gson
	 * @param <T>
	 */
	public static String map2SortJSON(Map<String, String> map){
		StringBuffer resultString = new StringBuffer();
		resultString.append('{');
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}else {
				resultString.append('"');
				resultString.append(key);
				resultString.append('"');
				resultString.append(':');
				if(isNumeric(value) && value.length() < 12) {
					resultString.append(value);
				}else {
					resultString.append('"');
					resultString.append(value);
					resultString.append('"');
				}
				if(i < keys.size()-1) {
					resultString.append(',');
				}
			}
		}
		resultString.append('}');
		return resultString.toString();
	}
	
	/**
     * 匹配是否包含数字
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     * @return
     * @author yutao
     * @date 2016年11月14日下午7:41:22
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}

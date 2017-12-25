package com.park.scanpay.tools;

import java.util.List;

import com.park.scanpay.config.constants.CodeConstants;
import com.park.scanpay.domain.response.ObjectListResponse;
import com.park.scanpay.domain.response.ObjectResponse;
import com.park.scanpay.domain.response.Response;


public class ResultTools {

	/**
	 * 返回
	 * @return
	 */
	public static String setResponse(String code){
		
		Response response =new Response();
		response.setCode(code);
		response.setMsg(CodeConstants.getName(code));
		return DateChangeTools.bean2gson(response);	
	}
	/**
	 * 
	 * @param code 
	 * @param object
	 * @return
	 */
	public static String setObjectResponse(String code,Object data){
		
		ObjectResponse response =new ObjectResponse();
		response.setCode(code);
		response.setMsg(CodeConstants.getName(code));
		response.setData(data);
		return DateChangeTools.bean2gson(response);	
	}
	/**
	 * 
	 * @param code 
	 * @param object
	 * @return
	 */
	public static String setObjectListResponse(String code,List<Object> data){
		
		ObjectListResponse response =new ObjectListResponse();
		response.setCode(code);
		response.setMsg(CodeConstants.getName(code));
		response.setData(data);;
		return DateChangeTools.bean2gson(response);	
	}
}

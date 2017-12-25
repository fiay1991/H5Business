package com.park.scanpay.response;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/14
 * @function 解析PayCenter返回的信息
 *
 */
public class PayCenterObject {
	
	private String result_code;
	private String content;
	private String token;
	private Object object;
	
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}

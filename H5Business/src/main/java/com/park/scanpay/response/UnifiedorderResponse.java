package com.park.scanpay.response;

public class UnifiedorderResponse {
	
	private String prepay_id;
	//private String code_url;
	private String appId;
	
	private String timeStamp;
	
	private String nonceStr;
	
	private String signType;
	
	private String paySign;
	
	
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	//	public String getCode_url() {
//		return code_url;
//	}
//	public void setCode_url(String code_url) {
//		this.code_url = code_url;
//	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	

}

package com.park.scanpay.request;

public class UnifiedorderRequest {
	
	private String body;//支付描述
	private String trade_no;//系统订单号
	private String total_fee;
	private String parkid;
	private String trade_type;//支付平台类型
	private String terminal_type;//支付设备
	private String auth_code;//刷卡支付用户授权码
	private String create_ip;
	private String openid;
	private String userid;
	private String accesstoken;
	private String return_url;
	private String notify_url;//异步通知回调地址（支付宝的手机网页支付用到）
	private String ali_user_id;//支付宝用户id
	private String ali_access_token;//支付宝accessToken
	
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getAli_user_id() {
		return ali_user_id;
	}
	public void setAli_user_id(String ali_user_id) {
		this.ali_user_id = ali_user_id;
	}
	public String getAli_access_token() {
		return ali_access_token;
	}
	public void setAli_access_token(String ali_access_token) {
		this.ali_access_token = ali_access_token;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getParkid() {
		return parkid;
	}
	public void setParkid(String parkid) {
		this.parkid = parkid;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTerminal_type() {
		return terminal_type;
	}
	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getCreate_ip() {
		return create_ip;
	}
	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}
	
}

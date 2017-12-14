package com.park.h5business.response;

/**
 * 
 * @author WangYuefei
 *
 */
public class TokenReponse {
	
	private String access_token, user_id, alipay_user_id, expires_in, 
		re_expires_in, refresh_token;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAlipay_user_id() {
		return alipay_user_id;
	}

	public void setAlipay_user_id(String alipay_user_id) {
		this.alipay_user_id = alipay_user_id;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRe_expires_in() {
		return re_expires_in;
	}

	public void setRe_expires_in(String re_expires_in) {
		this.re_expires_in = re_expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	@Override
	public String toString() {
		return "TokenReponse [access_token=" + access_token + ", user_id=" + user_id + ", alipay_user_id="
				+ alipay_user_id + ", expires_in=" + expires_in + ", re_expires_in=" + re_expires_in
				+ ", refresh_token=" + refresh_token + "]";
	}
}

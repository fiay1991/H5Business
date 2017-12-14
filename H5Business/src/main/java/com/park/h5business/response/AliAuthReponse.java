package com.park.h5business.response;

/**
 * 
 * @author WangYuefei
 *
 */
public class AliAuthReponse {

	private TokenReponse alipay_system_oauth_token_response;
	
	private String sign;

	public TokenReponse getAlipay_system_oauth_token_response() {
		return alipay_system_oauth_token_response;
	}

	public void setAlipay_system_oauth_token_response(TokenReponse alipay_system_oauth_token_response) {
		this.alipay_system_oauth_token_response = alipay_system_oauth_token_response;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "AliAuthReponse [alipay_system_oauth_token_response=" + alipay_system_oauth_token_response + ", sign="
				+ sign + "]";
	}
}

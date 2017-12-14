package com.park.h5business.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="URLProfile")
public class URLProfile {


	private String PHPSERVER;
	private String UnifiedOrder;
	private String PHPORDERSERVER;
	private String REDIRURL;
	private String WEIXINAPPID;
	private String WEIXINAPPSECRET;
	private String FINDORDER;
	private String RETURNURL;
	private String WXRETURNURL;
	private String ALIPAYAPPID;
	private String ALIPAYPRIVATEKEY;
	private String ALIPAYALIPUBLICKEY;
	private String EINVOICE;
	
	public String getALIPAYAPPID() {
		return ALIPAYAPPID;
	}
	public void setALIPAYAPPID(String aLIPAYAPPID) {
		ALIPAYAPPID = aLIPAYAPPID;
	}
	public String getALIPAYPRIVATEKEY() {
		return ALIPAYPRIVATEKEY;
	}
	public void setALIPAYPRIVATEKEY(String aLIPAYPRIVATEKEY) {
		ALIPAYPRIVATEKEY = aLIPAYPRIVATEKEY;
	}
	public String getALIPAYALIPUBLICKEY() {
		return ALIPAYALIPUBLICKEY;
	}
	public void setALIPAYALIPUBLICKEY(String aLIPAYALIPUBLICKEY) {
		ALIPAYALIPUBLICKEY = aLIPAYALIPUBLICKEY;
	}
	public String getPHPSERVER() {
		return PHPSERVER;
	}
	public void setPHPSERVER(String pHPSERVER) {
		PHPSERVER = pHPSERVER;
	}
	public String getUnifiedOrder() {
		return UnifiedOrder;
	}
	public void setUnifiedOrder(String unifiedOrder) {
		UnifiedOrder = unifiedOrder;
	}
	public String getPHPORDERSERVER() {
		return PHPORDERSERVER;
	}
	public void setPHPORDERSERVER(String pHPORDERSERVER) {
		PHPORDERSERVER = pHPORDERSERVER;
	}
	
	public String getREDIRURL() {
		return REDIRURL;
	}
	public void setREDIRURL(String rEDIRURL) {
		REDIRURL = rEDIRURL;
	}
	public String getWEIXINAPPID() {
		return WEIXINAPPID;
	}
	public void setWEIXINAPPID(String wEIXINAPPID) {
		WEIXINAPPID = wEIXINAPPID;
	}
	public String getWEIXINAPPSECRET() {
		return WEIXINAPPSECRET;
	}
	public void setWEIXINAPPSECRET(String wEIXINAPPSECRET) {
		WEIXINAPPSECRET = wEIXINAPPSECRET;
	}
	public String getFINDORDER() {
		return FINDORDER;
	}
	public void setFINDORDER(String fINDORDER) {
		FINDORDER = fINDORDER;
	}
	public String getRETURNURL() {
		return RETURNURL;
	}
	public void setRETURNURL(String rETURNURL) {
		RETURNURL = rETURNURL;
	}
	public String getEINVOICE() {
		return EINVOICE;
	}
	public void setEINVOICE(String eINVOICE) {
		EINVOICE = eINVOICE;
	}
	public String getWXRETURNURL() {
		return WXRETURNURL;
	}
	public void setWXRETURNURL(String wXRETURNURL) {
		WXRETURNURL = wXRETURNURL;
	}
	
	
}

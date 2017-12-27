package com.park.scanpay.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author WangYuefei
 * @function 对应配置文件中CloudCore的链接
 * 
 */
@Component
@ConfigurationProperties(prefix="CCProfile")
public class CCProfile {
	
	// order_info相关
	private String findOrderByCarInfo;
	private String findOrderByOrderNum;
	private String modOrderPriceInfo;
	private String modOrderBasicInfo;
	// order_pay_record相关
	private String addOrderPayRecord;
	private String modOrderPayRecordBasic;
	private String modOrderPayRecordStatus;
	private String findOrderPayRecord;
	// 缴费查询
	private String paymentQuery;
	
	//park相关
	private String findParkInfo;
	
	//系统相关
	private String generateFieldValue;
	
	public String getPaymentQuery() {
		return paymentQuery;
	}
	public void setPaymentQuery(String paymentQuery) {
		this.paymentQuery = paymentQuery;
	}
	public String getFindOrderByCarInfo() {
		return findOrderByCarInfo;
	}
	public void setFindOrderByCarInfo(String findOrderByCarInfo) {
		this.findOrderByCarInfo = findOrderByCarInfo;
	}
	public String getFindOrderByOrderNum() {
		return findOrderByOrderNum;
	}
	public void setFindOrderByOrderNum(String findOrderByOrderNum) {
		this.findOrderByOrderNum = findOrderByOrderNum;
	}
	public String getModOrderPriceInfo() {
		return modOrderPriceInfo;
	}
	public void setModOrderPriceInfo(String modOrderPriceInfo) {
		this.modOrderPriceInfo = modOrderPriceInfo;
	}
	public String getModOrderBasicInfo() {
		return modOrderBasicInfo;
	}
	public void setModOrderBasicInfo(String modOrderBasicInfo) {
		this.modOrderBasicInfo = modOrderBasicInfo;
	}
	public String getAddOrderPayRecord() {
		return addOrderPayRecord;
	}
	public void setAddOrderPayRecord(String addOrderPayRecord) {
		this.addOrderPayRecord = addOrderPayRecord;
	}
	public String getModOrderPayRecordBasic() {
		return modOrderPayRecordBasic;
	}
	public void setModOrderPayRecordBasic(String modOrderPayRecordBasic) {
		this.modOrderPayRecordBasic = modOrderPayRecordBasic;
	}
	public String getModOrderPayRecordStatus() {
		return modOrderPayRecordStatus;
	}
	public void setModOrderPayRecordStatus(String modOrderPayRecordStatus) {
		this.modOrderPayRecordStatus = modOrderPayRecordStatus;
	}
	public String getFindOrderPayRecord() {
		return findOrderPayRecord;
	}
	public void setFindOrderPayRecord(String findOrderPayRecord) {
		this.findOrderPayRecord = findOrderPayRecord;
	}
	public String getFindParkInfo() {
		return findParkInfo;
	}
	public void setFindParkInfo(String findParkInfo) {
		this.findParkInfo = findParkInfo;
	}
	public String getGenerateFieldValue() {
		return generateFieldValue;
	}
	public void setGenerateFieldValue(String generateFieldValue) {
		this.generateFieldValue = generateFieldValue;
	}
}

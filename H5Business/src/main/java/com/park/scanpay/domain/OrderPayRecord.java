package com.park.scanpay.domain;

public class OrderPayRecord {
	
	private int id;
	private String parkId;
	private String orderNum;
	private String tradeNo;
	private String costBefore;//应收费用
	private String costAfter;//实收费用
	private String discountAmount;//優惠金額
	private String payStatus;//状态 1.未付费2.已付费 
	private String payWay;//付款方式 (手机交费)(0余额支付[使用无优卡余额] 1 支付宝支付[支付宝支付] ；3：微信支付；4：连连（网银）；5：易宝（网银),6：友贝,10,余额支付宝，30余额微信，40余额连连，50余额易宝,60:余额友贝
	private String payChannel;
	private String describe;
	private String payTerminal;
	private String userAccount;
	private String payTime;
    private String createTime;
    private String updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getCostBefore() {
		return costBefore;
	}
	public void setCostBefore(String costBefore) {
		this.costBefore = costBefore;
	}
	public String getCostAfter() {
		return costAfter;
	}
	public void setCostAfter(String costAfter) {
		this.costAfter = costAfter;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPayTerminal() {
		return payTerminal;
	}
	public void setPayTerminal(String payTerminal) {
		this.payTerminal = payTerminal;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}

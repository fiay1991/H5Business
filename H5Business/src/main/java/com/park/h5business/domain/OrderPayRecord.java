package com.park.h5business.domain;

import java.math.BigDecimal;

public class OrderPayRecord {
	
	private int id;
	private String  order_num;
	private String  order_num_pay;
	private BigDecimal cost_before;//应收费用
	private BigDecimal cost_after;//实收费用
	private BigDecimal discount_amount;//優惠金額
	private int pay_status;//状态 1.未付费2.已付费 
	private int pay_way;//付款方式 (手机交费)(0余额支付[使用无优卡余额] 1 支付宝支付[支付宝支付] ；3：微信支付；4：连连（网银）；5：易宝（网银),6：友贝,10,余额支付宝，30余额微信，40余额连连，50余额易宝,60:余额友贝
    private int create_time;
    private int update_time;
    private String openid;
    private String transaction_id;//微信返回的订单号
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public BigDecimal getCost_before() {
		return cost_before;
	}
	public void setCost_before(BigDecimal cost_before) {
		this.cost_before = cost_before;
	}
	public BigDecimal getCost_after() {
		return cost_after;
	}
	public void setCost_after(BigDecimal cost_after) {
		this.cost_after = cost_after;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
	public int getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}
	public int getPay_status() {
		return pay_status;
	}
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}
	public int getPay_way() {
		return pay_way;
	}
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOrder_num_pay() {
		return order_num_pay;
	}
	public void setOrder_num_pay(String order_num_pay) {
		this.order_num_pay = order_num_pay;
	}
	public BigDecimal getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(BigDecimal discount_amount) {
		this.discount_amount = discount_amount;
	}

}

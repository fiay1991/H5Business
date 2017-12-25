package com.park.scanpay.response;

public class OrderResponse {
	
	private String orderid;//云端订单号
	private String local_orderid;//本地订单号
	private int is_user;//是否无忧用户（0：否；1：是）
	private int status;//缴费状态（1：已缴费；2：未缴费；3：需补缴）
	private int pay_type;//缴费方式（1：无忧支付；2:现金支付）
	private float total_price;//补缴金额（总金额）  -----应收金额
	private float payed_price;//已缴金额
	private float discount_price;//折扣金额 (云端折扣)
	private float unpay_price;//未缴金额   --- 实收金额
	private int total_count;//已计费总时长，单位秒
	private int pay_time;//最后支付时间
	private int service_status;
	
	public OrderResponse(){
		
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getLocal_orderid() {
		return local_orderid;
	}
	public void setLocal_orderid(String local_orderid) {
		if (null ==local_orderid) {
			local_orderid=" ";
		}
		this.local_orderid = local_orderid;
	}
	public int getIs_user() {
		return is_user;
	}
	public void setIs_user(int is_user) {
		this.is_user = is_user;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPay_type() {
		return pay_type;
	}
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	public float getPayed_price() {
		return payed_price;
	}
	public void setPayed_price(float payed_price) {
		this.payed_price = payed_price;
	}
	public float getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(float discount_price) {
		this.discount_price = discount_price;
	}
	public float getUnpay_price() {
		return unpay_price;
	}
	public void setUnpay_price(float unpay_price) {
		this.unpay_price = unpay_price;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getPay_time() {
		return pay_time;
	}
	public void setPay_time(int pay_time) {
		this.pay_time = pay_time;
	}
	
	public int getService_status() {
		return service_status;
	}
	public void setService_status(int service_status) {
		this.service_status = service_status;
	}
	@Override  
    public String toString()   
    {  
        return "{orderid=" + orderid + ",local_orderid=" + local_orderid  
                + ",pay_type=" + pay_type + ",is_user=" + is_user +",status="+status+",total_price="+total_price
                +",payed_price="+payed_price+",discount_price="+discount_price+",unpay_price="+unpay_price+",total_count="+total_count+",pay_time="+pay_time+"}";  
    }  
}

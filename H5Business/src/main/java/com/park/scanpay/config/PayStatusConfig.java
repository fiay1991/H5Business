package com.park.scanpay.config;

/**
 * @author WangYuefei
 * 时间：2017年11月28日
 * 功能：支付的状态
 * 备注：与PayCenter保持一致
 */

public class PayStatusConfig {
	
	/**
	 * 未支付
	 */
	public static final String INTRADING = "INTRADING";
	public static final String INTRADINGOFINT = "1";
	/**
	 * 支付失败
	 */
	public static final String NPAY = "FAIL";
	public static final String NPAYOFINT = "3";
	
	/**
	 * 支付成功
	 */
	public static final String YPAY = "SUCCESS";
	public static final String YPAYOFINT = "2";
	
	/**
	 * 交易关闭
	 */
	public static final String OFFPAY = "OFF";
}

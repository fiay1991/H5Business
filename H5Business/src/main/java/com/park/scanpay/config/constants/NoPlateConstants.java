package com.park.scanpay.config.constants;

public enum NoPlateConstants {

	/**
	 * 类型
	 */
	进场(1), 出场(2),预缴费(3),

	/**
	 * 状态
	 */
	待入场(0), 已入场(1), 待支付(2), 待离场(3), 已离场(4);

	private final int value;

	private NoPlateConstants(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

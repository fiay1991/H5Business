/**
 * 
 */
package com.park.scanpay.domain.noplate;

/**
 * @author fangct 
 * created on 2017年12月15日
 */
public class NoPlateRecord {

	private int id;
	private int park_id;
	private String channel_id;
	private String temp_plate;
	private int enter_time;
	private int exit_time;
	private String openid;
	private int status;//0: 待进场，1: 已进场，2: 待支付，3: 待离场，4: 已离场
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPark_id() {
		return park_id;
	}
	public void setPark_id(int park_id) {
		this.park_id = park_id;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getTemp_plate() {
		return temp_plate;
	}
	public void setTemp_plate(String temp_plate) {
		this.temp_plate = temp_plate;
	}
	public int getEnter_time() {
		return enter_time;
	}
	public void setEnter_time(int enter_time) {
		this.enter_time = enter_time;
	}
	public int getExit_time() {
		return exit_time;
	}
	public void setExit_time(int exit_time) {
		this.exit_time = exit_time;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "NoPlateRecord [id=" + id + ", park_id=" + park_id + ", channel_id=" + channel_id + ", temp_plate="
				+ temp_plate + ", enter_time=" + enter_time + ", exit_time=" + exit_time + ", openid=" + openid
				+ ", status=" + status + "]";
	}
	
}

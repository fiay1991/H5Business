package com.park.scanpay.response;

/**
 * 
* @Description: 无车牌出场显示字段
* @author fangct
* @date 2017年12月22日 下午1:37:56
 */
public class NoPlateExitResponse {

	private String parkName;//车场名称
	private String channelId;//通道编号
	private String tempPlate;//临时车牌
	private String enterTime;//入场时间
	private String exitTime;//出场时间
	private String payedPrice;//已缴费
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getTempPlate() {
		return tempPlate;
	}
	public void setTempPlate(String tempPlate) {
		this.tempPlate = tempPlate;
	}
	public String getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	public String getExitTime() {
		return exitTime;
	}
	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}
	public String getPayedPrice() {
		return payedPrice;
	}
	public void setPayedPrice(String payedPrice) {
		this.payedPrice = payedPrice;
	}
	@Override
	public String toString() {
		return "NoPlateExitResponse [parkName=" + parkName + ", channelId=" + channelId + ", tempPlate=" + tempPlate
				+ ", enterTime=" + enterTime + ", exitTime=" + exitTime + ", payedPrice=" + payedPrice + "]";
	}
}

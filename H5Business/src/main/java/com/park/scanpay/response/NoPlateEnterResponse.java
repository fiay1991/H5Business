package com.park.scanpay.response;

/**
 * 
* @Description: 无车牌进场显示字段
* @author fangct
* @date 2017年12月22日 下午1:37:56
 */
public class NoPlateEnterResponse {

	private String parkName;//车场名称
	private String channelId;//通道编号
	private String tempPlate;//临时车牌
	private String enterTime;//入场时间
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
	@Override
	public String toString() {
		return "NoPlateEnterResponse [parkName=" + parkName + ", channelId=" + channelId + ", tempPlate=" + tempPlate
				+ ", enterTime=" + enterTime + "]";
	}
}

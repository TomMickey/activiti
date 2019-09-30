package com.grgbanking;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3553200697096682418L;
	//原因
	private String reason;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//请假天数
	private Integer leaveDays;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "User [reason=" + reason + ", startTime=" + startTime + ", endTime=" + endTime + ", leaveDays="
				+ leaveDays + "]";
	}
	
	
}

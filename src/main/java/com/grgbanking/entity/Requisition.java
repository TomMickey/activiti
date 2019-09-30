package com.grgbanking.entity;

import java.io.Serializable;

public class Requisition implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4875609564228898393L;
	//申请时间
	private String date;
	//申请标题
	private String title;
	//申请操作 购买,维修,换购,调配,借调,退回
	private String operate;
	//申请实体
	private String object;
	//实体照片
	private String pictureUrl;
	//
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	@Override
	public String toString() {
		return "Requisition [date=" + date + ", title=" + title + ", operate=" + operate + ", object=" + object
				+ ", pictureUrl=" + pictureUrl + "]";
	}

}

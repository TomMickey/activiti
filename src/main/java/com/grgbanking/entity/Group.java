package com.grgbanking.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8696746685609663598L;
	
	//id
	private Integer id;
	
	//组名
	private String groupName;
	
	//说明
	private String desc;
	

}

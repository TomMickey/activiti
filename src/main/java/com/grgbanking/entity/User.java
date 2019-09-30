package com.grgbanking.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private Integer id;
	
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 用户地址
	 */
	private String address;

}

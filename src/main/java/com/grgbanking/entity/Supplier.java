package com.grgbanking.entity;

import java.io.Serializable;

/**
 *     供应商实体
 * @author NI
 *
 */
public class Supplier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2045617289250394713L;
	//供应商名称
	private String supplierName;
	//供应商地址
	private String address;
	//供应商联系方式
	private String phone;
	//供应商报价单
	private Integer quotation;
	//供应商负责人
	private String chargeMan;
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getQuotation() {
		return quotation;
	}
	public void setQuotation(Integer quotation) {
		this.quotation = quotation;
	}
	public String getChargeMan() {
		return chargeMan;
	}
	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}
	@Override
	public String toString() {
		return "Supplier [supplierName=" + supplierName + ", address=" + address + ", phone=" + phone + ", quotation="
				+ quotation + ", chargeMan=" + chargeMan + "]";
	}

}

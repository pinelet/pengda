package com.pinelet.utp.manage.form;

import java.math.BigDecimal;

/**
 * 查询用表单实体
 * @author zjl
 *
 */
public class QueryForm extends ModelImpl {

	//人员ID
	private String pid;
	//卡号
	private String cardcode;
	//设备编号
	private String devicecode;
	//卡状态
	private String cardstatus;
	//持卡人姓名
	private String cardname;
	
	private String phone;
	
	private String address;
	
	private String gprscode;
	
	private String startdate;
	
	private String enddate;
	//交易类型
	private String transtype;
	
	private BigDecimal balance;
	
	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	public String getCardstatus() {
		return cardstatus;
	}

	public void setCardstatus(String cardstatus) {
		this.cardstatus = cardstatus;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGprscode() {
		return gprscode;
	}

	public void setGprscode(String gprscode) {
		this.gprscode = gprscode;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}

package com.pinelet.utp.core.service;

public class TransEntity implements DataEntity{
	
	//卡号
	private String cardno =  null;
	//消费金额
	private String sum = null;
	//余额
	private String balance = null;
	//交易时间
	private String transtime = null;

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTranstime() {
		return transtime;
	}

	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
}

package com.kingsun.core.Test;

import java.io.Serializable;

public class Information implements Serializable {

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public int getConsum() {
		return consum;
	}

	public void setConsum(int consum) {
		this.consum = consum;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	private static final long serialVersionUID = -8147274854803049776L;
	
	/**
	 * 卡号
	 */
	private String cardCode;
	
	/**
	 * 消费金额：分
	 */
	private int consum;
	
	/**
	 * 卡余额：分
	 */
	private int balance;
	
	/**
	 * 操作时间 格式yymmddMMss
	 */
	private String optime;

}

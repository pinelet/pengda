package com.pinelet.utp.manage.form;

import com.pinelet.utp.entity.Cardinfo;

public class CardForm extends Cardinfo {

	/**
	 * 统一主键验证
	 */
	private String id = null;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

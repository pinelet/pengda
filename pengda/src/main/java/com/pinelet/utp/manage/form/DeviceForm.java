package com.pinelet.utp.manage.form;

import com.pinelet.utp.entity.Deviceinfo;

public class DeviceForm extends Deviceinfo {

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

	/**
	 * 耗材总状态
	 * 当耗材各项指标有报警或失效时，此状态为有报警或有失效
	 */
	private String consumeStatus = null;

	public String getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}
}

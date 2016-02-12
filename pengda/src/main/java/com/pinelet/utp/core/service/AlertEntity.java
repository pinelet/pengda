package com.pinelet.utp.core.service;

public class AlertEntity implements DataEntity {

	
	//card通讯错误
	private boolean commAlert = false;
	//低压报警
	private boolean lowpressureAlert = false;
	//高液位报警
	private boolean highpositionAlert = false;
	//低液位报警
	private boolean lowpositionAlert = false;
	//制水
	private boolean makewater = false;
	//停售
	private boolean offsale = false;
	//售水
	private boolean onsale = false;
	
	private String operationTime = null;
	
	public boolean isCommAlert() {
		return commAlert;
	}
	public void setCommAlert(boolean commAlert) {
		this.commAlert = commAlert;
	}
	public boolean isLowpressureAlert() {
		return lowpressureAlert;
	}
	public void setLowpressureAlert(boolean lowpressureAlert) {
		this.lowpressureAlert = lowpressureAlert;
	}
	public boolean isHighpositionAlert() {
		return highpositionAlert;
	}
	public void setHighpositionAlert(boolean highpositionAlert) {
		this.highpositionAlert = highpositionAlert;
	}
	public boolean isLowpositionAlert() {
		return lowpositionAlert;
	}
	public void setLowpositionAlert(boolean lowpositionAlert) {
		this.lowpositionAlert = lowpositionAlert;
	}
	public boolean isMakewater() {
		return makewater;
	}
	public void setMakewater(boolean makewater) {
		this.makewater = makewater;
	}
	public boolean isOffsale() {
		return offsale;
	}
	public void setOffsale(boolean offsale) {
		this.offsale = offsale;
	}
	public boolean isOnsale() {
		return onsale;
	}
	public void setOnsale(boolean onsale) {
		this.onsale = onsale;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

}

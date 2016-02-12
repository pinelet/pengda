package com.pinelet.utp.entity;

public class AlertResult {

    private String gprscode;

    private String devicecode;
    
    private String model;
    
    private String ppdate;

    private String carbondate;

    private String rodate;

    private String minedate;
    
    private boolean cardcom;

    private boolean lowpressure;

    private boolean highposition;

    private boolean lowposition;

    private boolean makewater;

    private boolean offsale;

    private boolean onsale;

	public String getGprscode() {
		return gprscode;
	}

	public void setGprscode(String gprscode) {
		this.gprscode = gprscode;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPpdate() {
		return ppdate;
	}

	public void setPpdate(String ppdate) {
		this.ppdate = ppdate;
	}

	public String getCarbondate() {
		return carbondate;
	}

	public void setCarbondate(String carbondate) {
		this.carbondate = carbondate;
	}

	public String getRodate() {
		return rodate;
	}

	public void setRodate(String rodate) {
		this.rodate = rodate;
	}

	public String getMinedate() {
		return minedate;
	}

	public void setMinedate(String minedate) {
		this.minedate = minedate;
	}

	public boolean isCardcom() {
		return cardcom;
	}

	public void setCardcom(boolean cardcom) {
		this.cardcom = cardcom;
	}

	public boolean isLowpressure() {
		return lowpressure;
	}

	public void setLowpressure(boolean lowpressure) {
		this.lowpressure = lowpressure;
	}

	public boolean isHighposition() {
		return highposition;
	}

	public void setHighposition(boolean highposition) {
		this.highposition = highposition;
	}

	public boolean isLowposition() {
		return lowposition;
	}

	public void setLowposition(boolean lowposition) {
		this.lowposition = lowposition;
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
}

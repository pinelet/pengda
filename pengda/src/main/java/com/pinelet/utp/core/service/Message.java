package com.pinelet.utp.core.service;

public class Message {

	protected String statusdata;
	protected long timestamp;
	
	   public String getStatusdata() {
			return statusdata;
		}

		public void setStatusdata(String statusdata) {
			this.statusdata = statusdata;
		}
		
		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}


}
package cn.beecloud;

public class BCMSWapPayResult extends BCPayResult{
	
	private String phoneToken;
	
	private String responseMsg;
	
	public String getPhoneToken() {
		return phoneToken;
	}

	public void setPhoneToken(String phoneToken) {
		this.phoneToken = phoneToken;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}

package cn.beecloud;


/**
 * @author Ray
 * @since 2015/10/22
 */
public class BCMSWapPayResult extends BCPayResult{
	
	private String phoneToken;
	
	private String merTransDate;
	
	public String getPhoneToken() {
		return phoneToken;
	}

	public void setPhoneToken(String phoneToken) {
		this.phoneToken = phoneToken;
	}

	public String getMerTransDate() {
		return merTransDate;
	}

	public void setMerTransDate(String merTransDate) {
		this.merTransDate = merTransDate;
	}
}

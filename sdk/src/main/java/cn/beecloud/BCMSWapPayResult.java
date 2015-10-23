package cn.beecloud;


/**
 * @author Ray
 * @since 2015/10/22
 */
public class BCMSWapPayResult extends BCPayResult{
	
	private String phoneToken;
	
	private String channelTradeNo;
	
	public String getPhoneToken() {
		return phoneToken;
	}

	public void setPhoneToken(String phoneToken) {
		this.phoneToken = phoneToken;
	}

	public String getChannelTradeNo() {
		return channelTradeNo;
	}

	public void setChannelTradeNo(String channelTradeNo) {
		this.channelTradeNo = channelTradeNo;
	}
}

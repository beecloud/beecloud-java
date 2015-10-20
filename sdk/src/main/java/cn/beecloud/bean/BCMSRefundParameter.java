package cn.beecloud.bean;

public class BCMSRefundParameter extends BCRefundParameter{
	
	private String cause;
	
	private String appuser;
	
	public BCMSRefundParameter(String billNo, String refundNo, Integer refundFee) {
		super(billNo, refundNo, refundFee);
	}

	public String getCause() {
		return cause;
	}
	
	/**
	 * @param cause 退款理由
	 * (必填)
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getAppuser() {
		return appuser;
	}
	
	/**
	 * @param appuser 申请人
	 * (必填)
	 */
	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}
}

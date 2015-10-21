package cn.beecloud.bean;

public class BCMSWebRefundBean {
	
	private String refundId;
	
	private String merchantId;
	
	private String payorderId;
	
	private String merOrderId;
	
	private String amount;
	
	private String refundAmount;
	
	private String state;
	
	private String applyDate;
	
	private String startFlag;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayorderId() {
		return payorderId;
	}

	public void setPayorderId(String payorderId) {
		this.payorderId = payorderId;
	}

	public String getMerOrderId() {
		return merOrderId;
	}

	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}
}

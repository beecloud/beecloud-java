package cn.beecloud.bean;

import cn.beecloud.BCQueryResult;
import cn.beecloud.BCEumeration.RESULT_TYPE;

public class BCMSWapQueryResult {
	
	private String errMsg;
	
	private String errDetail;
	
	private RESULT_TYPE type;
	
	private String txnType;
	
	private String txnStat;
	
	private String amount;
	
	private String merTransTime;
	
	private String merOrderId;

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnStat() {
		return txnStat;
	}

	public void setTxnStat(String txnStat) {
		this.txnStat = txnStat;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMerTransTime() {
		return merTransTime;
	}

	public void setMerTransTime(String merTransTime) {
		this.merTransTime = merTransTime;
	}

	public String getMerOrderId() {
		return merOrderId;
	}

	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrDetail() {
		return errDetail;
	}

	public void setErrDetail(String errDetail) {
		this.errDetail = errDetail;
	}

	public RESULT_TYPE getType() {
		return type;
	}

	public void setType(RESULT_TYPE type) {
		this.type = type;
	}
}

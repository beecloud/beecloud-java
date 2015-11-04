package cn.beecloud;

import cn.beecloud.BCEumeration.RESULT_TYPE;


/**
 * @author Ray
 * @since 2015/10/22
 */
public class BCMSWapPayResult extends BCPayResult{
	
	private String objectId;
	
	private String refNo;
	
	private BCMSWapBill wapBill;
	
	private String merTransDate;

	public BCMSWapBill getWapBill() {
		return wapBill;
	}

	public void setWapBill(BCMSWapBill wapBill) {
		this.wapBill = wapBill;
	}
	
	public void setToken(String token) {
		this.wapBill.setToken(token);
	}
	
	public void setVerifyCode(String verifyCode) {
		this.wapBill.setVerifyCode(verifyCode);
	}
	
	public BCMSWapPayResult(String errMsg, RESULT_TYPE type) {
		super.setType(type);
		super.setErrMsg(errMsg);
	}

	public BCMSWapPayResult(RESULT_TYPE type) {
		super.setType(type);
	}

	public BCMSWapPayResult() {
	}

	public String getMerTransDate() {
		return merTransDate;
	}

	public void setMerTransDate(String merTransDate) {
		this.merTransDate = merTransDate;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
}

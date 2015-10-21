package cn.beecloud.bean;

import cn.beecloud.BCPayResult;
import cn.beecloud.BCEumeration.RESULT_TYPE;

public class BCMSRefundResult {
	
private String sucessMsg;
	
	private String errMsg;
	
	private String errDetail;
	
	private String objectId;
	
	private RESULT_TYPE type;
	
	private String refundId;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getSucessMsg() {
		return sucessMsg;
	}

	public void setSucessMsg(String sucessMsg) {
		this.sucessMsg = sucessMsg;
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

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public RESULT_TYPE getType() {
		return type;
	}

	public void setType(RESULT_TYPE type) {
		this.type = type;
	}
}

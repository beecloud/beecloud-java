package cn.beecloud.bean;

import java.util.LinkedList;
import java.util.List;

import cn.beecloud.BCEumeration.RESULT_TYPE;

/**
 * @author Ray
 * @since 2015/10/22
 */
public class BCMSWebQueryResult {
	
	private String errMsg;
	
	private String errDetail;
	
	private RESULT_TYPE type;
	
	private Integer count;
	
	private BCMSWebOrderBean msBean;
	
	private List<BCMSWebOrderBean> msBeanList = new LinkedList<BCMSWebOrderBean>();

	private List<BCMSWebRefundBean> msRefundBeanList = new LinkedList<BCMSWebRefundBean>();
	
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

	public BCMSWebOrderBean getMsBean() {
		return msBean;
	}

	public void setMsBean(BCMSWebOrderBean msBean) {
		this.msBean = msBean;
	}

	public List<BCMSWebOrderBean> getMsBeanList() {
		return msBeanList;
	}

	public void setMsBeanList(List<BCMSWebOrderBean> msBeanList) {
		this.msBeanList = msBeanList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<BCMSWebRefundBean> getMsRefundBeanList() {
		return msRefundBeanList;
	}

	public void setMsRefundBeanList(List<BCMSWebRefundBean> msRefundBeanList) {
		this.msRefundBeanList = msRefundBeanList;
	}
}

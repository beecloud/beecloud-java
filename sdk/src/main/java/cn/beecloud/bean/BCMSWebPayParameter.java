package cn.beecloud.bean;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCMSWebPayParameter extends BCPayParameter {
	
	private String subject;
	
	public BCMSWebPayParameter(PAY_CHANNEL channel, Integer totalFee,
			String billNo, String title, String subject) {
		super(channel, totalFee, billNo, title);
		this.subject =  subject;
	}
	
	/**
	 * 访问字段  {@link #subject}
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject 商品种类，该参数，是从民生电商处获得
	 * (必填)
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}

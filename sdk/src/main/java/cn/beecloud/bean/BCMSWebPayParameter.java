package cn.beecloud.bean;

import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCMSWebPayParameter {
	
	private PAY_CHANNEL channel;
	
	private Integer totalFee;
	
	private String billNo;
	
	private String title;
	
	private Map<String, Object> optional;
	
	private String returnUrl;
	
	private String subject;
	
	public BCMSWebPayParameter(PAY_CHANNEL channel, Integer totalFee,
			String billNo, String title, String subject) {
		this.channel = channel;
		this.totalFee = totalFee;
		this.billNo = billNo;
		this.title = title;
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
	
	/**
	 * 访问字段  {@link #channel}
	 */
	public PAY_CHANNEL getChannel() {
		return channel;
	}
	
	/**
	 * @param channel 渠道类型，为MS_WEB
	 * (必填)
	 */
	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}
	
	/**
	 * 访问字段  {@link #totalFee}
	 */
	public Integer getTotalFee() {
		return totalFee;
	}
	
	/**
	 * @param totalFee 订单总金额， 必须是正整数，单位为分，最低100分
	 * (必填)
	 */
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	
	/**
	 * 访问字段  {@link #billNo}
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * @param billNo 商户订单号，8到30位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
	 * (必填)
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/**
	 * 访问字段  {@link #title}
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title 订单标题，UTF8编码格式，32个字节内，最长支持16个汉字
	 * (必填)
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 访问字段  {@link #optional}
	 */
	public Map<String, Object> getOptional() {
		return optional;
	}
	
	/**
	 * @param optional 附加数据，用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * (选填)
	 */
	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}
	
	/**
	 * 访问字段  {@link #returnUrl}
	 */
	public String getReturnUrl() {
		return returnUrl;
	}
	
	/**
	 * @param returnUrl 同步返回页面，支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径,不要以包含localhost，否则渠道会认为非法，当为MS_WEB时，必填
	 * (选填)
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}

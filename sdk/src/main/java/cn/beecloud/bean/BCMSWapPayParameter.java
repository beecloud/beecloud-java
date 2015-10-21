package cn.beecloud.bean;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCMSWapPayParameter extends BCPayParameter {
	
	private String custId;
	
	private String cardNo;
	
	private String custName;
	
	private String custIdNo;
	
	private String custIdType;
	
	private String bankNo;
	
	private String phoneNo;
	
	private String phoneVerCode;
	
	private String phoneToken;
	
	private String subject;
	
	public BCMSWapPayParameter(PAY_CHANNEL channel, Integer totalFee,
			String billNo, String title) {
		super(channel, totalFee, billNo, title);
		// TODO Auto-generated constructor stub
	}

	public String getCustId() {
		return custId;
	}
	
	/**
	 * @param custId 客户号
	 * (必填)
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCardNo() {
		return cardNo;
	}
	
	/**
	 * @param cardNo 卡号
	 * (必填)
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getCustName() {
		return custName;
	}
	
	/**
	 * @param custName 持卡人姓名
	 * (必填)
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustIdNo() {
		return custIdNo;
	}
	
	/**
	 * @param custIdNo 持卡人证件号
	 * (必填)
	 */
	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public String getCustIdType() {
		return custIdType;
	}
	
	/**
	 * @param custIdType 持卡人证件类型
	 * (必填)
	 */
	public void setCustIdType(String custIdType) {
		this.custIdType = custIdType;
	}

	public String getBankNo() {
		return bankNo;
	}
	
	/**
	 * @param bankNo 支付银行号
	 * (必填)
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}
	
	/**
	 * @param phoneNo 手机号
	 * (必填)
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPhoneVerCode() {
		return phoneVerCode;
	}
	
	/**
	 * @param phoneVerCode 手机验证码，持卡人收到的手机验证码， 鉴权令牌已返回，发起支付时必填
	 * (选填)
	 */
	public void setPhoneVerCode(String phoneVerCode) {
		this.phoneVerCode = phoneVerCode;
	}

	public String getPhoneToken() {
		return phoneToken;
	}
	
	/**
	 * @param phoneToken 手机校验码令牌， 鉴权令牌已返回，发起支付时必填
	 * (选填)
	 */
	public void setPhoneToken(String phoneToken) {
		this.phoneToken = phoneToken;
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

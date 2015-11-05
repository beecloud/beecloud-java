package cn.beecloud;

import cn.beecloud.BCEumeration.CARD_TYPE;

public class CardInfo {
	private CARD_TYPE cardType;
	
	private String custIdNo;
	
	private String custName;
	
	private String custIdType;
	
	private String cardNo;
	
	private String expiredDate;
	
	private String cvv2;

	public CARD_TYPE getCardType() {
		return cardType;
	}
	
	/**
	 * @param cardType 卡类型， 借记卡：DIRECT或者信用卡：CREDIT
	 * （必填）
	 */
	public void setCardType(CARD_TYPE cardType) {
		this.cardType = cardType;
	}

	public String getCustIdNo() {
		return custIdNo;
	}
	
	/**
	 * @param custIdNo 证件号,身份证号，军官证等
	 * (必填)
	 */
	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public String getCustName() {
		return custName;
	}
	
	/**
	 * @param custName 客户姓名,UTF8编码格式，32个字节内，最长支持16个汉字
	 * (必填)
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustIdType() {
		return custIdType;
	}
	
	
	/**
	 * @param custIdType 客户证件类型:
	 * 0	身份证类型
	 * 1	护照类型
	 * 2	军官证
	 * 3	士兵证
	 * 4	港澳台通行证
	 * 5	临时身份证
	 * 6	户口本
	 * 7	其他类型证件
	 * 9	警官证
	 * 12	外国人居留证
	 * 15	回乡证
	 * 16	企业营业执照
	 * 17	法人代码证
	 * 18	台胞证
	 * (必填)
	 */
	public void setCustIdType(String custIdType) {
		this.custIdType = custIdType;
	}

	public String getCardNo() {
		return cardNo;
	}
	
	/**
	 * @param cardNo 借记卡和信用卡的卡号
	 * (必填)
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiredDate() {
		return expiredDate;
	}
	
	/**
	 * @param expiredDate 信用卡有效日期, 信用卡的有效日期,使用信用卡时必填
	 * (选填)
	 */
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCvv2() {
		return cvv2;
	}
	
	/**
	 * @param cvv2， 信用卡验证码, 信用卡背后的三位验证码，使用信用卡时必填
	 * (选填)
	 */
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
}

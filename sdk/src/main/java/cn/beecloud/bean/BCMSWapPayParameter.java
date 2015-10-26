package cn.beecloud.bean;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCMSWapPayParameter extends BCMSWebPayParameter {
	
	private String custId;
	
	private String cardNo;
	
	private String custName;
	
	private String custIdNo;
	
	private String custIdType;
	
	private String bankNo;
	
	private String expiredDate;
	
	private String cvv2;
	
	private String phoneNo;
	
	private String phoneVerCode;
	
	private String phoneToken;
	
	private String flag;  
	
	
	public BCMSWapPayParameter(PAY_CHANNEL channel, Integer totalFee,
			String billNo, String title, String subject) {
		super(channel, totalFee, billNo, title, subject);
	}
	
	/**
	 * 访问字段  {@link #custId}
	 */
	public String getCustId() {
		return custId;
	}
	
	/**
	 * @param custId 客户号,28位以内的数字或字母的组合
	 * (必填)
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	/**
	 * 访问字段  {@link #cardNo}
	 */
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
	
	/**
	 * 访问字段  {@link #custName}
	 */
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
	

	/**
	 * 访问字段  {@link #custIdNo}
	 */
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
	
	/**
	 * 访问字段  {@link #custIdType}
	 */
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
	
	/**
	 * 访问字段  {@link #bankNo}
	 */
	public String getBankNo() {
		return bankNo;
	}
	
	/**
	 * @param bankNo 银行编号,需要快捷支付的银行号
	 * 03080000	招商银行
	 * 01020000	中国工商银行
	 * 01030000	中国农业银行
	 * 01050000	中国建设银行
	 * 01040000	中国银行
	 * 03100000	浦发银行
	 * 03010000	中国交通银行
	 * 03050000	中国民生银行
	 * SDB（暂不支持）	深圳发展银行
	 * 03060000	广东发展银行
	 * 03020000	中信银行
	 * 03040000	华夏银行
	 * 03090000	兴业银行
	 * 14055810	广州农村商业银行
	 * 04135810	广州银行
	 * CUPS（暂不支持）	中国银联
	 * 65012900	上海农村商业银行
	 * POST（暂不支持）	中国邮政
	 * 04031000	北京银行
	 * 03170000	渤海银行
	 * 14181000	北京农商银行
	 * 04240001	南京银行
	 * 03030000	中国光大银行
	 * 26150704	东亚银行
	 * 01033320	宁波银行
	 * 04233310	杭州银行
	 * 05105840	平安银行
	 * 04403600	徽商银行
	 * 03160000	浙商银行
	 * 04012900	上海银行
	 * 01000000	中国邮政储蓄银行
	 * 05213000	江苏银行
	 * 04202220	大连银行
	 * (必填)
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	/**
	 * 访问字段  {@link #phoneNo}
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	
	/**
	 * @param phoneNo 手机号,手机11位号码
	 * (必填)
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	/**
	 * 访问字段  {@link #phoneVerCode}
	 */
	public String getPhoneVerCode() {
		return phoneVerCode;
	}
	
	/**
	 * @param phoneVerCode 一般为6位，是民生电商发给用户的**快捷支付阶段，该值必填**
	 * (选填)
	 */
	public void setPhoneVerCode(String phoneVerCode) {
		this.phoneVerCode = phoneVerCode;
	}
	
	/**
	 * 访问字段  {@link #phoneToken}
	 */
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
	 * 访问字段  {@link #expiredDate}
	 */
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
	
	/**
	 * 访问字段  {@link #cvv2}
	 */
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
	
	/**
	 * @param channel 渠道类型, 为MS_WAP
	 * (选填)
	 */
	@Override
	public void setChannel(PAY_CHANNEL channel) {
		super.setChannel(channel);
	}
	
	/**
	 * 访问字段  {@link #flag}
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * @param flag 快捷鉴权或是支付的标志字段,"sign"代表鉴权，"pay"代表支付
	 * (必填)
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
}

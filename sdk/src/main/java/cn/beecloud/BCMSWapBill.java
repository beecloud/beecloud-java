package cn.beecloud;

import java.util.Map;

import cn.beecloud.BCEumeration.MS_WAP_TYPE;
import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCMSWapBill {
	
	private MS_WAP_TYPE type;
	
	private String bankNo;
	
	private String phoneNo;
	
	private String custId;
	
	private String billNo;
	
	private Integer totalFee;
	
	private String title;
	
	private String subject;
	
	private PAY_CHANNEL channel;
	
	private Map<String, Object> optional;
	
	private CardInfo cardInfo = new CardInfo();
	
	private String token;
	
	private String verifyCode;
	
	private String flag;

	public MS_WAP_TYPE getType() {
		return type;
	}
	
	/**
	 * @param type 类型，无卡或者有卡（MS_WAP_TYPE.CARD或者MS_WAP_TYPE.SAVED_CARD）
	 * （必填）
	 */
	public void setType(MS_WAP_TYPE type) {
		this.type = type;
	}

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

	public String getCustId() {
		return custId;
	}
	
	/**
	 * @param custId 客户号,22位以内的数字或字母的组合
	 * (必填)
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

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

	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject 商品种类，该参数，是从民生电商处获得，发起快捷支付时必填
	 * (选填)
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public PAY_CHANNEL getChannel() {
		return channel;
	}
	
	/**
	 * @param channel 渠道类型, 固定为MS_WAP
	 * (必填)
	 */
	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}

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

	public CardInfo getCardInfo() {
		return cardInfo;
	}
	
	/**
	 * @param cardInfo 卡信息，当type为{@link MS_WAP_TYPE.CARD}时必填
	 * （选填）
	 */
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

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

	public String getToken() {
		return token;
	}
	
	/**
	 * @param token 手机校验码令牌，发起支付时必填
	 * (选填)
	 */
	public void setToken(String token) {
		this.token = token;
	}

	public String getVerifyCode() {
		return verifyCode;
	}
	
	/**
	 * @param verifyCode 一般为6位，是民生电商发给用户的短信验证码，该值必填
	 * (选填)
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public BCMSWapBill(String billNo, Integer totalFee, String title,
			PAY_CHANNEL channel, Map<String, Object> optional) {
		super();
		this.billNo = billNo;
		this.totalFee = totalFee;
		this.title = title;
		this.channel = channel;
		this.optional = optional;
	}

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

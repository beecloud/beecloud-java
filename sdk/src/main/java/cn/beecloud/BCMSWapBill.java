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

	public void setType(MS_WAP_TYPE type) {
		this.type = type;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public PAY_CHANNEL getChannel() {
		return channel;
	}

	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}

	public Map<String, Object> getOptional() {
		return optional;
	}

	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

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

	public void setFlag(String flag) {
		this.flag = flag;
	}
}

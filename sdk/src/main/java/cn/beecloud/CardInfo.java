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

	public void setCardType(CARD_TYPE cardType) {
		this.cardType = cardType;
	}

	public String getCustIdNo() {
		return custIdNo;
	}

	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustIdType() {
		return custIdType;
	}

	public void setCustIdType(String custIdType) {
		this.custIdType = custIdType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
}

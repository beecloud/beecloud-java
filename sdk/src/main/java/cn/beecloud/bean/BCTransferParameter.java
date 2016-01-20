package cn.beecloud.bean;

import java.util.Map;


/**
 * BC单笔代付 Created by bianjianjun on 16/1/8.
 */
public class BCTransferParameter {
    // 下发订单总金额 必须是正整数，单位为分
    private Integer totalFee;
    // 商户订单号 8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
    private String billNo;
    // 下发订单标题 UTF8编码格式，32个字节内，最长支持16个汉字
    private String title;
    // 交易源 UTF8编码格式，目前只能填写OUT_PC
    private String tradeSource;
    // 银行缩写编码
    private String bankCode;
    // 银行联行行号 需要向银行咨询
    private String bankAssociatedCode;
    // 银行全名
    private String bankFullName;
    // 银行卡类型 区分借记卡和信用卡 DE代表借记卡，CR代表信用卡，其他值为非法
    private String cardType;
    // 收款帐户类型 区分对公和对私 P代表私户，C代表公户，其他值为非法
    private String accountType;
    // 收款方的银行卡号
    private String accountNo;
    // 收款方的姓名或者单位名
    private String accountName;
    // 银行绑定的手机号，当需要手机收到银行入账信息时，该值必填，前提是该手机在银行有短信通知业务，否则收不到银行信息
    private String mobile;

    private Map<String, Object> optional;

    public BCTransferParameter() {};

    public BCTransferParameter(Integer totalFee, String billNo, String title, String tradeSource,
            String bankCode, String bankAssociatedCode, String bankFullName, String cardType,
            String accountType, String accountNo, String accountName, Map<String, Object> optional) {
        this.totalFee = totalFee;
        this.billNo = billNo;
        this.title = title;
        this.tradeSource = tradeSource;
        this.bankCode = bankCode;
        this.bankAssociatedCode = bankAssociatedCode;
        this.bankFullName = bankFullName;
        this.cardType = cardType;
        this.accountType = accountType;
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.optional = optional;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(String tradeSource) {
        this.tradeSource = tradeSource;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAssociatedCode() {
        return bankAssociatedCode;
    }

    public void setBankAssociatedCode(String bankAssociatedCode) {
        this.bankAssociatedCode = bankAssociatedCode;
    }

    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Map<String, Object> getOptional() {
        return optional;
    }

    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }

}

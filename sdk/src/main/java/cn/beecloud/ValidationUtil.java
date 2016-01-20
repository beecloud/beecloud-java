package cn.beecloud;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;
import cn.beecloud.bean.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 接口参数验证类
 * 
 * @author Ray
 * @version 0.1
 * @since 2015/6/11
 */
public class ValidationUtil {
    private final static String BILL_NO_FORMAT_INVALID = "billNo 是一个长度介于8至32字符的数字字母字符串！";

    private final static String TRANSFERS_BATCH_NO_FORMAT_INVALID = "batchNo 是一个长度在11到32个字符的数字字母字符串！";

    private final static String PAY_PARAM_EMPTY = "支付参数不能为空！";

    private final static String BATCH_REFUND_PARAM_EMPTY = "批量审核参数不能为空！";

    private final static String REFUND_PARAM_EMPTY = "退款参数不能为空！";

    private final static String QUERY_PARAM_EMPTY = "查询参数不能为空！";

    private final static String BILL_NO_EMPTY = "billNo 不能为空！";

    private final static String TRANSFERS_BATCH_NO_EMPTY = "批量打款batchNo 不能为空！";

    private final static String TRANSFER_PARAM_EMPTY = "transfer参数不能为空！";

    private final static String TRANSFER_CHANNEL_EMPTY = "单笔打款channel 不能为空!";

    private final static String TRANSFER_TRANSFER_NO_EMPTY = "单笔打款transferNo 不能为空！";

    private final static String TRANSFER_TOTAL_FEE_EMPTY = "单笔打款totalFee 不能为空！";

    private final static String TRANSFER_DESC_EMPTY = "单笔打款description 不能为空！";

    private final static String TRANSFER_USER_ID_EMPTY = "单笔打款channelUserId 不能为空！";

    private final static String TRANSFER_REDPACK_INFO_EMPTY = "微信红包redpackInfo 不能为空! ";

    private final static String TRANSFER_USER_NAME_EMPTY = "支付宝单笔打款channelUserName 不能为空！";

    private final static String TRANSFER_ACCOUNT_NAME_EMPTY = "支付宝单笔打款accountName 不能为空！";

    private final static String ALI_TRANSFER_NO_INVALID = "支付宝单笔打款transferNo 是一个长度在11到32个字符的数字字母字符串";

    private final static String WX_TRANSFER_TOTAL_FEE_INVALID = "微信打款金额不能小于1.00元，totalFee必须大于等于100!";

    private final static String WX_REDPACK_TOTAL_FEE_INVALID = "只能发放1.00块到200块钱的红包，totalFee范围必须在(100~20000)内";

    private final static String ALI_TRANSFER_TOTAL_FEE_INVALID = "支付宝单笔打款totalFee 必须大于 0！";

    private final static String TRANSFER_REDPACK_INFO_FIELD_EMPTY = "微信红包sendName、wishing、activityName 不能为空!";

    private final static String WX_TRANSFER_NO_INVALID = "微信单笔打款transferNo 是一个长度为10的数字！";

    private final static String TRANSFERS_PARAM_EMPTY = "批量打款参数不能为空！";

    private final static String TRANSFERS_CHANNEL_EMPTY = "批量打款channel 不能为空！";

    private final static String TRANSFERS_DATA_LIST_EMPTY = "批量打款transferDataList 不能为空！";

    private final static String TRANSFER_ID_EMPTY = "transferId 不能为空！";

    private final static String RECEIVER_ACCOUNT_EMPTY = "receiverAccount 不能为空！";

    private final static String RECEIVER_NAME_EMPTY = "receiverName 不能为空！";

    private final static String TRANSFER_FEE_EMPTY = "transferFee 不能为空！";

    private final static String TRANSFER_FEE_INVALID = "transferFee 必须大于0！";

    private final static String TRANSFER_NOTE_EMPTY = "transferNote 不能为空！";

    private final static String TRANSFERS_ACCOUNT_NAME_EMPTY = "accountName 不能为空！";

    private final static String TITLE_EMPTY = "title 不能为空！";

    private final static String TOTAL_FEE_EMPTY = "totalFee 不能为空！";

    private final static String REFUND_FEE_EMPTY = "refundFee 不能为空！";

    private final static String REFUND_FEE_INVALID = "refundFee 必须大于零！";

    private final static String QR_PAY_MODE_EMPTY = "qrPayMode 不能为空！";

    private final static String RETURN_URL_EMPTY = "returnUrl 不能为空！";

    private final static String REFUND_NO_EMPTY = "refundNo 不能为空！";

    private final static String BATCH_REFUND_AGREE_EMPTY = "批量审核agree不能为空！";

    private final static String BATCH_REFUND_CHANNEL_EMPTY = "批量审核channel不能为空!";

    private final static String BATCH_REFUND_ID_LIST_EMPTY = "批量审核ids不能为空！";

    private final static String CHANNEL_EMPTY = "channel 不能为空！";

    private final static String YEE_NOBANCARD_FACTOR_EMPTY = "cardNo, cardPwd, frqid 不能为空！";

    private final static String REFUND_NO_FORMAT_INVALID = "refundNo 是格式为当前日期加3-24位数字字母（不能为000）流水号的字符串！ ";

    private final static String TITLE_FORMAT_INVALID = "title 是一个长度不超过32字节的字符串！";

    private final static String LIMIT_FORMAT_INVALID = "limit 的最大长度为50！ 并且不能小于10！";

    private final static String OPENID_EMPTY = "openid 不能为空！";

    private final static String CHANNEL_INVALID_FOR_REFUND = "退款只支持WX, UN, ALI !";

    private final static String TRANSFER_ID_FORMAT_EMPTY = "transferId 是一个长度不超过32字符的数字字母字符串！";

    private final static String TRANSFERS_LIST_SIZE_INVALID = "transferDataList 长度不能超过1000！";

    private final static String TRANSFERS_CHANNEL_SUPPORT_INVALID = "批量打款仅支持ALI";

    private final static String BILL_TIME_OUT_ZERO = "billTimeout不能为0！";

    private final static String OBJECT_ID_EMPTY = "objectId 不能为空！";

    private final static String OBJECT_ID_INVALID = "objectId 只能包含数字、字母或者-";

    private final static String REFUND_UPDATE_CHANNEL_INVALID = "退款更新仅支持微信、百度、易宝、快钱！";

    private final static String INTERNATIONAL_PAY_PARAM_EMPTY = "境外支付参数不能为空！";

    private final static String CURRENCY_EMPTY = "currency不能为空！";

    private final static String CREDIT_CARD_INFO_EMPTY = "信用卡信息不能为空！";

    private final static String CREDIT_CARD_ID_EMPTY = "存储的信用卡ID不能为空！";

    private final static String PAYPAL_RETURN_URL_EMPTY = "PAYPAL直接支付returnUrl不能为空！";

    private final static String TRADE_SOURCE_EMPTY = "tradeSource 不能为空！";

    private final static String BANK_CODE_EMPTY = "bankCode 不能为空！";

    private final static String BANK_ASSOCIATED_CODE_EMPTY = "bankAssociatedCode 不能为空！";

    private final static String BANK_FULL_NAME_EMPTY = "bankFullName 不能为空！";

    private final static String CARD_TYPE_EMPTY = "cardType 不能为空！";

    private final static String ACCOUNT_TYPE_EMPTY = "accountType 不能为空！";

    private final static String ACCOUNT_NO_EMPTY = "accountNo 不能为空！";

    private final static String ACCOUNT_NAME_EMPTY = "accountName 不能为空！";

    private final static String OPTIONAL_EMPTY = "optional 不能为空！";

    static void validateQueryRefundStatus(PAY_CHANNEL channel, String refundNo) throws BCException {
        if (channel == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CHANNEL_EMPTY);
        } else if (!channel.equals(PAY_CHANNEL.BD) && !channel.equals(PAY_CHANNEL.WX)
                && !channel.equals(PAY_CHANNEL.YEE) && !channel.equals(PAY_CHANNEL.KUAIQIAN)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_UPDATE_CHANNEL_INVALID);
        } else if (StrUtil.empty(refundNo)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_EMPTY);
        }
    }

    static void validateBCTransfers(TransfersParameter para) throws BCException {
        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_PARAM_EMPTY);

        } else if (para.getChannel() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_CHANNEL_EMPTY);
        } else if (!para.getChannel().equals(PAY_CHANNEL.ALI)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_CHANNEL_SUPPORT_INVALID);
        } else if (para.getBatchNo() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_BATCH_NO_EMPTY);
        } else if (!para.getBatchNo().matches("[0-9A-Za-z]{11,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_BATCH_NO_FORMAT_INVALID);
        } else if (para.getAccountName() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_ACCOUNT_NAME_EMPTY);
        } else if (para.getTransferDataList() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_DATA_LIST_EMPTY);
        }
        for (ALITransferData data : para.getTransferDataList()) {
            if (StrUtil.empty(data.getTransferId())) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_ID_EMPTY);
            } else if (!data.getTransferId().matches("[0-9A-Za-z]{1,32}")) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_ID_FORMAT_EMPTY);
            } else if (StrUtil.empty(data.getReceiverAccount())) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), RECEIVER_ACCOUNT_EMPTY);
            } else if (StrUtil.empty(data.getReceiverName())) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), RECEIVER_NAME_EMPTY);
            } else if (StrUtil.empty(data.getTransferFee())) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_FEE_EMPTY);
            } else if (data.getTransferFee() <= 0) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_FEE_INVALID);
            } else if (StrUtil.empty(data.getTransferNote())) {
                throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                        RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_NOTE_EMPTY);
            }
        }
        if (para.getTransferDataList().size() > 1000) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFERS_LIST_SIZE_INVALID);
        }
    }

    static void validateBCPay(BCOrder para) throws BCException {

        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), PAY_PARAM_EMPTY);
        }
        if (para.getChannel() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CHANNEL_EMPTY);
        } else if (StrUtil.empty(para.getBillNo())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_EMPTY);
        } else if (StrUtil.empty(para.getTitle())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TITLE_EMPTY);
        } else if (StrUtil.empty(para.getTotalFee())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TOTAL_FEE_EMPTY);
        } else if (para.getBillTimeout() != null && para.getBillTimeout() == 0) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_TIME_OUT_ZERO);
        } else if (para.getBillNo() != null && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
        } else if (StrUtil.empty(para.getReturnUrl())
                && (para.getChannel().equals(PAY_CHANNEL.ALI_WEB)
                        || para.getChannel().equals(PAY_CHANNEL.ALI_QRCODE)
                        || para.getChannel().equals(PAY_CHANNEL.UN_WEB)
                        || para.getChannel().equals(PAY_CHANNEL.JD_WEB) || para.getChannel()
                        .equals(PAY_CHANNEL.JD_WAP))) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), RETURN_URL_EMPTY);
        } else if (para.getChannel().equals(PAY_CHANNEL.WX_JSAPI)
                && StrUtil.empty(para.getOpenId())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), OPENID_EMPTY);
        } else if (para.getChannel().equals(PAY_CHANNEL.ALI_QRCODE)
                && StrUtil.empty(para.getQrPayMode())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), QR_PAY_MODE_EMPTY);
        } else if (para.getChannel().equals(PAY_CHANNEL.YEE_NOBANKCARD)
                && (para.getCardNo() == null || para.getCardPwd() == null || para.getFrqid() == null)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), YEE_NOBANCARD_FACTOR_EMPTY);
        } else
            try {
                if (para.getTitle().getBytes("GBK").length > 32) {
                    throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                            RESULT_TYPE.PARAM_INVALID.name(), TITLE_FORMAT_INVALID);
                }
            } catch (UnsupportedEncodingException e) {
                if (para.getTitle().length() > 16) {
                    throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                            RESULT_TYPE.PARAM_INVALID.name(), TITLE_FORMAT_INVALID);
                }
            }
    }

    static void validateBCTransfer(BCTransferParameter para) throws BCException {

        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), PAY_PARAM_EMPTY);
        }
        if (StrUtil.empty(para.getBillNo())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_EMPTY);
        } else if (StrUtil.empty(para.getTitle())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TITLE_EMPTY);
        } else if (StrUtil.empty(para.getTotalFee())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TOTAL_FEE_EMPTY);
        } else if (para.getBillNo() != null && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
        } else if (StrUtil.empty(para.getTradeSource())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRADE_SOURCE_EMPTY);
        } else if (StrUtil.empty(para.getBankCode())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BANK_CODE_EMPTY);
        } else if (StrUtil.empty(para.getBankAssociatedCode())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BANK_ASSOCIATED_CODE_EMPTY);
        } else if (StrUtil.empty(para.getBankFullName())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BANK_FULL_NAME_EMPTY);
        } else if (StrUtil.empty(para.getCardType())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CARD_TYPE_EMPTY);
        } else if (StrUtil.empty(para.getAccountType())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), ACCOUNT_TYPE_EMPTY);
        } else if (StrUtil.empty(para.getAccountNo())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), ACCOUNT_NO_EMPTY);
        } else if (StrUtil.empty(para.getAccountName())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), ACCOUNT_NAME_EMPTY);
        }

    }

    static void validateBCRefund(BCRefund para) throws BCException {
        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_PARAM_EMPTY);
        } else if (StrUtil.empty(para.getBillNo())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_EMPTY);
        } else if (StrUtil.empty(para.getRefundFee())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_FEE_EMPTY);
        } else if (para.getRefundFee() <= 0) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_FEE_INVALID);
        } else if (StrUtil.empty(para.getRefundNo())) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_EMPTY);
        } else if (para.getChannel() != null && !para.getChannel().equals(PAY_CHANNEL.WX)
                && !para.getChannel().equals(PAY_CHANNEL.ALI)
                && !para.getChannel().equals(PAY_CHANNEL.UN)
                && !para.getChannel().equals(PAY_CHANNEL.YEE)
                && !para.getChannel().equals(PAY_CHANNEL.JD)
                && !para.getChannel().equals(PAY_CHANNEL.KUAIQIAN)
                && !para.getChannel().equals(PAY_CHANNEL.BD)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CHANNEL_INVALID_FOR_REFUND);
        } else if (!para.getRefundNo().startsWith(
                new SimpleDateFormat("yyyyMMdd").format(new Date()))) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_FORMAT_INVALID);
        } else if (!para.getRefundNo().substring(8, para.getRefundNo().length())
                .matches("[0-9A-Za-z]{3,24}")
                || para.getRefundNo().substring(8, para.getRefundNo().length()).matches("000")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_FORMAT_INVALID);
        } else if (!para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
        }
    }

    static void validateQueryBill(BCQueryParameter para) throws BCException {
        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), QUERY_PARAM_EMPTY);
        } else if (!StrUtil.empty(para.getBillNo())
                && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
        } else if (para.getLimit() != null && (para.getLimit() > 50 || para.getLimit() < 10)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), LIMIT_FORMAT_INVALID);
        }
    }

    static void validateQueryRefund(BCQueryParameter para) throws BCException {
        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), QUERY_PARAM_EMPTY);
        } else if (!StrUtil.empty(para.getBillNo())
                && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
        } else if (!StrUtil.empty(para.getRefundNo())
                && (!para.getRefundNo().substring(8, para.getRefundNo().length())
                        .matches("[0-9A-Za-z]{3,24}") || para.getRefundNo()
                        .substring(8, para.getRefundNo().length()).matches("000"))) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_FORMAT_INVALID);
        } else if (para.getLimit() != null && (para.getLimit() > 50 || para.getLimit() < 10)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), LIMIT_FORMAT_INVALID);
        }
    }

    static void validateQueryById(String objectId) throws BCException {
        if (objectId == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), OBJECT_ID_EMPTY);
        } else if (!objectId.matches("[0-9a-zA-Z\\-]+")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), OBJECT_ID_INVALID);
        }
    }

    static void validateBatchRefund(BCBatchRefund batchRefund) throws BCException {
        if (batchRefund == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BATCH_REFUND_PARAM_EMPTY);
        } else if (batchRefund.getAgree() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BATCH_REFUND_AGREE_EMPTY);
        } else if (batchRefund.getChannel() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BATCH_REFUND_CHANNEL_EMPTY);
        } else if (batchRefund.getIds() == null || batchRefund.getIds().size() == 0) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BATCH_REFUND_ID_LIST_EMPTY);
        }
    }

    static void validateBCTransfer(TransferParameter para) throws BCException {
        if (para == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_PARAM_EMPTY);
        } else if (para.getChannel() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_CHANNEL_EMPTY);
        } else if (para.getTransferNo() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_TRANSFER_NO_EMPTY);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.ALI_TRANSFER)
                && !para.getTransferNo().matches("[0-9A-Za-z]{11,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), ALI_TRANSFER_NO_INVALID);
        } else if (!para.getChannel().equals(TRANSFER_CHANNEL.ALI_TRANSFER)
                && !para.getTransferNo().matches("[0-9]{10}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), WX_TRANSFER_NO_INVALID);
        } else if (para.getTotalFee() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_TOTAL_FEE_EMPTY);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.WX_TRANSFER)
                && para.getTotalFee() < 100) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), WX_TRANSFER_TOTAL_FEE_INVALID);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.WX_REDPACK)
                && (para.getTotalFee() > 20000 || para.getTotalFee() < 100)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), WX_REDPACK_TOTAL_FEE_INVALID);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.ALI_TRANSFER)
                && para.getTotalFee() <= 0) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), ALI_TRANSFER_TOTAL_FEE_INVALID);
        } else if (para.getDescription() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_DESC_EMPTY);
        } else if (para.getChannelUserId() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_USER_ID_EMPTY);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.ALI_TRANSFER)
                && para.getChannelUserName() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_USER_NAME_EMPTY);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.WX_REDPACK)
                && para.getRedpackInfo() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_REDPACK_INFO_EMPTY);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.WX_REDPACK)
                && (para.getRedpackInfo() != null)
                && (para.getRedpackInfo().getSendName() == null
                        || para.getRedpackInfo().getWishing() == null || para.getRedpackInfo()
                        .getActivityName() == null)) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_REDPACK_INFO_FIELD_EMPTY);
        } else if (para.getChannel().equals(TRANSFER_CHANNEL.ALI_TRANSFER)
                && para.getAccountName() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TRANSFER_ACCOUNT_NAME_EMPTY);
        }
    }

    static void validateBCInternatioalPay(BCInternationlOrder order) throws BCException {
        if (order == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), INTERNATIONAL_PAY_PARAM_EMPTY);
        } else if (order.getBillNo() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_EMPTY);
        } else if (order.getChannel() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CHANNEL_EMPTY);
        } else if (order.getTotalFee() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TOTAL_FEE_EMPTY);
        } else if (order.getBillNo() != null && !order.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
        } else if (order.getCurrency() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CURRENCY_EMPTY);
        } else if (order.getTitle() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), TITLE_EMPTY);
        } else if (order.getChannel().equals(PAY_CHANNEL.PAYPAL_CREDITCARD)
                && order.getCreditCardInfo() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CREDIT_CARD_INFO_EMPTY);
        } else if (order.getChannel().equals(PAY_CHANNEL.PAYPAL_SAVED_CREDITCARD)
                && order.getCreditCardId() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), CREDIT_CARD_ID_EMPTY);
        } else if (order.getChannel().equals(PAY_CHANNEL.PAYPAL_PAYPAL)
                && order.getReturnUrl() == null) {
            throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                    RESULT_TYPE.PARAM_INVALID.name(), PAYPAL_RETURN_URL_EMPTY);
        } else {
            try {
                if (order.getTitle().getBytes("GBK").length > 32) {
                    throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                            RESULT_TYPE.PARAM_INVALID.name(), TITLE_FORMAT_INVALID);
                }
            } catch (UnsupportedEncodingException e) {
                if (order.getTitle().length() > 16) {
                    throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(),
                            RESULT_TYPE.PARAM_INVALID.name(), TITLE_FORMAT_INVALID);
                }
            }
        }
    }
}

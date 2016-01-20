/**
 * *************************
 *
 * @Date: Mar 18, 2015
 * @Time: 4:50:02 PM
 * @Author: Rui.Feng
 * <p/>
 * **************************
 */
package cn.beecloud;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.*;
import net.sf.json.JSONObject;


/**
 * BeeCloud JAVA SDK核心类， 包括支付、退款、查询、企业打款、批量退款等接口
 *
 * @author Ray
 * @since 2015/7/11
 */
public class BCPay {

    private final static String NOT_REGISTER = "未注册";

    private final static String NOT_CORRECT_RESPONSE = "响应不正确";

    private final static String NETWORK_ERROR = "网络错误";

    private final static String TEST_MODE_SUPPORT_ERROR = "测试模式仅支持国内支付(WX_JSAPI暂不支持)、订单查询、订单总数查询、单笔订单查询";

    /**
     * 支付接口
     *
     * @param order
     * {@link BCOrder} (必填) 支付参数
     * @return 调起BeeCloud支付后的返回结果
     * @throws BCException
     */
    public static BCOrder startBCPay(BCOrder order) throws BCException {

        ValidationUtil.validateBCPay(order);

        Map<String, Object> param = new HashMap<String, Object>();

        buildPayParam(param, order);

        if (BCCache.isSandbox()) {
            if (order.getChannel().equals(PAY_CHANNEL.WX_JSAPI)) {
                throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), TEST_MODE_SUPPORT_ERROR);
            }
            Map<String, Object> ret = doPost(BCUtilPrivate.getkSandboxApiPay(), param);
            placeSandboxOrder(order, ret);
            // 易宝点卡支付代码调用回调
            if (order.getChannel().equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
                doGet(BCUtilPrivate.getkApiSandboxNotify() + "/" + BCCache.getAppID() + "/"
                        + order.getObjectId() + "?para=", new HashMap<String, Object>());
            }
            return order;
        }
        Map<String, Object> ret = doPost(BCUtilPrivate.getkApiPay(), param);

        placeOrder(order, ret);

        return order;
    }

    /**
     * 代付接口
     *
     * @param bcTransferParameter
     * {@link BCTransferParameter} (必填) 支付参数
     * @return 调起BeeCloud代付后的返回结果
     * @throws BCException
     */
    public static void startBCTransfer(BCTransferParameter bcTransferParameter) throws BCException {
        ValidationUtil.validateBCTransfer(bcTransferParameter);
        Map<String, Object> param = new HashMap<String, Object>();
        buildBCTransferParam(param, bcTransferParameter);
        doPost(BCUtilPrivate.getkApiBCTransfer(), param);
    }

    /**
     * 退款接口
     *
     * @param refund
     * {@link BCRefund} （必填） 退款参数
     * @return 发起退款的返回结果
     * @throws BCException
     */
    public static BCRefund startBCRefund(BCRefund refund) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateBCRefund(refund);

        Map<String, Object> param = new HashMap<String, Object>();

        buildRefundParam(param, refund);

        Map<String, Object> ret = doPost(BCUtilPrivate.getkApiRefund(), param);

        refund.setObjectId(StrUtil.toStr(ret.get("id")));
        if (ret.containsKey("url")) {
            refund.setAliRefundUrl(StrUtil.toStr(ret.get("url")));
        }

        return refund;
    }

    /**
     * 订单查询（批量）接口
     *
     * @param para
     * {@link BCQueryParameter} （必填） 订单查询参数
     * @return 订单查询返回的结果
     * @throws BCException
     */
    @SuppressWarnings("unchecked")
    public static List<BCOrder> startQueryBill(BCQueryParameter para) throws BCException {
        ValidationUtil.validateQueryBill(para);

        Map<String, Object> param = new HashMap<String, Object>();

        buildQueryParam(param, para);

        if (BCCache.isSandbox()) {
            Map<String, Object> ret = doGet(BCUtilPrivate.getkApiSandboxQueryBill(), param);
            return generateBCOrderList((List<Map<String, Object>>) ret.get("bills"));
        }
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryBill(), param);

        return generateBCOrderList((List<Map<String, Object>>) ret.get("bills"));

    }

    /**
     * 订单查询（单笔，根据id）接口
     *
     * @param objectId
     * （必填） 订单记录唯一标识
     * @return id查询返回结果
     * @throws BCException
     */
    public static BCOrder startQueryBillById(String objectId) throws BCException {

        ValidationUtil.validateQueryById(objectId);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        StringBuilder urlSb = new StringBuilder();
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
            urlSb.append(BCUtilPrivate.getkApiSandboxQueryBillById());
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
            urlSb.append(BCUtilPrivate.getkApiQueryBillById());
        }

        urlSb.append("/");
        urlSb.append(objectId);
        urlSb.append("?para=");
        Map<String, Object> ret = doGet(urlSb.toString(), param);

        return generateBCOrder((Map<String, Object>) ret.get("pay"));
    }

    /**
     * 订单总数查询接口
     *
     * @param para
     * {@link BCQueryParameter} （必填）订单总数查询参数
     * @return 订单总数查询返回的结果
     * @throws BCException
     */
    public static Integer startQueryBillCount(BCQueryParameter para) throws BCException {

        ValidationUtil.validateQueryBill(para);

        Map<String, Object> param = new HashMap<String, Object>();

        buildQueryCountParam(param, para);

        if (BCCache.isSandbox()) {
            Map<String, Object> ret = doGet(BCUtilPrivate.getkApiSandboxQueryBillCount(), param);
            return (Integer) ret.get("count");
        }
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryBillCount(), param);

        return (Integer) ret.get("count");
    }

    /**
     * 退款记录查询（批量）接口
     *
     * @param para
     * {@link BCQueryParameter} （必填）订单查询参数
     * @return 退款查询返回的结果
     * @throws BCException
     */
    public static List<BCRefund> startQueryRefund(BCQueryParameter para) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateQueryRefund(para);

        Map<String, Object> param = new HashMap<String, Object>();

        buildQueryParam(param, para);

        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryRefund(), param);

        return generateBCRefundList((List<Map<String, Object>>) ret.get("refunds"));
    }

    /**
     * 退款查询接口（根据 id）
     *
     * @param objectId
     * (必填) 退款记录唯一标识
     * @return 单笔退款记录查询返回结果
     * @throws BCException
     */
    public static BCRefund startQueryRefundById(String objectId) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateQueryById(objectId);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));

        StringBuilder urlSb = new StringBuilder();
        urlSb.append(BCUtilPrivate.getkApiQueryRefundById());
        urlSb.append("/");
        urlSb.append(objectId);
        urlSb.append("?para=");
        Map<String, Object> ret = doGet(urlSb.toString(), param);

        return generateBCRefund((Map<String, Object>) ret.get("refund"));

    }

    /**
     * 退款记录总数查询接口
     *
     * @param para
     * {@link BCQueryParameter} （必填） 退款总数查询参数
     * @return 退款总数查询返回的结果
     * @throws BCException
     */
    public static Integer startQueryRefundCount(BCQueryParameter para) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateQueryRefund(para);

        Map<String, Object> param = new HashMap<String, Object>();
        buildQueryCountParam(param, para);

        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryRefundCount(), param);

        return (Integer) ret.get("count");
    }

    /**
     * 退款状态更新接口
     *
     * @param refundNo
     * （必填）商户退款单号， 格式为:退款日期(8位) + 流水号(3~24
     * 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     * @param channel
     * (必填) 渠道类型， 根据不同场景选择不同的支付方式，包含： YEE 易宝 WX 微信 KUAIQIAN 快钱 BD 百度
     * @return 退款状态更新返回结果，包括（SUCCESS， PROCESSING, FAIL...）
     * @throws BCException
     */
    public static String startRefundUpdate(PAY_CHANNEL channel, String refundNo) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateQueryRefundStatus(channel, refundNo);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        param.put("channel", StrUtil.toStr(channel));
        param.put("refund_no", refundNo);

        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiRefundUpdate(), param);
        return StrUtil.toStr(ret.get("refund_status"));
    }

    /**
     * 境外支付（paypal）接口
     *
     * @param order
     * {@link BCInternationlOrder} （必填）
     * @return 支付后返回的order
     * @throws BCException
     */
    public static BCInternationlOrder startBCInternatioalPay(BCInternationlOrder order)
            throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateBCInternatioalPay(order);

        Map<String, Object> param = new HashMap<String, Object>();

        buildInternatioalPayParam(param, order);

        Map<String, Object> ret = doPost(BCUtilPrivate.getApiInternationalPay(), param);

        placePayPalOrder(order, ret);

        return order;
    }

    /**
     * 单笔打款接口
     *
     * @param para
     * {@link TransferParameter} （必填）单笔打款参数
     * @return 如果channel类型是TRANSFER_CHANNEL.ALI_TRANSFER, 返回需要跳转支付的url, 否则返回空字符串
     * @throws BCException
     */
    public static String startTransfer(TransferParameter para) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateBCTransfer(para);

        Map<String, Object> param = new HashMap<String, Object>();

        buildTransferParam(param, para);

        Map<String, Object> ret = doPost(BCUtilPrivate.getkApiTransfer(), param);

        if (ret.containsKey("url")) {
            return StrUtil.toStr(ret.get("url"));
        }
        return "";
    }

    /**
     * 批量打款接口
     *
     * @param para
     * {@link TransfersParameter} （必填） 批量打款参数
     * @return 批量打款跳转支付url
     * @throws BCException
     */
    public static String startTransfers(TransfersParameter para) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateBCTransfers(para);

        Map<String, Object> param = new HashMap<String, Object>();

        buildTransfersParam(param, para);

        Map<String, Object> ret = doPost(BCUtilPrivate.getkApiTransfers(), param);

        return StrUtil.toStr(ret.get("url"));
    }

    /**
     * 预退款审核接口，包括批量否决和批量同意
     *
     * @param batchRefund
     * （必填） 预退款批量审核参数
     * @return 批量审核结果
     * @throws BCException
     */
    public static BCBatchRefund startBatchRefund(BCBatchRefund batchRefund) throws BCException {

        checkTestModeSwitch();

        ValidationUtil.validateBatchRefund(batchRefund);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channel", StrUtil.toStr(batchRefund.getChannel()));
        param.put("agree", batchRefund.getAgree());
        param.put("ids", batchRefund.getIds());
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));

        Map<String, Object> ret = doPut(BCUtilPrivate.getApiBatchRefund(), param);

        if (ret.containsKey("result_map")) {
            batchRefund.setIdResult((Map<String, String>) ret.get("result_map"));
            if (ret.containsKey("url")) {
                batchRefund.setAliRefundUrl(StrUtil.toStr(ret.get("url")));
            }
        }

        return batchRefund;
    }

    /**
     * Webhook接收签名验证接口
     *
     * @param sign
     * （必填） Webhook提供的签名
     * @param timestamp
     * （必填） Webhook提供的timestamp，注意是String格式
     * @return 验签结果
     */
    public static boolean verifySign(String sign, String timestamp) {
        String mySign = MD5.sign(BCCache.getAppID() + BCCache.getAppSecret(), timestamp, "UTF-8");

        if (sign.equals(mySign))
            return true;
        else
            return false;
    }

    /**
     * 构建支付rest api参数
     */
    private static void buildPayParam(Map<String, Object> param, BCOrder para) {

        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        param.put("channel", StrUtil.toStr(para.getChannel()));
        param.put("total_fee", para.getTotalFee());
        param.put("bill_no", para.getBillNo());
        param.put("title", para.getTitle());

        if (para.getReturnUrl() != null) {
            param.put("return_url", para.getReturnUrl());
        }
        if (para.getOptional() != null && para.getOptional().size() > 0) {
            param.put("optional", para.getOptional());
        }
        if (para.getOpenId() != null) {
            param.put("openid", para.getOpenId());
        }
        if (para.getShowUrl() != null) {
            param.put("show_url", para.getShowUrl());
        }
        if (para.getQrPayMode() != null) {
            if (para.getQrPayMode().ordinal() == 2) {
                param.put("qr_pay_mode", String.valueOf(para.getQrPayMode().ordinal() + 1));
            } else {
                param.put("qr_pay_mode", String.valueOf(para.getQrPayMode().ordinal()));
            }
        }
        if (para.getBillTimeout() != null) {
            param.put("bill_timeout", para.getBillTimeout());
        }
        if (para.getChannel().equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
            param.put("cardno", para.getCardNo());
            param.put("cardpwd", para.getCardPwd());
            param.put("frqid", para.getFrqid());
        }
    }

    /**
     * 构建BC代付rest api参数
     */
    private static void buildBCTransferParam(Map<String, Object> param, BCTransferParameter para) {

        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        param.put("total_fee", para.getTotalFee());
        param.put("bill_no", para.getBillNo());
        param.put("title", para.getTitle());
        param.put("trade_source", para.getTradeSource());
        param.put("bank_code", para.getBankCode());
        param.put("bank_associated_code", para.getBankAssociatedCode());
        param.put("bank_fullname", para.getBankFullName());
        param.put("card_type", para.getCardType());
        param.put("account_type", para.getAccountType());
        param.put("account_no", para.getAccountNo());
        param.put("account_name", para.getAccountName());
        if (!StrUtil.empty(para.getMobile()))
            param.put("mobile", para.getBankFullName());
        param.put("optional", para.getOptional());

    }

    /**
     * 构建退款rest api参数
     */
    private static void buildRefundParam(Map<String, Object> param, BCRefund para) {

        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignatureWithMasterSecret(StrUtil.toStr(param
                .get("timestamp"))));
        param.put("refund_no", para.getRefundNo());
        param.put("bill_no", para.getBillNo());
        param.put("refund_fee", para.getRefundFee());

        if (para.getChannel() != null) {
            param.put("channel", StrUtil.toStr(para.getChannel()));
        }
        if (para.isNeedApproval() != null) {
            param.put("need_approval", para.isNeedApproval());
        }
        if (para.getOptional() != null && para.getOptional().size() > 0)
            param.put("optional", para.getOptional());
    }

    /**
     * 构建查询rest api参数
     */
    private static void buildQueryParam(Map<String, Object> param, BCQueryParameter para) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        if (para.getChannel() != null) {
            param.put("channel", StrUtil.toStr(para.getChannel()));
        }
        if (para.getBillNo() != null) {
            param.put("bill_no", para.getBillNo());
        }
        if (para.getRefundNo() != null) {
            param.put("refund_no", para.getRefundNo());
        }
        if (para.getSkip() != null) {
            param.put("skip", para.getSkip());
        }
        if (para.getLimit() != null) {
            param.put("limit", para.getLimit());
        }
        if (para.getStartTime() != null) {
            param.put("start_time", para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
            param.put("end_time", para.getEndTime().getTime());
        }
        if (para.getPayResult() != null) {
            param.put("spay_result", para.getPayResult());
        }
        if (para.getNeedDetail() != null && para.getNeedDetail()) {
            param.put("need_detail", para.getNeedDetail());
        }
        if (para.getNeedApproval() != null && para.getNeedApproval()) {
            param.put("need_approval", para.getNeedApproval());
        }

    }

    /**
     * 构建订单总数查询rest api参数
     */
    private static void buildQueryCountParam(Map<String, Object> param, BCQueryParameter para) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        if (para.getChannel() != null) {
            param.put("channel", StrUtil.toStr(para.getChannel()));
        }
        if (para.getBillNo() != null) {
            param.put("bill_no", para.getBillNo());
        }
        if (para.getRefundNo() != null) {
            param.put("refund_no", para.getRefundNo());
        }
        if (para.getStartTime() != null) {
            param.put("start_time", para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
            param.put("end_time", para.getEndTime().getTime());
        }
    }

    /**
     * 构建境外支付rest api参数
     */
    private static void buildInternatioalPayParam(Map<String, Object> param,
            BCInternationlOrder order) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        param.put("channel", StrUtil.toStr(order.getChannel()));
        param.put("currency", StrUtil.toStr(order.getCurrency()));
        param.put("bill_no", order.getBillNo());
        param.put("title", order.getTitle());
        param.put("total_fee", order.getTotalFee());
        if (order.getCreditCardInfo() != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            param.put("credit_card_info", map);
            map.put("card_number", order.getCreditCardInfo().getCardNo());
            map.put("expire_month", order.getCreditCardInfo().getExpireMonth());
            map.put("expire_year", order.getCreditCardInfo().getExpireYear());
            map.put("cvv", order.getCreditCardInfo().getCvv());
            map.put("first_name", order.getCreditCardInfo().getFirstName());
            map.put("last_name", order.getCreditCardInfo().getLastName());
            map.put("card_type", StrUtil.toStr(order.getCreditCardInfo().getCardType()));
        }
        if (order.getCreditCardId() != null) {
            param.put("credit_card_id", order.getCreditCardId());
        }
        if (order.getReturnUrl() != null) {
            param.put("return_url", order.getReturnUrl());
        }
    }

    /**
     * 构建单笔打款rest api参数
     */
    private static void buildTransferParam(Map<String, Object> param, TransferParameter para) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignatureWithMasterSecret(StrUtil.toStr(param
                .get("timestamp"))));
        param.put("channel", StrUtil.toStr(para.getChannel()));
        param.put("transfer_no", para.getTransferNo());
        param.put("total_fee", para.getTotalFee());
        param.put("desc", para.getDescription());
        param.put("channel_user_id", para.getChannelUserId());
        if (para.getChannelUserName() != null) {
            param.put("channel_user_name", para.getChannelUserName());
        }
        if (para.getRedpackInfo() != null) {
            Map<String, Object> redpackInfo = new HashMap<String, Object>();
            redpackInfo.put("send_name", para.getRedpackInfo().getSendName());
            redpackInfo.put("wishing", para.getRedpackInfo().getWishing());
            redpackInfo.put("act_name", para.getRedpackInfo().getActivityName());
            param.put("redpack_info", redpackInfo);
        }
        if (para.getAccountName() != null) {
            param.put("account_name", para.getAccountName());
        }
    }

    /**
     * 构建批量打款rest api参数
     */
    private static void buildTransfersParam(Map<String, Object> param, TransfersParameter para) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignatureWithMasterSecret(StrUtil.toStr(param
                .get("timestamp"))));
        param.put("channel", StrUtil.toStr(para.getChannel()));
        param.put("batch_no", para.getBatchNo());
        param.put("account_name", para.getAccountName());
        List<Map<String, Object>> transferList = new ArrayList<Map<String, Object>>();
        for (ALITransferData data : para.getTransferDataList()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("transfer_id", data.getTransferId());
            map.put("receiver_account", data.getReceiverAccount());
            map.put("receiver_name", data.getReceiverName());
            map.put("transfer_fee", data.getTransferFee());
            map.put("transfer_note", data.getTransferNote());
            transferList.add(map);
        }
        param.put("transfer_data", transferList);
    }

    /**
     * 生成返回BCOrder list
     */
    private static List<BCOrder> generateBCOrderList(List<Map<String, Object>> bills) {

        List<BCOrder> bcOrderList = new ArrayList<BCOrder>();
        for (Map<String, Object> bill : bills) {
            BCOrder bcOrder = new BCOrder();
            generateBCOrderBean(bill, bcOrder);
            bcOrderList.add(bcOrder);
        }
        return bcOrderList;
    }

    /**
     * 生成返回BCOrder
     */
    private static BCOrder generateBCOrder(Map<String, Object> bill) {
        BCOrder bcOrder = new BCOrder();
        generateBCOrderBean(bill, bcOrder);
        return bcOrder;
    }

    /**
     * 生成返回BCRefund list
     */
    private static List<BCRefund> generateBCRefundList(List<Map<String, Object>> refundList) {

        List<BCRefund> bcRefundList = new ArrayList<BCRefund>();
        for (Map<String, Object> refund : refundList) {
            BCRefund bcRefund = new BCRefund();
            generateBCRefundBean(refund, bcRefund);
            bcRefundList.add(bcRefund);
        }
        return bcRefundList;
    }

    /**
     * 生成返回BCRefund
     */
    private static BCRefund generateBCRefund(Map<String, Object> refund) {
        BCRefund bcRefund = new BCRefund();
        generateBCRefundBean(refund, bcRefund);
        return bcRefund;
    }

    /**
     * 构建返回BCOrder bean
     */
    private static void generateBCOrderBean(Map<String, Object> bill, BCOrder bcOrder) {
        bcOrder.setObjectId(StrUtil.toStr(bill.get("id")));
        bcOrder.setBillNo(StrUtil.toStr(bill.get("bill_no")));
        bcOrder.setTotalFee((Integer) bill.get("total_fee"));
        bcOrder.setTitle(StrUtil.toStr(bill.get("title")));
        bcOrder.setChannel(PAY_CHANNEL.valueOf(StrUtil.toStr(bill.get("sub_channel"))));
        bcOrder.setResulted(((Boolean) bill.get("spay_result")));
        if (bill.containsKey("trade_no") && bill.get("trade_no") != null) {
            bcOrder.setChannelTradeNo(StrUtil.toStr(bill.get("trade_no")));
        }
        bcOrder.setOptionalString(StrUtil.toStr(bill.get("optional")));
        bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long) bill
                .get("create_time")));
        if (bill.containsKey("message_detail")) {
            bcOrder.setMessageDetail(StrUtil.toStr(bill.get("message_detail")));
        }
        bcOrder.setRefundResult((Boolean) bill.get("refund_result"));
        bcOrder.setRevertResult((Boolean) bill.get("revert_result"));
    }

    /**
     * 构建返回BCRefund bean
     */
    private static void generateBCRefundBean(Map<String, Object> refund, BCRefund bcRefund) {
        bcRefund.setObjectId(StrUtil.toStr(refund.get("id")));
        bcRefund.setBillNo(StrUtil.toStr(refund.get("bill_no")));
        bcRefund.setChannel(PAY_CHANNEL.valueOf(StrUtil.toStr(refund.get("sub_channel"))));
        bcRefund.setFinished((Boolean) refund.get("finish"));
        bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long) refund
                .get("create_time")));
        bcRefund.setOptionalString(StrUtil.toStr(refund.get("optional")));
        bcRefund.setRefunded((Boolean) refund.get("result"));
        bcRefund.setTitle(StrUtil.toStr(refund.get("title")));
        bcRefund.setTotalFee((Integer) refund.get("total_fee"));
        bcRefund.setRefundFee((Integer) refund.get("refund_fee"));
        bcRefund.setRefundNo(StrUtil.toStr(refund.get("refund_no")));
        if (refund.containsKey("message_detail")) {
            bcRefund.setMessageDetail(StrUtil.toStr(refund.get("message_detail")));
        }
    }

    /**
     * 构建WXJSAPI返回Map
     */
    private static Map<String, String> generateWXJSAPIMap(Map<String, Object> ret) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", StrUtil.toStr(ret.get("app_id")));
        map.put("package", StrUtil.toStr(ret.get("package")));
        map.put("nonceStr", StrUtil.toStr(ret.get("nonce_str")));
        map.put("timeStamp", StrUtil.toStr(ret.get("timestamp")));
        map.put("paySign", StrUtil.toStr(ret.get("pay_sign")));
        map.put("signType", StrUtil.toStr(ret.get("sign_type")));

        return map;
    }

    /**
     * doPost方法，封装rest api POST方式请求
     *
     * @param url
     * 请求url
     * @param param
     * 请求参数
     * @return rest api返回参数
     * @throws BCException
     */
    private static Map<String, Object> doPost(String url, Map<String, Object> param)
            throws BCException {
        Client client = BCAPIClient.client;
        if (client == null) {
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }
        WebTarget target = client.target(url);
        try {
            Response response = target.request().post(
                    Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                Integer resultCode = (Integer) ret.get("result_code");
                String resultMessage = StrUtil.toStr(ret.get("result_msg"));
                String errorDetail = StrUtil.toStr(ret.get("err_detail"));

                boolean isSuccess = (resultCode == 0);
                if (isSuccess) {
                    return ret;
                } else {
                    throw new BCException(resultCode, resultMessage, errorDetail);
                }
            } else {
                throw new BCException(-1, RESULT_TYPE.NOT_CORRECT_RESPONSE.name(),
                        NOT_CORRECT_RESPONSE);
            }
        } catch (Exception e) {
            if (e instanceof BCException) {
                throw (BCException) e;
            }
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NETWORK_ERROR + ","
                    + e.getMessage());
        }
    }

    /**
     * doPut方法，封装rest api PUT方式请求
     *
     * @param url
     * 请求url
     * @param param
     * 请求参数
     * @return rest api返回参数
     * @throws BCException
     */
    private static Map<String, Object> doPut(String url, Map<String, Object> param)
            throws BCException {
        Client client = BCAPIClient.client;
        if (client == null) {
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }
        WebTarget target = client.target(url);
        try {
            Response response = target.request().put(
                    Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                Integer resultCode = (Integer) ret.get("result_code");
                String resultMessage = StrUtil.toStr(ret.get("result_msg"));
                String errorDetail = StrUtil.toStr(ret.get("err_detail"));

                boolean isSuccess = (resultCode == 0);
                if (isSuccess) {
                    return ret;
                } else {
                    throw new BCException(resultCode, resultMessage, errorDetail);
                }
            } else {
                throw new BCException(-1, RESULT_TYPE.NOT_CORRECT_RESPONSE.name(),
                        NOT_CORRECT_RESPONSE);
            }
        } catch (Exception e) {
            if (e instanceof BCException) {
                throw (BCException) e;
            }
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), e.getMessage());
        }
    }

    /**
     * doGet方法，封装rest api GET方式请求
     *
     * @param url
     * 请求url
     * @param param
     * 请求参数
     * @return rest api返回参数
     * @throws BCException
     */
    private static Map<String, Object> doGet(String url, Map<String, Object> param)
            throws BCException {
        Client client = BCAPIClient.client;
        if (client == null) {
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(url);

        try {
            sb.append(URLEncoder.encode(JSONObject.fromObject(param).toString(), "UTF-8"));
            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                Integer resultCode = (Integer) ret.get("result_code");
                String resultMessage = StrUtil.toStr(ret.get("result_msg"));
                String errorDetail = StrUtil.toStr(ret.get("err_detail"));
                boolean isSuccess = (resultCode == 0);

                if (isSuccess) {
                    return ret;
                } else {
                    throw new BCException(resultCode, resultMessage, errorDetail);
                }
            } else {
                System.out.println(sb.toString());
                throw new BCException(-1, RESULT_TYPE.NOT_CORRECT_RESPONSE.name(),
                        NOT_CORRECT_RESPONSE);
            }
        } catch (Exception e) {
            if (e instanceof BCException) {
                throw (BCException) e;
            }
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), e.getMessage());
        }
    }

    /**
     * 组建返回订单
     */
    private static void placeOrder(BCOrder order, Map<String, Object> ret) {
        order.setObjectId(StrUtil.toStr(ret.get("id")));
        switch (order.getChannel()) {
            case WX_NATIVE:
                if (ret.containsKey("code_url") && null != ret.get("code_url")) {
                    order.setCodeUrl(StrUtil.toStr(ret.get("code_url")));
                }
                break;
            case WX_JSAPI:
                order.setWxJSAPIMap(generateWXJSAPIMap(ret));
                break;
            case ALI_WEB:
            case ALI_QRCODE:
            case ALI_WAP:
                if (ret.containsKey("html") && null != ret.get("html") && ret.containsKey("url")
                        && null != ret.get("url")) {
                    order.setHtml(StrUtil.toStr(ret.get("html")));
                    order.setUrl(StrUtil.toStr(ret.get("url")));
                }
                break;
            case UN_WEB:
            case JD_WAP:
            case JD_WEB:
            case KUAIQIAN_WAP:
            case KUAIQIAN_WEB:
                if (ret.containsKey("html") && null != ret.get("html")) {
                    order.setHtml(StrUtil.toStr(ret.get("html")));
                }
                break;
            case YEE_WAP:
            case YEE_WEB:
            case BD_WEB:
            case BD_WAP:
                if (ret.containsKey("url") && null != ret.get("url")) {
                    order.setUrl(StrUtil.toStr(ret.get("url")));
                }
            default:
                break;
        }
    }

    /**
     * 组建返回境外支付订单
     */
    private static void placePayPalOrder(BCInternationlOrder order, Map<String, Object> ret) {
        order.setObjectId(StrUtil.toStr(ret.get("id")));
        switch (order.getChannel()) {
            case PAYPAL_PAYPAL:
                order.setUrl(StrUtil.toStr(ret.get("url")));
                break;
            case PAYPAL_CREDITCARD:
                order.setCreditCardId(StrUtil.toStr(ret.get("credit_card_id")));
                break;
            default:
                break;
        }
    }

    /**
     * 组建返回沙箱支付订单
     */
    private static void placeSandboxOrder(BCOrder order, Map<String, Object> ret) {
        order.setObjectId(StrUtil.toStr(ret.get("id")));
        switch (order.getChannel()) {
            case WX_NATIVE:
                if (ret.containsKey("url") && null != ret.get("url")) {
                    order.setCodeUrl(StrUtil.toStr(ret.get("url")));
                }
                break;
            case WX_JSAPI:
                order.setWxJSAPIMap(generateWXJSAPIMap(ret));
                break;
            case ALI_WEB:
            case ALI_QRCODE:
            case ALI_WAP:
                if (ret.containsKey("url") && null != ret.get("url")) {
                    order.setHtml(BCUtil.generateSandboxHtmlWithUrl(StrUtil.toStr(ret.get("url"))));
                    order.setUrl(StrUtil.toStr(ret.get("url")));
                }
                break;
            case UN_WEB:
            case JD_WAP:
            case JD_WEB:
            case KUAIQIAN_WAP:
            case KUAIQIAN_WEB:
                if (ret.containsKey("url") && null != ret.get("url")) {
                    order.setHtml(BCUtil.generateSandboxHtmlWithUrl(StrUtil.toStr(ret.get("url"))));
                }
                break;
            case YEE_WAP:
            case YEE_WEB:
            case BD_WEB:
            case BD_WAP:
                if (ret.containsKey("url") && null != ret.get("url")) {
                    order.setUrl(StrUtil.toStr(ret.get("url")));
                }
            default:
                break;
        }
    }

    /**
     * 检查某一借口是否支持测试模式
     */
    private static void checkTestModeSwitch() throws BCException {
        if (BCCache.isSandbox()) {
            throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), TEST_MODE_SUPPORT_ERROR);
        }
    }
}

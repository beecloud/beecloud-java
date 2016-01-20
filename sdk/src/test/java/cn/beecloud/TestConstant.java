package cn.beecloud;

/**
 * User: joseph Date: 14/11/30
 */
public class TestConstant {
    public static String KTestAppID = "c5d1cba1-5e3f-4ba0-941d-9b0a371fe719";
    public static String kTestAppSecret = "39a7a518-9ac8-4a9e-87bc-7885f33cf18c";
    public static String kTestTestSecret = "4bfdd244-574d-4bf3-b034-0c751ed34fee";
    public static String kTestMasterSecret = "e14ae2db-608c-4f8b-b863-c8c18953eef2";
    public static String kTestTable = "java_sdk_test";
    public static String outRefundNo = "2015062611111";
    public static String BILL_NO_WITH_SPECIAL_CHARACTER = "billno123!@#";
    public static String subject = "water";
    public static String totalFee = "1";
    public static String body = "test";
    public static String ASSERT_MESSAGE = "不一致";
    public static String TITLE_WITH_CHARACTER＿GREATER_THAN_32 = "超过16个中文字符的标题是不被接受的";
    public static String REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24 = "1234567890123456789012345";
    public static String REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3 = "01";
    public static String REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER = "特殊字符";
    public static String ALI_TRANSFER_ACCOUNT_NAME = "苏州比可网络科技有限公司";
    public static String ALI_TRANSFER_BATCH_NO_WITH_SPECIAL_CHARACTER = "batchNo流水号001";
    public static String ALI_TRANSFER_RECEIVER_ACCOUNT_1 = "13584809649";
    public static String ALI_TRANSFER_RECEIVER_ACCOUNT_2 = "13584804872";
    public static String ALI_TRANSFER_RECEIVER_NAME_1 = "袁某某";
    public static String ALI_TRANSFER_RECEIVER_NAME_2 = "张某某";
    public static String TRANSFER_NOTE = "下发";
    public static String INVALID_TRANSFER_ID = "包含中文aaabbccdd";
    public static String INVALID_OBJECT_ID = "aaabbbccc!!!@@@###";
    public static String VALID_REFUND_OBJECT_ID = "9ed1cec8-6f94-4666-b680-fbe0265d0867";
    public static String NOT_EXIST_BILL_NO = "aaabbbcccdddeee111222333";

    public static String YEE_NOBANKCARD_NO = "15078120125091678";
    public static String YEE_NOBANKCARD_PWD = "121684730734269992";
    public static String YEE_NOBANKCARD_FRQID = "SZX";

    public static String WXJSAPI_OPEN_ID = "ofEy7uL6p-pgwF7SoPrzD8GqvZ0";

    public static String TRANSFER_NO_WITH_SPECIAL_CHARACTER = "billno123!@#";
    public static String WX_TRANSFER_NO_WITH_INVALID_LENGTH = "123456789";

    public static String yeeWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWebReturnUrl.jsp";
    public static String yeeWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWapReturnUrl.jsp";
    public static String jdWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWebReturnUrl.jsp";
    public static String jdWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWapReturnUrl.jsp";
    public static String kqReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/kqReturnUrl.jsp";
    public static String aliReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/aliReturnUrl.jsp";
    public static String unReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/unReturnUrl.jsp";
    public static String bdReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/bdReturnUrl.jsp";

    public static Integer billTimeOut = 300;

    public static String MOCK_CODE_URL = "weixin://weixinmockurl001";
    public static String MOCK_OBJECT_ID = "abcdefg1234556890";
    public static String MOCK_PAY_URL = "http://www.163.com";
    public static String MOCK_PAY_HTML = "<form></form><script>form.submit()</script>";
    public static boolean MOCK_PAY_RESULT = false;
    public static long MOCK_CREATE_TIME = 1447224624009l;
    public static Integer MOCK_TOTAL_FEE = 1;
    public static String MOCK_CHANNEL = "WX";
    public static String MOCK_SUB_CHANNEL = "WX_NATIVE";
    public static String MOCK_BILL_NO = "e59990442bf04290b8943a36c3538f49";
    public static String MOCK_TITLE = "demo测试";
    public static String MOCK_WXJSAPI_APPID = "wx119a2bda81854ae0";
    public static String MOCK_WXJSAP_TIMESTAMP = "1447233544";
    public static String MOCK_WXJSAPI_NONCESTR = "5420ba9fced94dbbbd20d5f473058d3e";
    public static String MOCK_WXJSAPI_PACKAGE = "prepay_id=wx201511111719040099f391140762911510";
    public static String MOCK_WXJSAPI_PAYSIGN = "817F3E861DD73B04D2BE9E78FBEBA65F";
    public static String MOCK_WXJSAP_SIGNTYPE = "MD5";
    public static String MOCK_TITLE_16_CHINESE_CHARACTER = "正好十六个汉字的标题哈哈哈哈哈哈";
    public static String MOCK_TRADE_NO = "2015052300001000620053541865";
    public static String MOCK_OPTIONAL_JSON_STRING = "{\"rui\":\"rui\"}";
    public static String MOCK_MESSAGE_DETAIL_STRING = "{\"bc_appid\":\"c5d1cba1-5e3f-4ba0-941d-9b0a371fe719_728a592b-fe29-45e3-bddf-c4c54bf2d3f9\",\"payment_type\":\"1\",\"subject\":\"demo测试\",\"trade_no\":\"2015111600001000610062919539\",\"buyer_email\":\"13861331391\",\"gmt_create\":\"2015-11-16 14:59:53\",\"notify_type\":\"trade_status_sync\",\"quantity\":\"1\",\"out_trade_no\":\"6047c40979734cb586645517d23ee833\",\"seller_id\":\"2088711322600312\",\"notify_time\":\"2015-11-16 14:59:54\",\"trade_status\":\"TRADE_SUCCESS\",\"is_total_fee_adjust\":\"N\",\"total_fee\":\"0.01\",\"gmt_payment\":\"2015-11-16 14:59:54\",\"seller_email\":\"admin@beecloud.cn\",\"price\":\"0.01\",\"buyer_id\":\"2088502992657614\",\"notify_id\":\"0cbfa4cc5883b8e6afe37c52dcef71015e\",\"use_coupon\":\"N\",\"sign_type\":\"MD5\",\"sign\":\"5b6d83caec01406a93f85bfe367817cf\"}";
    public static String MOCK_REFUND_UPDATE_MSG = "SUCCESS";
    public static Integer MOCK_REFUND_FEE = 1;
    public static String MOCK_REFUND_NO = "2015111600223344";
    public static String MOCK_ALI_TRANSFER_URL = "http://alipay.transfer";
    public static String MOCK_APPROVE_IDLIST_ID1 = "abcdefg12345678";
    public static String MOCK_APPROVE_IDLIST_ID2 = "abcdefg12345678";
    public static String MOCK_APPROVE_ID1_RESULT = "OK";
    public static String MOCK_APPROVE_ID2_RESULT = "渠道错误。。。";
    public static String MOCK_SANDBOX_PAY_URL = "http://mock.beecloud.cn/testcase/?appid=c5d1cba1-5e3f-4ba0-941d-9b0a371fe719&channel=ALI_WEB&objectid=56f05148-f84d-461b-934a-996abe22e72e&title=demo%E6%B5%8B%E8%AF%95&fee=1&bill_no=d520c0237c9643eb9440a8cd0b094c55";

    public static String MOCK_CREDIT_CARD_ID = "CARD_ADMJ324234DJLKJS";

    public static String MOCK_ALI_REFUND_URL = "https://tradeexprod.alipay.com/refund/fastPayBatchPwdRefund.htm?partner=2088711322622223";
    public static String MOCK_CHANNEL_ERROR_MSG = "channel error:xxxxx";

    public final static String BILL_NO_FORMAT_INVALID = "billNo 是一个长度介于8至32字符的数字字母字符串！";

    public final static String BATCH_NO_FORMAT_INVALID = "batchNo 是一个长度在11到32个字符的数字字母字符串！";

    public final static String PAY_PARAM_EMPTY = "支付参数不能为空！";

    public final static String REFUND_PARAM_EMPTY = "退款参数不能为空！";

    public final static String QUERY_PARAM_EMPTY = "查询参数不能为空！";

    public final static String BILL_NO_EMPTY = "billNo 不能为空！";

    public final static String BATCH_NO_EMPTY = "batchNo 不能为空！";

    public final static String TRANSFER_DATA_EMPTY = "transferData 不能为空！";

    public final static String TRANSFER_ID_EMPTY = "transferId 不能为空！";

    public final static String RECEIVER_ACCOUNT_EMPTY = "receiverAccount 不能为空！";

    public final static String RECEIVER_NAME_EMPTY = "receiverName 不能为空！";

    public final static String TRANSFER_FEE_EMPTY = "transferFee 不能为空！";

    public final static String TRANSFER_FEE_INVALID = "transferFee 必须大于0！";

    public final static String TRANSFER_NOTE_EMPTY = "transferNote 不能为空！";

    public final static String ACCOUNT_NAME_EMPTY = "accountName 不能为空！";

    public final static String TITLE_EMPTY = "title 不能为空！";

    public final static String TOTAL_FEE_EMPTY = "totalFee 不能为空！";

    public final static String REFUND_FEE_EMPTY = "refundFee 不能为空！";

    public final static String REFUND_FEE_INVALID = "refundFee 必须大于零！";

    public final static String QR_PAY_MODE_EMPTY = "qrPayMode 不能为空！";

    public final static String RETURN_URL_EMPTY = "returnUrl 不能为空！";

    public final static String REFUND_NO_EMPTY = "refundNo 不能为空！";

    public final static String CHANNEL_EMPTY = "channel 不能为空！";

    public final static String YEE_NOBANCARD_FACTOR_EMPTY = "cardNo, cardPwd, frqid 不能为空！";

    public final static String REFUND_NO_FORMAT_INVALID = "refundNo 是格式为当前日期加3-24位数字字母（不能为000）流水号的字符串！ ";

    public final static String TITLE_FORMAT_INVALID = "title 是一个长度不超过32字节的字符串！";

    public final static String LIMIT_FORMAT_INVALID = "limit 的最大长度为50！";

    public final static String OPENID_EMPTY = "openid 不能为空！";

    public final static String CHANNEL_INVALID_FOR_REFUND = "退款只支持WX, UN, ALI !";

    public final static String TRANSFER_ID_FORMAT_INVALID = "transferId 是一个长度不超过32字符的数字字母字符串！";

    public final static String TRANSFER_LIST_SIZE_INVALID = "transferData 长度不能超过1000！";

    public final static String CHANNEL_SUPPORT_INVALID = "批量打款仅支持ALI";

    public static String ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN = "BCException未抛出！";
    public static String ASSERT_MESSAGE_BCEXCEPTION_THROWN = "BCException抛出";

    public final static String BILL_TIME_OUT_ZERO = "billTimeout不能为0！";

    public final static String OBJECT_ID_INVALID = "objectId 只能包含数字、字母或者-";

    public final static String OBJECT_ID_EMPTY = "objectId 不能为空！";

    public final static String REFUND_UPDATE_CHANNEL_INVALID = "退款更新仅支持微信、百度、易宝、快钱！";

    public static String MOCK_APP_INVALID_ERRMSG = "根据app_id找不到对应的APP或者app_sign不正确,或者timestamp不是当前UTC";

    public final static String BATCH_REFUND_PARAM_EMPTY = "批量审核参数不能为空！";

    public final static String BATCH_REFUND_AGREE_EMPTY = "批量审核agree不能为空！";

    public final static String BATCH_REFUND_CHANNEL_EMPTY = "批量审核channel不能为空!";

    public final static String BATCH_REFUND_ID_LIST_EMPTY = "批量审核ids不能为空！";

    public final static String TRANSFER_CHANNEL_EMPTY = "单笔打款channel 不能为空!";

    public final static String TRANSFER_TRANSFER_NO_EMPTY = "单笔打款transferNo 不能为空！";

    public final static String TRANSFER_TOTAL_FEE_EMPTY = "单笔打款totalFee 不能为空！";

    public final static String TRANSFER_DESC_EMPTY = "单笔打款description 不能为空！";

    public final static String TRANSFER_USER_ID_EMPTY = "单笔打款channelUserId 不能为空！";

    public final static String TRANSFER_REDPACK_INFO_EMPTY = "微信红包redpackInfo 不能为空! ";

    public final static String TRANSFER_USER_NAME_EMPTY = "支付宝单笔打款channelUserName 不能为空！";

    public final static String TRANSFER_ACCOUNT_NAME_EMPTY = "支付宝单笔打款accountName 不能为空！";

    public final static String ALI_TRANSFER_NO_INVALID = "支付宝单笔打款transferNo 是一个长度在11到32个字符的数字字母字符串";

    public final static String WX_TRANSFER_TOTAL_FEE_INVALID = "微信打款金额不能小于1.00元，totalFee必须大于等于100!";

    public final static String WX_REDPACK_TOTAL_FEE_INVALID = "只能发放1.00块到200块钱的红包，totalFee范围必须在(100~20000)内";

    public final static String ALI_TRANSFER_TOTAL_FEE_INVALID = "支付宝单笔打款totalFee 必须大于 0！";

    public final static String TRANSFER_REDPACK_INFO_FIELD_EMPTY = "微信红包sendName、wishing、activityName 不能为空!";

    public final static String TRANSFER_PARAM_EMPTY = "transfer参数不能为空！";

    public final static String WX_TRANSFER_NO_INVALID = "微信单笔打款transferNo 是一个长度为10的数字！";

    public final static String TRANSFERS_PARAM_EMPTY = "批量打款参数不能为空！";

    public final static String TRANSFERS_CHANNEL_EMPTY = "批量打款channel 不能为空！";

    public final static String TRANSFERS_DATA_LIST_EMPTY = "批量打款transferDataList 不能为空！";

    public final static String TRANSFERS_BATCH_NO_EMPTY = "批量打款batchNo 不能为空！";

    public final static String TRANSFERS_ACCOUNT_NAME_EMPTY = "accountName 不能为空！";

    public final static String TRANSFERS_CHANNEL_SUPPORT_INVALID = "批量打款仅支持ALI";

    public final static String TRANSFERS_BATCH_NO_FORMAT_INVALID = "batchNo 是一个长度在11到32个字符的数字字母字符串！";

    public final static String INTERNATIONAL_PAY_PARAM_EMPTY = "境外支付参数不能为空！";

    public final static String CURRENCY_EMPTY = "currency不能为空！";

    public final static String CREDIT_CARD_INFO_EMPTY = "信用卡信息不能为空！";

    public final static String CREDIT_CARD_ID_EMPTY = "存储的信用卡ID不能为空！";

    public final static String PAYPAL_RETURN_URL_EMPTY = "PAYPAL直接支付returnUrl不能为空！";

    public final static String TEST_MODE_SUPPORT_ERROR = "测试模式仅支持国内支付(WX_JSAPI暂不支持)、订单查询、订单总数查询、单笔订单查询";

    public final static String TRADE_SOURCE_EMPTY = "tradeSource 不能为空！";

    public final static String BANK_CODE_EMPTY = "bankCode 不能为空！";

    public final static String BANK_ASSOCIATED_CODE_EMPTY = "bankAssociatedCode 不能为空！";

    public final static String BANK_FULL_NAME_EMPTY = "bankFullName 不能为空！";

    public final static String CARD_TYPE_EMPTY = "cardType 不能为空！";

    public final static String ACCOUNT_TYPE_EMPTY = "accountType 不能为空！";

    public final static String ACCOUNT_NO_EMPTY = "accountNo 不能为空！";

    public static enum CHANNEL_TYPE {
        WX,
        WX_APP,
        WX_NATIVE,
        WX_JSAPI,
        ALI,
        ALI_APP,
        ALI_WEB,
        ALI_QRCODE,
        ALI_OFFLINE_QRCODE,
        UN,
        UN_APP,
        UN_WEB,
        KUAIQIAN,
        KUAIQIAN_WAP,
        KUAIQIAN_WEB,
        PAYPAL,
        YEE,
        YEE_WAP,
        YEE_WEB,
        JD,
        JD_WEB,
        JD_WAP,
        BD,
        BD_WEB,
        BD_WAP,
        BD_APP
    }

    public static enum REQUEST_TYPE {
        GET,
        POST
    }

}

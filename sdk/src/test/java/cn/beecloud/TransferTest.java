package cn.beecloud;

import static junit.framework.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.beecloud.bean.*;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.StrictExpectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;


/**
 * 单笔打款单元测试
 * 
 * @author Rui
 * @since 2015/11/12
 *
 */
public class TransferTest {

    static String transferNo = BCUtil.generateRandomUUIDPure();
    static TRANSFER_CHANNEL channel = TRANSFER_CHANNEL.ALI_TRANSFER;
    static Integer totalFee = 1;
    static String description = "赔偿";
    static String channelUserId = "13861331391";
    static String channelUserName = "冯睿";
    static String accountName = "苏州比可网络科技有限公司";
    static String sendName = "BeeCloud";
    static String wishing = "BeeCloud祝福开发者工作顺利!";
    static String activityName = "BeeCloud开发者红包活动";
    static RedpackInfo redpackInfo = new RedpackInfo();
    static String validWXTransferNo = "1234567890";
    static Integer validWXRedpackTotalFee = 200;
    static String billNo = BCUtil.generateRandomUUIDPure();
    static String subject = "javasdk unit test";

    static {
        redpackInfo.setActivityName(activityName);
        redpackInfo.setSendName(sendName);
        redpackInfo.setWishing(wishing);
    }

    static void testTransfer() {
        TransferParameter param = new TransferParameter();
        initTransferParam(param);

        if (BCCache.isSandbox()) {
            try {
                BCPay.startTransfer(param);
                Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
            } catch (Exception e) {
                Assert.assertTrue(e.getMessage(), e instanceof BCException);
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(RESULT_TYPE.OTHER_ERROR.name()));
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(TestConstant.TEST_MODE_SUPPORT_ERROR));
            }
            return;
        }

        String url = "";
        try {
            url = BCPay.startTransfer(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_PARAM_EMPTY));
        }

        try {
            param.setTransferNo(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_TRANSFER_NO_EMPTY));
        }

        try {
            param.setTransferNo(transferNo + "A");
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.ALI_TRANSFER_NO_INVALID));
        }

        try {
            param.setTransferNo(transferNo.substring(0, 10));
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.ALI_TRANSFER_NO_INVALID));
        }

        try {
            param.setTransferNo(TestConstant.TRANSFER_NO_WITH_SPECIAL_CHARACTER);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.ALI_TRANSFER_NO_INVALID));
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
            param.setTransferNo(transferNo);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_TRANSFER_NO_INVALID));
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_TRANSFER);
            param.setTransferNo(transferNo);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_TRANSFER_NO_INVALID));
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_TRANSFER);
            param.setTransferNo(TestConstant.WX_TRANSFER_NO_WITH_INVALID_LENGTH);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_TRANSFER_NO_INVALID));
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
            param.setTransferNo(TestConstant.WX_TRANSFER_NO_WITH_INVALID_LENGTH);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_TRANSFER_NO_INVALID));
        }
        param.setTransferNo(transferNo);

        try {
            param.setChannel(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_CHANNEL_EMPTY));
        }
        param.setChannel(channel);

        try {
            param.setTotalFee(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_TOTAL_FEE_EMPTY));
        }

        try {
            param.setTransferNo(validWXTransferNo);
            param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
            param.setTotalFee(20001);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_REDPACK_TOTAL_FEE_INVALID));
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
            param.setTotalFee(99);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_REDPACK_TOTAL_FEE_INVALID));
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_TRANSFER);
            param.setTotalFee(99);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.WX_TRANSFER_TOTAL_FEE_INVALID));
        }
        param.setChannel(channel);
        param.setTotalFee(totalFee);
        param.setTransferNo(transferNo);

        try {
            param.setDescription(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_DESC_EMPTY));
        }
        param.setDescription(description);

        try {
            param.setChannelUserId(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_USER_ID_EMPTY));
        }
        param.setChannelUserId(channelUserId);

        try {
            param.setChannelUserName(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_USER_NAME_EMPTY));
        }
        param.setChannelUserName(channelUserName);

        try {
            param.setTransferNo(validWXTransferNo);
            param.setRedpackInfo(null);
            param.setTotalFee(validWXRedpackTotalFee);
            param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_REDPACK_INFO_EMPTY));
        }
        param.setRedpackInfo(redpackInfo);

        try {
            redpackInfo.setActivityName(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_REDPACK_INFO_FIELD_EMPTY));
        }
        redpackInfo.setActivityName(activityName);

        try {
            redpackInfo.setSendName(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_REDPACK_INFO_FIELD_EMPTY));
        }
        redpackInfo.setSendName(sendName);

        try {
            redpackInfo.setWishing(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_REDPACK_INFO_FIELD_EMPTY));
        }
        redpackInfo.setWishing(wishing);

        try {
            param.setChannel(TRANSFER_CHANNEL.ALI_TRANSFER);
            param.setTransferNo(transferNo);
            param.setAccountName(null);
            url = BCPay.startTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_ACCOUNT_NAME_EMPTY));
        }
        param.setAccountName(accountName);
        // mock网络请求
        mockTransfer(param);
    }

    private static void mockTransfer(TransferParameter param) {

        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("url", TestConstant.MOCK_ALI_TRANSFER_URL);

        final Map<String, Object> wxRedpackMap = new HashMap<String, Object>();
        wxRedpackMap.putAll(returnMap);
        wxRedpackMap.remove("url");

        final Map<String, Object> wxTransferMap = new HashMap<String, Object>();
        wxTransferMap.putAll(returnMap);
        wxTransferMap.remove("url");

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate
                        .getkApiTransfer().substring(14)), withAny(Map.class));
                returns(returnMap, wxRedpackMap, wxTransferMap);
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), TestConstant.MOCK_APP_INVALID_ERRMSG);
            }
        };

        try {
            String url = BCPay.startTransfer(param);
            assertEquals(TestConstant.ASSERT_MESSAGE, TestConstant.MOCK_ALI_TRANSFER_URL, url);
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            param.setTransferNo(validWXTransferNo);
            param.setTotalFee(validWXRedpackTotalFee);
            param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
            String url = BCPay.startTransfer(param);
            assertEquals(TestConstant.ASSERT_MESSAGE, "", url);
        } catch (BCException ex) {
            ex.printStackTrace();
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            param.setChannel(TRANSFER_CHANNEL.WX_TRANSFER);
            String url = BCPay.startTransfer(param);
            assertEquals(TestConstant.ASSERT_MESSAGE, "", url);
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        new StrictExpectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate
                        .getkApiTransfer().substring(14)), withAny(Map.class));
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), TestConstant.MOCK_APP_INVALID_ERRMSG);
            }
        };

        try {
            String url = BCPay.startTransfer(param);
            assertEquals(TestConstant.ASSERT_MESSAGE, "", url);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (BCException ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.MOCK_APP_INVALID_ERRMSG));
        }
    }

    private static void initTransferParam(TransferParameter param) {
        param.setAccountName(accountName);
        param.setChannel(channel);
        param.setChannelUserId(channelUserId);
        param.setChannelUserName(channelUserName);
        param.setDescription(description);
        param.setRedpackInfo(redpackInfo);
        param.setTotalFee(totalFee);
        param.setTransferNo(transferNo);
    }

    static void testBCTransfer() {
        BCTransferParameter param = new BCTransferParameter();

        /*-------------------------start transfer param test------------------------*/
        try {
            BCPay.startBCTransfer(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.PAY_PARAM_EMPTY));
        }

        try {
            param.setBillNo(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_EMPTY));
        }
        param.setBillNo(billNo);

        try {
            param.setTitle(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.TITLE_EMPTY));
        }
        param.setTitle(subject);

        try {
            param.setTotalFee(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.TOTAL_FEE_EMPTY));
        }
        param.setTotalFee(1);
        //
        try {
            param.setBillNo("121");
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            param.setBillNo("111111111122222222222222222222222222222222222222");
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }
        param.setBillNo(billNo);

        try {
            param.setTradeSource(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.TRADE_SOURCE_EMPTY));
        }

        param.setTradeSource("OUT_PC");

        try {
            param.setBankCode(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BANK_CODE_EMPTY));
        }
        param.setBankCode("BOC");

        try {
            param.setBankAssociatedCode(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BANK_ASSOCIATED_CODE_EMPTY));
        }
        param.setBankAssociatedCode("111111");

        try {
            param.setBankFullName(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BANK_FULL_NAME_EMPTY));
        }
        param.setBankFullName("中国银行");

        try {
            param.setCardType(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.CARD_TYPE_EMPTY));
        }
        param.setCardType("DE");

        try {
            param.setAccountType(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.ACCOUNT_TYPE_EMPTY));
        }
        param.setAccountType("P");

        try {
            param.setAccountNo(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage()
                    .contains(TestConstant.ACCOUNT_NO_EMPTY));
        }
        param.setAccountNo("123456789");
        try {
            param.setAccountName(null);
            BCPay.startBCTransfer(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.ACCOUNT_NAME_EMPTY));
        }
    }

}

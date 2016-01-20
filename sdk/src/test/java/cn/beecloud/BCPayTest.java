package cn.beecloud;

import mockit.integration.junit4.JMockit;

import javax.ws.rs.client.Client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * JAVA SDK 单元测试主类
 * 
 * @author Rui
 */
@RunWith(JMockit.class)
public class BCPayTest {

    protected Client client;

    @Before
    public void setUp() throws Exception {
        BeeCloud.registerApp(TestConstant.KTestAppID, null, TestConstant.kTestAppSecret,
                TestConstant.kTestMasterSecret);
    }

    @Test
    public void javaSDKTest() {

        /**
         * 测试LIVE模式
         */
        PayTest.testPay();
        BillQueryTest.testQueryBillById();
        BillQueryTest.testQueryBill();
        BillQueryTest.testQueryBillCount();
        RefundTest.testRefund();
        RefundQueryTest.testQueryRefundById();
        RefundQueryTest.testQueryRefund();
        RefundQueryTest.testQueryRefundCount();
        RefundTest.testRefundUpdate();
        BatchHandleTest.testBatchRefund();
        TransferTest.testTransfer();
        TransfersTest.testTransfers();
        InternationalPayTest.testInternationalPay();
        TransferTest.testBCTransfer();

        /**
         * 测试SANDBOX模式
         */
        BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestTestSecret, null, null);
        BCCache.setSandbox(true);
        PayTest.testPay();
        BillQueryTest.testQueryBillById();
        BillQueryTest.testQueryBill();
        BillQueryTest.testQueryBillCount();
        RefundTest.testRefund();
        RefundQueryTest.testQueryRefundById();
        RefundQueryTest.testQueryRefund();
        RefundQueryTest.testQueryRefundCount();
        RefundTest.testRefundUpdate();
        BatchHandleTest.testBatchRefund();
        TransferTest.testTransfer();
        TransfersTest.testTransfers();
        InternationalPayTest.testInternationalPay();

    }
}

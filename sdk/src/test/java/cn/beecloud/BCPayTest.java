package cn.beecloud;



import static junit.framework.Assert.assertEquals;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.*;

public class BCPayTest {
	
	protected Client client;
	private String billNo;
	private String subject;
	private String refundNo;
	private String batchNo;
	private String transferId1;  
	private String transferId2;
	private Map<String, Object> payOptional = new HashMap<String, Object>();
	private Map<String, Object> refundOptional = new HashMap<String, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}  

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	
	@Test
	public void testQueryRefund() {
		BCRefundQueryParameter param = new BCRefundQueryParameter();
		BCQueryResult bcQueryResult = BCPay.startQueryRefund(param);
		System.out.println("test1");
	}
	
	@Test
	public void testQueryRefundStatus() {
		BCQueryStatusResult result = BCPay.startRefundUpdate(PAY_CHANNEL.WX, "201507149424f344");
		System.out.println("test1");
	}
	
	@Test
	public void testTransfer() {
		List<TransferData> list = new ArrayList<TransferData>();
		TransferData data1 = new TransferData("transfertest11221", "13584809743", "袁某某", 1, "赏赐");
		TransferData data2 = new TransferData("transfertest11222", "13584809742", "张某某", 1, "赏赐");
		list.add(data1);
		list.add(data2);
		
		
		BCPayResult result = BCPay.startTransfer(PAY_CHANNEL.ALI, "transfertest1122transfertest2233", "13861331391", list);
		System.out.println("test transfer!");
	}
	
	@Test
	public void testQueryBillById() {
		BCQueryResult result = BCPay.startQueryBillById("21c295fe-0f74-4697-b403-983ec61230ab");
		System.out.println(result.getOrder());
		System.out.println("test query by id!" + result);
	}
	
	@Test
	public void testQueryRefundById() {
		BCQueryResult result = BCPay.startQueryRefundById("89ef90dd-9670-4104-a4fd-117a129b9c65");
		System.out.println(result.getRefund());
		System.out.println("test refund by id!" + result);
	}
	
}

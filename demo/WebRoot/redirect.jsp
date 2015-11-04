<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Random"%>
<%@ page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@ page import="cn.beecloud.BeeCloud"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page import="cn.beecloud.BCEumeration.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.InputStream"%>
<%@ page import="cn.beecloud.bean.TransferData"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.apache.log4j.*"%>
<%@ include file="loadProperty.jsp"%>
<%
	/**
	 功能：商户结算跳转至指定支付方式页面
	 版本：3.3
	 日期：2015-03-20
	 说明：
	 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 该代码仅供学习和研究使用，只是提供一个参考。

	 //***********页面功能说明***********
	  该页面可以在本机电脑测试。
	 //********************************
	 */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>redirect</title>
</head>
<body>
	<%
	
		Logger log = Logger.getLogger(this.getClass());
		//returnUrl示例（商户根据自身系统指定）
		String yeeWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWebReturnUrl.jsp";
		String yeeWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWapReturnUrl.jsp";
		String jdWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWebReturnUrl.jsp";
		String jdWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWapReturnUrl.jsp";
		String kqReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/kqReturnUrl.jsp";
		String aliReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/aliReturnUrl.jsp";
		String unReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/unReturnUrl.jsp";
		String bdReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/bdReturnUrl.jsp";
		String msReturnUrl = "http://javademo.beecloud.cn:8080/demo/msReturnUrl.jsp";
		
		//微信 公众号id（读取配置文件conf.properties）及微信 redirec_uri
		Properties prop = loadProperty();
		String wxJSAPAppId = prop.get("wxJSAPIAppId").toString();
		String wxJSAPIRedirectUrl = "http://apitest.beecloud.cn/demo/wxJSAPIRedirectUrl.jsp";
		String encodedWSJSAPIRedirectUrl = URLEncoder.encode(wxJSAPIRedirectUrl);
		
		//模拟商户的交易编号、标题、金额、附加数据
		String billNo = BCUtil.generateRandomUUIDPure();
		String title = "demo测试";
		String totalFee = "1";
		Map<String, Object> optional = new HashMap<String, Object>();
		optional.put("rui", "睿");
		
		//易宝点卡支付参数样例
		String cardNo = "15078120125091678";
		String cardPwd = "121684730734269992";
		String frqid = "SZX";
		
		String type = request.getParameter("paytype");

		//BCPayResult的type字段有OK和非OK两种，当type字段是OK时（对应值为0），bcPayResult包含支付所需的内容如html或者code_url或者支付成功信息,
		//当type的字段为非OK的时候，，bcPayResult包含通用错误和具体的错误信息。商户系统可以任意显示，打印或者记录日志。
		BCPayResult bcPayResult = new BCPayResult();
		
		if (type.equals("alipay")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WEB, 1, billNo, title);
			param.setReturnUrl(aliReturnUrl);
			param.setBillTimeout(200);
			param.setOptional(optional);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("alipayQr")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_QRCODE, 1, billNo, title);
			param.setReturnUrl(aliReturnUrl);
			param.setQrPayMode(QR_PAY_MODE.MODE_FRONT);
			param.setBillTimeout(120);
			
            bcPayResult = BCPay.startBCPay(param);
            if (bcPayResult.getType().ordinal() == 0) {
            	out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
            
		} else if (type.equals("alipayWAP")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WAP, 1, billNo, title);
			param.setBillTimeout(140);
            
			bcPayResult = BCPay.startBCPay(param);
            if (bcPayResult.getType().ordinal() == 0) {
            	out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
            
		} else if (type.equals("wechatQr")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_NATIVE, 1, billNo, title);
			param.setBillTimeout(120);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				System.out.println(bcPayResult.getObjectId());
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("wechatJSAPI")) {
			String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxJSAPAppId+ "&redirect_uri=" + encodedWSJSAPIRedirectUrl + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
			log.info("wx jsapi redirct url:" + redirectUrl);
			response.sendRedirect(redirectUrl);
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_JSAPI, 1, billNo, title);
 			param.setOpenId("123456zxcvbnm");

 			bcPayResult = BCPay.startBCPay(param);
 			if (bcPayResult.getType().ordinal() == 0) {
 				System.out.println(bcPayResult.getWxJSAPIMap());
 			} else {
 				//handle the error message as you wish！
 				out.println(bcPayResult.getErrMsg());
 				out.println(bcPayResult.getErrDetail());
 			}
		} else if (type.equals("unionpay")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.UN_WEB, 1, billNo, title);
			param.setReturnUrl(unReturnUrl);
			param.setBillTimeout(180);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				System.out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("alitransfer")) {
			List<TransferData> list = new ArrayList<TransferData>();
			TransferData data1 = new TransferData("transfertest11223", "13584809743", "袁某某", 1, "赏赐");
			TransferData data2 = new TransferData("transfertest11224", "13584809742", "张某某", 1, "赏赐");
			list.add(data1);
			list.add(data2);
			
			
			bcPayResult = BCPay.startTransfer(PAY_CHANNEL.ALI, billNo, "苏州比可网络科技有限公司", list);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("yeeWap")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WAP, 1, billNo, title);
			param.setReturnUrl(yeeWapReturnUrl);
			param.setBillTimeout(180);
			BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
			bcPayResult = BCPay.startBCPay(param);
			BeeCloud.registerApp("c5d1cba1-5e3f-4ba0-941d-9b0a371fe719", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c");
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				System.out.println(bcPayResult.getObjectId());
				log.info("yee wap object id:" + bcPayResult.getObjectId());
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("yeeWeb")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WEB, 1, billNo, title);
			param.setReturnUrl(yeeWebReturnUrl);
			param.setBillTimeout(180);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				log.info("yee web object id:" + bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("yeeNoBankCard")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_NOBANKCARD, 10, billNo, title);
			param.setCardNo(cardNo);
			param.setCardPwd(cardPwd);
			param.setFrqid(frqid);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getSucessMsg());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		}else if (type.equals("jdWap")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WAP, 1, billNo, title);
			param.setReturnUrl(jdWapReturnUrl);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				System.out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("jdWeb")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WEB, 1, billNo, title);
			param.setReturnUrl(jdWebReturnUrl);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				System.out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("kqWeb")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, title);
			param.setBillTimeout(120);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				System.out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("kqWap")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, title);
			param.setBillTimeout(120);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				System.out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("bdWeb")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WEB, 1, billNo, title);
			param.setReturnUrl(bdReturnUrl);
			param.setBillTimeout(180);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				System.out.println(bcPayResult.getObjectId());
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("bdWap")) {
			
			BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WAP, 1, billNo, title);
			param.setReturnUrl(bdReturnUrl);
			param.setBillTimeout(180);
			
			bcPayResult = BCPay.startBCPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				System.out.println(bcPayResult.getObjectId());
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("msWeb")) {
			String msBillNo = billNo.substring(3);
			String subject = "xxxxxx";
			System.out.println("msBillNo:" + msBillNo);
			BCMSWebPayParameter param = new BCMSWebPayParameter(PAY_CHANNEL.MS_WEB, 100, msBillNo, title, subject);
			param.setReturnUrl(msReturnUrl);
			
			bcPayResult = BCPay.startBCMSWebPay(param);
			if (bcPayResult.getType().ordinal() == 0) {
				System.out.println(bcPayResult.getObjectId());
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("msWap")) {
			String msBillNo = billNo.substring(10);
			String subject = "1172001";
			System.out.println("msBillNo:" + msBillNo);
			BCMSWapBill param = new BCMSWapBill(msBillNo, 100, "test", PAY_CHANNEL.MS_WAP, null);
			param.setCustId("001986098765432123456");
			param.setType(MS_WAP_TYPE.CARD);
			param.setBankNo("03080000");
			param.setPhoneNo("13861331391");
			param.setFlag("sign");
			
			param.getCardInfo().setCardNo("5187187005718530");
			param.getCardInfo().setCustName("冯睿");
			param.getCardInfo().setCustIdType("0");
			param.getCardInfo().setCustIdNo("320503198306271012");
			param.getCardInfo().setCardType(CARD_TYPE.CREDICT);
			param.getCardInfo().setExpiredDate("0919");
			param.getCardInfo().setCvv2("250");
			
			
			
			
			System.out.println(param.getTotalFee());;
			BCMSWapPayResult result = BCPay.startBCMSWapAuth(param);
			if (result.getType().ordinal() == 0) {
				result.getWapBill().setVerifyCode(param.getToken());
				result.getWapBill().setFlag("pay");
				result.getWapBill().setSubject(subject);
				
				
				result = BCPay.startBCMSWapPay(result.getWapBill());
				if (result.getType().ordinal() == 0) {
					out.println(result.getObjectId());
					out.println(result.getRefNo());
					out.println("merTransDate:" + result.getMerTransDate());
				} else {
					//handle the error message as you wish！
					out.println(result.getErrMsg());
					out.println(result.getErrDetail());
					out.println("merTransDate:" + result.getMerTransDate());
				}
			}
			else {
				//handle the error message as you wish！
				out.println(result.getErrMsg());
				out.println(result.getErrDetail());
			}
			bcPayResult = new BCPayResult(RESULT_TYPE.OK);
		} else if (type.equals("msWap2")) {
			String msBillNo = billNo.substring(10);
			String subject = "1172001";
			System.out.println("msBillNo:" + msBillNo);
			BCMSWapBill param = new BCMSWapBill(msBillNo, 100, "test", PAY_CHANNEL.MS_WAP, null);
			param.setCustId("001986098765432123456");
			param.setType(MS_WAP_TYPE.SAVED_CARD);
			param.setBankNo("03080000");
			param.setPhoneNo("13861331391");
			param.setFlag("sign");
			
			BCMSWapPayResult result = BCPay.startBCMSWapAuth(param);
			if (result.getType().ordinal() == 0) {
				System.out.println("phone token:" + result.getWapBill().getToken());
				result.getWapBill().setVerifyCode(param.getToken());
				result.getWapBill().setFlag("pay");
				result.getWapBill().setSubject(subject);
				
				result = BCPay.startBCMSWapPay(result.getWapBill());
				if (result.getType().ordinal() == 0) {
					out.println(result.getObjectId());
					out.println(result.getRefNo());
					out.println("merTransDate:" + result.getMerTransDate());
				} else {
					//handle the error message as you wish！
					out.println(result.getErrMsg());
					out.println(result.getErrDetail());
					out.println("merTransDate:" + result.getMerTransDate());
				}
			}
			else {
				//handle the error message as you wish！
				out.println(result.getErrMsg());
				out.println(result.getErrDetail());
			}
			bcPayResult = new BCPayResult(RESULT_TYPE.OK);
		}
	%>
<div align="center" id="qrcode">
</div>
</body>
<script src="./qrcode.js"></script>
<script type="text/javascript">
    var type = '<%=type%>';
    var isCodeUrl = <%=bcPayResult.getType().ordinal()%>;
    var codeUrl;
    if (type == 'wechatQr') {
    	codeUrl = '<%=bcPayResult.getCodeUrl()%>';
    } else if (type == 'aliOfflineQr')
     	codeUrl = '<%=bcPayResult.getAliQrCode()%>';
            function makeqrcode() {
                var qr = qrcode(10, 'M');
                qr.addData(codeUrl);
                qr.make();
                var wording=document.createElement('p');
                wording.innerHTML = "扫我，扫我";
                var code=document.createElement('DIV');
                code.innerHTML = qr.createImgTag();
                var element=document.getElementById("qrcode");
                element.appendChild(wording);
                element.appendChild(code);
            }
    if ((type == 'wechatQr' || type == "aliOfflineQr") && isCodeUrl == 0) {
        makeqrcode();
    }
    
</script>
</html>
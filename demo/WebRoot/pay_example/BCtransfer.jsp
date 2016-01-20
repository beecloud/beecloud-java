<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="java.util.Properties" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ include file="loadProperty.jsp" %>

<%
    /*
	     功能：商户BC打款
	     版本：1.0
	     日期：2016-01-13
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
    <title>transfer</title>
</head>
<body>

<%
	Logger log = Logger.getLogger("BCtransfer.jsp");
	Properties prop = loadProperty();
    String billNo = BCUtil.generateRandomUUIDPure();
    BCTransferParameter bCTransferParameter = new BCTransferParameter();
    bCTransferParameter.setBillNo(billNo);
    bCTransferParameter.setTotalFee(1);
    bCTransferParameter.setTitle("测试代付");
    bCTransferParameter.setTradeSource("OUT_PC");
    bCTransferParameter.setBankCode("BOC");
    bCTransferParameter.setBankAssociatedCode("104305045378");
    bCTransferParameter.setBankFullName("中国银行");
    bCTransferParameter.setCardType("DE");
    bCTransferParameter.setAccountType("C");
    bCTransferParameter.setAccountNo("530065239604");
    bCTransferParameter.setAccountName("苏州比可网络科技有限公司");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("email","jackybian@beecloud.cn");
    bCTransferParameter.setOptional(map);
    try {
        BCPay.startBCTransfer(bCTransferParameter);
        out.println(bCTransferParameter.getResultType());
    } catch (BCException e) {
        log.error(e.getMessage(), e);
        out.println(e.getMessage());
    }

%>
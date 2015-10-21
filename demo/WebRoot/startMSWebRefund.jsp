<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Start MS Web Refund</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
	String billNo = request.getParameter("bill_no");
	String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
	BCMSRefundResult result = BCPay.startMingShengRefund(billNo, 100, refundNo);
	
	if (result.getType().ordinal() == 0 ) {
		out.println(result.getObjectId());
		System.out.println(result.getObjectId());
		Thread.sleep(5000);
		out.println(result.getSucessMsg());
		out.println(result.getRefundId());
	} else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
%>
</body>
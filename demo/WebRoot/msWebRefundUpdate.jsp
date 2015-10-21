<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Ming Sheng Web Refund Update</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
	String subChannel = request.getParameter("refundId");
	BCQueryStatusResult result = BCPay.startRefundUpdate(channelEnum, refund_no);
	if (result.getType().ordinal() == 0 ) {
		out.println(result.getRefundStatus());
	} else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
%>
</body>
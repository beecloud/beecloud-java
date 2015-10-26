<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Refund Update</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
	String subChannel = request.getParameter("channel");
	String channel = subChannel.split("_")[0];
	String refund_no = request.getParameter("refund_no");
	PAY_CHANNEL channelEnum = PAY_CHANNEL.valueOf(channel);
	if (subChannel.equals("YEE_WAP")) {
		BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
	}
	BCQueryStatusResult result = BCPay.startRefundUpdate(channelEnum, refund_no);
	if (subChannel.equals("YEE_WAP")) {
		BeeCloud.registerApp("c5d1cba1-5e3f-4ba0-941d-9b0a371fe719", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c");
	}
	if (result.getType().ordinal() == 0 ) {
		out.println(result.getRefundStatus());
	} else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
%>
</body>
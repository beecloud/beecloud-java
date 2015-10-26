<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@page import="cn.beecloud.bean.*"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<link href="demo.css" rel="stylesheet" type="text/css"/>
<title>Query Min Sheng Order By Id</title>
<script type="text/javascript">
	function startMSWebRefund(billNo) {
		window.location.href="startMSWebRefund.jsp?bill_no=" + billNo;
	}
</script>
</head>
<body>
<%
	String msWebQueryById = request.getParameter("msWebQueryById");
	
	String msWapQueryById = request.getParameter("msWapQueryById");
	
	if (msWebQueryById != null) {
		String billNo = request.getParameter("billNo");
		if (billNo.equals("")) {
			out.println("请输入订单号!");
			return;
		}
		BCMSWebQueryResult result = BCPay.startQueryMSWebBillById(billNo);
		if (result.getType().ordinal() == 0) {
			pageContext.setAttribute("msBean", result.getMsBean());
		} else {
			out.println(result.getErrMsg());
			out.println(result.getErrDetail());
		}
	} else if (msWapQueryById != null) {
		String channelTradeNo = request.getParameter("billNo");
		if (channelTradeNo.equals("")) {
			out.println("请输入渠道交易号!");
			return;
		}
		BCMSWapQueryResult result = BCPay.startQueryMSWapBillById(channelTradeNo);
		if (result.getType().ordinal() == 0) {
			pageContext.setAttribute("msWapBean", result);
		} else {
			out.println(result.getErrMsg());
			out.println(result.getErrDetail());
		}
	}
%>
	
<!--网关单笔  -->
<c:if test="${msBean != null}">
	<table border="3" class="table"><tr><th>平台交易号</th><th>商户订单号</th><th>平台接受订单时间</th><th>金额</th><th>支付银行</th><th>状态</th><th>交易类型</th><th>发起退款</th></tr>
			<tr><td>${msBean.payOrderId}</td><td>${msBean.merOrderId}</td><td>${msBean.merSendTime}</td><td>${msBean.amountSum}</td><td>${msBean.payBank}</td><td>${msBean.state}</td><td>${msBean.type}</td>
				<td align="center" >
					<input class="button" type="button" onclick="startMSWebRefund('${msBean.merOrderId}')" value="退款"/>
				</td>
			</tr>
	</table>
</c:if>
<!--快捷单笔  -->
<c:if test="${msWapBean != null}">
	<table border="3" class="table"><tr><th>交易类型</th><th>交易状态</th><th>金额</th><th>商户交易时间</th><th>商户订单号</th>
		<tr><td>${msWapBean.txnType}</td><td>${msWapBean.txnStat}</td><td>${msWapBean.amount}</td><td>${msWapBean.merTransTime}</td><td>${msWapBean.merOrderId}</td>
		</tr>
	</table>
</c:if>
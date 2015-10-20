<%@page import="cn.beecloud.bean.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<link href="demo.css" rel="stylesheet" type="text/css"/>
<title>redirect</title>
<script type="text/javascript">
	function queryStatus(channel, refund_no) {
		window.location.href="refundUpdate.jsp?refund_no=" + refund_no + "&channel=" + channel;
	}
	
	function startRefund(bill_no, total_fee, channel) {
		window.location.href="startRefund.jsp?bill_no=" + bill_no + "&total_fee=" + total_fee + "&channel=" + channel;
	}
</script>
</head>
<body>
<%
	String querytype = request.getParameter("querytype");
	
	Object queryRefund = request.getParameter("queryRefund");
	
	BCQueryResult bcQueryResult;
	
	if(queryRefund != null) {
		if (querytype.equals("aliQuery")) {
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.ALI);
			param.setNeedDetail(true);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("wechatQuery")) {
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.WX);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("unionQuery")) {
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.UN);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("yeeQuery")) {
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.YEE);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("yeeWapQuery")) {
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.YEE_WAP);
			//param.setNeedDetail(true);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
			bcQueryResult = BCPay.startQueryRefund(param);
			BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		}else if (querytype.equals("jdQuery")) {
			Date date = new Date();
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.MINUTE, -120);
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.JD);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			} 
		} else if (querytype.equals("kqQuery")) {
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.KUAIQIAN);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			} 
		} else if (querytype.equals("bdQuery")) {
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.BD);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			} 
		} else if (querytype.equals("msQuery")) {
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setChannel(PAY_CHANNEL.MS);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			} 
		}else if (querytype.equals("noChannelQuery")) {
			
			BCRefundQueryParameter param = new BCRefundQueryParameter();
			param.setLimit(50);
			
			bcQueryResult = BCPay.startQueryRefundCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryRefund(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
				pageContext.setAttribute("nochannel", true);
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} 
	}else {
		if (querytype.equals("aliQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.ALI);
			param.setNeedDetail(true);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			System.out.println(bcQueryResult.getTotalCount());
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		
		} else if (querytype.equals("wechatQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.WX);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("unionQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.UN);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("yeeQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.YEE);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("yeeWapQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.YEE_WAP);
			param.setNeedDetail(true);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
			bcQueryResult = BCPay.startQueryBill(param);
			BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("jdQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.JD);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("kqQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.KUAIQIAN);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		} else if (querytype.equals("bdQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setChannel(PAY_CHANNEL.BD);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		}else if (querytype.equals("noChannelQuery")) {
			BCQueryParameter param = new BCQueryParameter();
			param.setLimit(50);
			
			bcQueryResult = BCPay.startQueryBillCount(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("count", bcQueryResult.getTotalCount());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
			
			bcQueryResult = BCPay.startQueryBill(param);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
				pageContext.setAttribute("nochannel", true);
				pageContext.setAttribute("channel", null);
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErrDetail());
			}
		}
	}
%>
<c:if test="${billSize != null and billSize !=0}">
	<table border="3" class="table"><tr><th>订单号</th><th>总金额</th><th>标题</th><th>渠道交易号</th><th>渠道</th><th>子渠道</th><th>已付款</th><th>附加数据</th><th>渠道详细信息</th><th>已退款</th><th>创建时间</th><th>发起退款</th></tr>
		<c:forEach var="bill" items="${bills}" varStatus="index"> 
			<tr><td>${bill.billNo}</td><td>${bill.totalFee}</td><td>${bill.title}</td><td>${bill.channelTradeNo}</td><td>${bill.channel}</td><td>${bill.subChannel}</td><td>${bill.spayResult}</td><td>${bill.optional}</td><td>${bill.messageDetail}</td><td>${bill.refundResult}</td><td>${bill.dateTime}</td>
				<c:if test="${bill.spayResult == true && bill.refundResult == false && nochannel == null}">
						<td align="center" >
							<input class="button" type="button" onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${bill.subChannel}')" value="退款"/>
						</td>
				</c:if>
				<c:if test="${bill.spayResult == true && bill.refundResult == false && nochannel != null}">
						<td align="center" >
							<input class="button" type="button" onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${channel}')" value="无渠道退款"/>
						</td>
				</c:if>
			</tr>
		</c:forEach> 
		<tr><td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${count}</font></td></tr>
	</table>
</c:if>
<c:if test="${refundSize != null and refundSize !=0}">
	<table border="3" class="table"><tr><th>订单号</th><th>退款单号</th><th>标题</th><th>订单金额</th><th>退款金额</th><th>渠道</th><th>子渠道</th><th>是否结束</th><th>是否退款</th><th>附加数据</th><th>渠道详细信息</th><th>退款创建时间</th><c:if test="${isWeChat != null}"><th>退款状态查询</th></c:if></tr>
		<c:forEach var="refund" items="${refundList}" varStatus="index"> 
			<tr align="center" ><td>${refund.billNo}</td><td>${refund.refundNo}</td><td>${refund.title}</td><td>${refund.totalFee}</td><td>${refund.refundFee}</td><td>${refund.channel}</td><td>${refund.subChannel}</td><td>${refund.finished}</td><td>${refund.refunded}</td><td>${refund.optional}</td><td>${refund.messageDetail}</td><td>${refund.dateTime}</td>
			<c:if test="${fn:containsIgnoreCase(refund.channel,'WX') || fn:containsIgnoreCase(refund.channel,'YEE') || fn:containsIgnoreCase(refund.channel,'BD') || fn:containsIgnoreCase(refund.channel,'KUAIQIAN')}">
			<td>
			<input class="button" type="button" onclick="queryStatus('${refund.channel}','${refund.refundNo}')" value="查询"/>
			</td>
			</c:if>
			</tr>
		</c:forEach> 
		<tr><td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${count}</font></td></tr>
	</table>
</c:if>
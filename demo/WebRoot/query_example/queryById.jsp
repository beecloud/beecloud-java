<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.beecloud.bean.BCOrder" %>
<%@ page import="cn.beecloud.bean.BCException" %>
<%@ page import="cn.beecloud.bean.BCRefund" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <link href="../css/demo.css" rel="stylesheet" type="text/css"/>
    <title>Query By Id</title>
    <script type="text/javascript">
        function queryStatus(channel, refund_no) {
            window.location.href = "refundUpdate.jsp?refund_no=" + refund_no + "&channel=" + channel;
        }
        function startRefund(bill_no, total_fee, channel) {
            window.location.href = "startRefund.jsp?bill_no=" + bill_no + "&total_fee=" + total_fee + "&channel=" + channel;
        }
    </script>
</head>
<body>
<%
    Object queryRefund = request.getParameter("queryRefund");

    String id = request.getParameter("id");
    if (id.equals("")) {
        out.println("请输入ID!");
        return;
    }
	/*
	* bill Id或者refund Id可根据需求自行传入，本处数据作为样例数据，仅供参考
	*/
    if (queryRefund != null) {
        try {
            BCRefund result = BCPay.startQueryRefundById(id);
            pageContext.setAttribute("refund", result);
        } catch (BCException e) {
            out.println(e.getMessage());
        }

    } else {
        try {
            BCOrder result = BCPay.startQueryBillById(id);
            out.println(result);
            pageContext.setAttribute("bill", result);
        } catch (BCException e) {
            out.println(e.getMessage());
        }

    }
%>

<c:if test="${refund != null}">
    <table border="3" class="table">
        <tr>
            <th>订单号</th>
            <th>退款单号</th>
            <th>标题</th>
            <th>订单金额</th>
            <th>退款金额</th>
            <th>渠道</th>
            <th>附加数据</th>
            <th>是否结束</th>
            <th>是否退款</th>
            <th>渠道详细信息</th>
            <th>退款创建时间</th>
            <th>退款更新时间</th>
            <c:if test="${fn:containsIgnoreCase(refund.channel,'WX') || fn:containsIgnoreCase(refund.channel,'YEE') || fn:containsIgnoreCase(refund.channel,'BD') || fn:containsIgnoreCase(refund.channel,'KUAIQIAN')}">
                <th>退款状态查询</th>
            </c:if></tr>
        <tr align="center">
            <td>${refund.billNo}</td>
            <td>${refund.refundNo}</td>
            <td>${refund.title}</td>
            <td>${refund.totalFee}</td>
            <td>${refund.refundFee}</td>
            <td>${refund.channel}</td>
            <td>${refund.optional}</td>
            <td>${refund.finished}</td>
            <td>${refund.refunded}</td>
            <td>${refund.messageDetail}</td>
            <td>${refund.dateTime}</td>
            <td>${refund.updateDateTime}</td>

            <c:if test="${fn:containsIgnoreCase(refund.channel,'WX') || fn:containsIgnoreCase(refund.channel,'YEE') || fn:containsIgnoreCase(refund.channel,'BD') || fn:containsIgnoreCase(refund.channel,'KUAIQIAN')}">
                <td>
                    <input class="button" type="button" onclick="queryStatus('${refund.channel}','${refund.refundNo}')"
                           value="查询"/>
                </td>
            </c:if>

        </tr>
    </table>
</c:if>
<c:if test="${bill != null}">
    <table border="3" class="table">
        <tr>
            <th>订单号</th>
            <th>标题</th>
            <th>订单金额</th>
            <th>渠道</th>
            <th>渠道交易号</th>
            <th>附加数据</th>
            <th>是否支付成功</th>
            <th>已退款</th>
            <th>渠道详细信息</th>
            <th>订单创建时间</th>
        </tr>
        <tr align="center">
            <td>${bill.billNo}</td>
            <td>${bill.title}</td>
            <td>${bill.totalFee}</td>
            <td>${bill.channel}</td>
            <td>${bill.channelTradeNo}</td>
            <td>${bill.optional}</td>
            <td>${bill.resulted}</td>
            <td>${bill.refundResult}</td>
            <td>${bill.messageDetail}</td>
            <td>${bill.dateTime}</td>

            <td align="center">
                <c:if test="${bill.resulted == true}">
                    <input class="button" type="button"
                           onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${bill.channel}')" value="退款"/>
                </c:if>
            </td>

        </tr>
    </table>
</c:if>
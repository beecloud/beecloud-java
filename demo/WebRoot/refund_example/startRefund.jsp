<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="cn.beecloud.bean.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="org.apache.log4j.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>Start Refund</title>
    <script type="text/javascript">
    </script>
</head>
<body>
<%
    Logger log = Logger.getLogger(this.getClass());
    log.info("start-------------------" + request.getHeader("referer"));
    String isYeeWap = request.getParameter("isYeeWap");
    String billNo = request.getParameter("bill_no");
    Object channelObject = request.getParameter("channel");
    System.out.println("channelObject:" + channelObject);
    Object prefund = request.getParameter("prefund");
    System.out.println("prefund" + prefund);
    Map<String, Object> optional = new HashMap<String, Object>();
    optional.put("test", "test");
    Integer refundFee = Integer.parseInt(request.getParameter("total_fee"));
    PAY_CHANNEL channel = null;
    if (channelObject != null && !channelObject.equals("")) {
        if (channelObject.toString().contains("WX")) {
            channel = PAY_CHANNEL.WX;
        } else if (channelObject.toString().contains("ALI")) {
            channel = PAY_CHANNEL.ALI;
        } else if (channelObject.toString().contains("UN")) {
            channel = PAY_CHANNEL.UN;
        } else if (channelObject.toString().contains("YEE")) {
            channel = PAY_CHANNEL.YEE;
        } else if (channelObject.toString().contains("JD")) {
            channel = PAY_CHANNEL.JD;
        } else if (channelObject.toString().contains("KUAIQIAN")) {
            channel = PAY_CHANNEL.KUAIQIAN;
        } else if (channelObject.toString().contains("BD")) {
            channel = PAY_CHANNEL.BD;
        }
    }
    log.info("start2------------------");
    System.out.println("channel:" + channel);
    String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
    if (isYeeWap.equals("1")) {
        BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
    }
    log.info("start3------------------");
    BCRefund param = new BCRefund(billNo, refundNo, 1);
    param.setOptional(optional);
    if (prefund.toString().equals("true")) {
        param.setNeedApproval(true);
        System.out.println("dd");
    }

    log.info("start4------------------");
    log.info("start5------------------" + request.getHeader("referer"));
    log.info("before start refund!");
    try {
        BCRefund refund = BCPay.startBCRefund(param);
        if (refund.getAliRefundUrl() != null) {
            response.sendRedirect(refund.getAliRefundUrl());
        } else {
            out.println("退款成功！WX渠道还需要定期查询退款结果！");
        }
    } catch (BCException e) {
        e.printStackTrace();
    }
    log.info("after start refund!");
    if (isYeeWap.equals("1")) {
        BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
    }
%>
</body>
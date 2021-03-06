<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Enumeration" %>
<%
  /* 
         功能：PAYPAL立即支付完成后同步跳转至本页面
         版本：1.0
         日期：2015-07-20
         说明：
         以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
   */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>PAYPAL Return Url</title>
</head>
<body>
<%
	//遍历所有字段代码样例
	Enumeration<String> enu = request.getParameterNames();
	while(enu.hasMoreElements()) {
		String key = enu.nextElement();
		System.out.println("key:" + key + ";value:" + request.getParameter(key));
	}

    String billNo = (String) request.getParameter("bill_no").trim();
    String result = (String) request.getParameter("state").trim();

    if (result.equals("approved")) {
        out.println("<h3>PAYPAL支付成功，商户应自行实现成功逻辑！</h3>");
        out.println("bill no:" + billNo);
        //handle othre return parameter as you wish
        return;
    } else {
        out.println("<h3>PAYPAL支付未成功，商户应自行实现失败逻辑！</h3>");
    }
%>
</body>
</html>
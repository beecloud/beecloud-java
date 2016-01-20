<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%
    /* *
     功能：商户结算页面
     版本：3.3
     日期：2015-03-20
     说明：
     以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
     该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
     本Demo仅支持非IE浏览器，秒支付SDK无此限制。

     //***********页面功能说明***********
     该页面可以在本机电脑测试。
     UI页面
     //********************************
     * */
%>
<!doctype html>
<html>
<head>
	<link href="../css/demo.css" rel="stylesheet" type="text/css"/>
    <meta charset="utf-8">
    <title>支付页面</title>
    <style>
    	.clear:after {
    content: ".";
    display: block;
    clear: both;
    visibility: hidden;
    line-height: 0;
    height: 0;
}

html {
    width: 100%;
}

body {
    margin: 0;
    padding: 0;
    width: 100%;
    color: #111;
    font-family: "PingHei", STHeitiSC-Light, "Lucida Grande",
    "Lucida Sans Unicode", Helvetica, Arial, Verdana, sans-serif;
    font-size: 1em;
}

ul {
    list-style: none;
    padding: 0;
    margin: 0;
    width: 100%;
}

ul li {
    float: left;
    margin: 0 1em;
}

ul li img {
    cursor: pointer;
    width: 158px;
    border: rgba(0, 0, 0, 0.2) 2px solid;
}

ul li img:hover {
    box-shadow: 0 0 2px #0CA6FC;
    border: #0CA6FC 2px solid;
}

.button {
    cursor: pointer;
    display: block;
    line-height: 45px;
    text-align: center;
    width: 158px;
    height: 45px;
    margin-top: 1.5em;
    border: rgba(123, 170, 247, 1) 1px solid;
    color: #fff;
    font-size: 1.2em;
    border-top-color: #1992da;
    border-left-color: #0c75bb;
    border-right-color: #0c75bb;
    border-bottom-color: #00589c;
    -webkit-box-shadow: inset 0 1px 1px 0 #6fc5f5;
    -moz-box-shadow: inset 0 1px 1px 0 #6fc5f5;
    box-shadow: inset 0 1px 1px 0 #6fc5f5;
    background: #117ed2;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#37aaea",
    endColorstr="#117ed2");
    background: -webkit-gradient(linear, left top, left bottom, from(#37aaea),
    to(#117ed2));
    background: -moz-linear-gradient(top, #37aaea, #117ed2);
    background-image: -o-linear-gradient(top, #37aaea 0, #117ed2 100%);
    background-image: linear-gradient(to bottom, #37aaea 0, #117ed2 100%);
}

.button:hover {
    background: #1c5bad;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#2488d4",
    endColorstr="#1c5bad");
    background: -webkit-gradient(linear, left top, left bottom, from(#2488d4),
    to(#1c5bad));
    background: -moz-linear-gradient(top, #2488d4, #1c5bad);
    background-image: -o-linear-gradient(top, #2488d4 0, #1c5bad 100%);
    background-image: linear-gradient(to bottom, #2488d4 0, #1c5bad 100%);
    -webkit-box-shadow: inset 0 1px 1px 0 #64bef1;
    -moz-box-shadow: inset 0 1px 1px 0 #64bef1;
    box-shadow: inset 0 1px 1px 0 #64bef1;
}

li.clicked img {
    box-shadow: 0 0 2px #0CA6FC;
    border: #0CA6FC 2px solid;
}

input {
    display: none;
}
    </style>

</head>
<body>
<%
    BeeCloud.registerApp("c5d1cba1-5e3f-4ba0-941d-9b0a371fe719", "4bfdd244-574d-4bf3-b034-0c751ed34fee", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c", "e14ae2db-608c-4f8b-b863-c8c18953eef2");
%>
<div>
    <h2>应付总额： ¥0.01</h2>

    <p>请选择支付方式</p>
</div>
<div>
    支付平台
</div>
<form action="pay_example/pay.jsp" method="POST" target="_blank">
    <div>
        <ul class="clear" style="margin-top:20px">
            <li class="clicked" onclick="paySwitch(this)">
                <input type="radio" value="ALI_WEB" name="paytype" checked="checked">
                <img src="http://beeclouddoc.qiniudn.com/ali.png" alt="">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="WX_NATIVE" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/wechats.png" alt="">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="WX_JSAPI" name="paytype">
                <img src="http://7xavqo.com1.z0.glb.clouddn.com/wechatgzh.png" alt="">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="UN_WEB" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/unionpay.png" alt="">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="ALI_QRCODE" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/alis.png" alt="">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="ALI_WAP" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/aliwap.png" alt="">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="YEE_WAP" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/ybwap.png" alt="YEE WAP">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="YEE_WEB" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/yb.png" alt="YEE WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="YEE_NOBANKCARD" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/ybcard.png" alt="YEE WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="JD_WAP" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/jdwap.png" alt="JD　WAP">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="JD_WEB" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/jd.png" alt="JD　WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="KUAIQIAN_WAP" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/kqwap.png" alt="KUAIQIAN WAP">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="KUAIQIAN_WEB" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/kq.png" alt="KUAIQIAN WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="BD_WEB" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/bd.png" alt="KUAIQIAN WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="BD_WAP" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/bdwap.png" alt="KUAIQIAN WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="PAYPAL_PAYPAL" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/paypal.png" alt="PAYPAL PAYPAL">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="PAYPAL_CREDITCARD" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/icon_paypal_credit.png" alt="PAYPAL CREDITCARD WEB">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="PAYPAL_SAVED_CREDITCARD" name="paytype">
                <img src="http://beeclouddoc.qiniudn.com/icon_paypal_kuaijiezhifu.png" alt="PAYPAL SAVED CREDITCARD">
            </li>
        </ul>
    </div>
    <div style="clear: both;">
        <input type="submit" class="button" value="确认付款">
    </div>
</form>
<hr/>
<div>
    <h2>微信、支付宝企业打款</h2>

    <p>请选择渠道进行操作</p>
</div>
<form action="pay_example/transfer.jsp" method="POST" target="_blank">
	<div>
    	<ul>
   		 	<li onclick="paySwitch(this)">
                <input type="radio" value="WX_REDPACK" name="transferType">
                <img src="http://beeclouddoc.qiniudn.com/wx_redpack.png" alt="微信红包">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="WX_TRANSFER" name="transferType">
                <img src="http://beeclouddoc.qiniudn.com/wx_transfer.png" alt="微信单笔打款">
            </li>
             <li onclick="paySwitch(this)">
                <input type="radio" value="ALI_TRANSFER" name="transferType">
                <img src="http://beeclouddoc.qiniudn.com/ali_transfer.png" alt="支付宝单笔打款">
            </li>
            <li onclick="paySwitch(this)">
                <input type="radio" value="ALI_TRANSFER" name="batchTransferType">
                <img src="http://beeclouddoc.qiniudn.com/alitransfer.png" alt="">
            </li>
    	</ul>
    </div>
    <br/><br/>
     <div style="clear: both;">
        <input type="submit" class="button" value="确认打款">
    </div>
</form>
<hr/>

<div>
    <h2>BC代付</h2>
    <p>请选择渠道进行操作</p>
</div>
<form action="pay_example/BCtransfer.jsp" method="POST" >
    <tr><input type="input" name="email" size=10 value ="test@beecloud.cn" /></tr>
    <input type="submit" class="button" value="确认BC代付">
</form>
<hr/>

<div>
    <h2>订单查询及发起退款，退款查询，退款状态查询</h2>

    <p>请选择渠道进行操作</p>
</div>

<form action="query_example/query.jsp" method="POST" target="_blank">
    <div>
        <ul class="clear" style="margin-top:20px">
            <li class="clicked" onclick="querySwitch(this)">
                <input type="radio" value="ALI" name="querytype" checked="checked">
                <img src="http://beeclouddoc.qiniudn.com/ali.png" alt="">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="WX" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/wechat.png" alt="">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="UN" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/unionpay.png" alt="">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="YEE" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/yb.png" alt="YEE">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="YEE_WAP" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/ybwap.png" alt="YEE">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="JD" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/jd.png" alt="YEE">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="KUAIQIAN" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/kq.png" alt="YEE">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="BD" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/bd.png" alt="BAIDU">
            </li>
            <li onclick="querySwitch(this)">
                <input type="radio" value="" name="querytype">
                <img src="http://beeclouddoc.qiniudn.com/unionpay1122.png" alt="无渠道查询">
            </li>
        </ul>
    </div>
    <div style="clear: both;">
        <input name="queryBIll" type="submit" class="button" value="订单查询">
        <input name="queryRefund" type="submit" class="button" value="退款查询">
        <input name="queryPrefund" type="submit" class="button" value="预退款查询">
    </div>
</form>

<hr/>
<div>
    <h2>根据ID查询订单记录、退款记录</h2>

    <p>请输入ID:</p>
</div>

<form action="query_example/queryById.jsp" method="POST" target="_blank">
    <input type="text" name="id" style="display:block;width:300px;height:25px">

    <div style="clear: both;">
        <input name="queryBIll" type="submit" class="button" value="订单查询">
        <input name="queryRefund" type="submit" class="button" value="退款查询">
    </div>
</form>

</body>
<script type="text/javascript">
    function paySwitch(that) {
        var li = that.parentNode.children;
        for (var i = 0; i < li.length; i++) {
            li[i].className = "";
            li[i].childNodes[1].removeAttribute("checked");
        }
        console.log(li);
        that.className = "clicked";
        that.childNodes[1].setAttribute("checked", "checked");
    }
    function querySwitch(that) {
        var li = that.parentNode.children;
        for (var i = 0; i < li.length; i++) {
            li[i].className = "";
            li[i].childNodes[1].removeAttribute("checked");
        }
        console.log(li);
        that.className = "clicked";
        that.childNodes[1].setAttribute("checked", "checked");
    }
</script>
</html>

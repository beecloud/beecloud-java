## BeeCloud Java SDK (Open Source)

![pass](https://img.shields.io/badge/Build-pass-green.svg) ![license](https://img.shields.io/badge/license-MIT-brightgreen.svg) ![v2.3.0](https://img.shields.io/badge/Version-v2.3.0-blue.svg) 

## 简介

本项目的官方GitHub地址是 [https://github.com/beecloud/beecloud-java](https://github.com/beecloud/beecloud-java)

本SDK的是根据[BeeCloud Rest API](https://github.com/beecloud/beecloud-rest-api)开发的Java SDK，适用于JRE 1.6及以上平台。可以作为调用BeeCloud Rest API的示例或者直接用于生产。

## 安装

1.从[github](https://github.com/beecloud/beecloud-java/releases)下载带依赖的jar文件,然后导入到自己的工程依赖包中


2.若是工程采用maven进行依赖配置，可在自己工程的pom.xml文件里加入以下配置

```xml
<dependency>   
    <groupId>cn.beecloud</groupId>
    <artifactId>beecloud-java-sdk</artifactId>
    <version>2.3.0</version>
</dependency>
```
工程名以及版本号需要保持更新。（更新可参考本项目的pom.xml，文件最顶端）


## 注册

三个步骤，2分钟轻松搞定： 

1. 注册开发者：猛击这里注册成为[BeeCloud](https://beecloud.cn/register/)开发者

2. 注册应用：使用注册的账号登陆[控制台](https://beecloud.cn/login/)后，点击"+创建App"创建新应用

3. 在代码中注册：

  BeeCloud.registerApp(appid, appsecret);


## 使用方法

具体使用请参考本目录下的demo项目


### <a name="payment">支付</a>

支付接口接收BCPayParameter参数对象，该对象封装了发起支付所需的各个具体参数。BCPayParameter类提供了一个4个必填的具体支付参数作为参数的构造函数：
```java
public BCPayParameter(PAY_CHANNEL channel, Integer totalFee, String billNo, String title)
```
发起支付将返回BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。
#### <a name="wx_native">微信扫码调用</a>
正确状态调用getCodeUrl()方法返回二维码字符串，返回code url的格式为：weixin://wxpay/bizpayurl?sr=XXXXX。
请商户调用第三方库将返回的code url生成二维码图片。
该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_NATIVE, 1, billNo, title);  
param.setBillTimeout(120);

BCPayResult bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	System.out.println(bcPayResult.getCodeUrl());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```
#### <a name="wx_jsapi">微信公众号调用</a>
正确状态调用getWxJSAPIMap()方法返回jsapi map对象。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_JSAPI, 1, billNo, title);
param.setOpenId(openId);

BCPayResult bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	System.out.println(bcPayResult.getWxJSAPIMap());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="un_web">银联网页调用</a>
正确状态调用getHtml()方法，如将html输出至页面，即可开始银联网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.UN_WEB, 1, billNo, title);
param.setReturnUrl(unReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="ali_web">阿里网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付宝网页支付。getUrl()方法返回支付宝跳转url,推荐使用html。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WEB, 1, billNo, title);
param.setReturnUrl(aliReturnUrl);
param.setBillTimeout(120);
param.setOptional(optional);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
	out.println(bcPayResult.getUrl());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="ali_qrcode">阿里扫码调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始扫描支付。getUrl()方法返回支付宝内嵌二维码地址。需使用```<iframe>```加载此url
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_QRCODE, 1, billNo, title);
param.setReturnUrl(aliReturnUrl);
param.setQrPayMode(QR_PAY_MODE.MODE_FRONT);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
    //使用html示例
	out.println(bcPayResult.getHtml());
	//使用url示例
	out.println("<html><iframe width='420' height='330' name='url' frameborder='0' src='" + bcPayResult.getUrl() + "'></iframe</html>");
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="ali_wap">阿里移动网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付。getUrl()方法返回支付宝跳转url,推荐使用html。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WAP, 1, billNo, title);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="kuaiqian_web">快钱网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始快钱网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, title);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="kuaiqian_wap">快钱移动网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始快钱移动网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, title);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="jd_web">京东网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始京东网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WEB, 1, billNo, title);
param.setReturnUrl(jdReturnUrl);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="jd_wap">京东移动网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始京东移动网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WAP, 1, billNo, title);
param.setReturnUrl(jdReturnUrl);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="yee_web">易宝网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付。getUrl()方法返回跳转url,推荐使用html。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WEB, 1, billNo, title);
param.setReturnUrl(yeeWebReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
	out.println(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="yee_wap">易宝移动网页调用</a>
正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WAP, 1, billNo, title);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="yee_nobankcard">易宝点卡支付调用</a>
getSucessMsg()方法，getSucessMsg()方法显示支付已经成功。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_NOBANKCARD, 10, billNo, title);
param.setCardNo(cardNo);
param.setCardPwd(cardPwd);
param.setFrqid(frqid);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getObjectId());
	Thread.sleep(5000);
	out.println(bcPayResult.getSucessMsg());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="bd_wap">百度移动网页调用</a>
正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WAP, 1, billNo, title);
param.setReturnUrl(bdReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="bd_web">百度网页调用</a>
正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WEB, 1, billNo, title);
param.setReturnUrl(bdReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```



代码中的参数对象BCPayParameter封装字段显示如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX_NATIVE 微信公众号二维码支付<br/>WX_JSAPI 微信公众号支付<br/>ALI_WEB 支付宝网页支付<br/>ALI_QRCODE 支付宝内嵌二维码支付<br>ALI_WAP 支付宝移动网页支付 <br/>UN_WEB 银联网页支付<br>JD_WEB 京东网页支付<br/> JD_WAP 京东移动网页支付<br/> YEE_WEB 易宝网页支付<br/> YEE_WAP 易宝移动网页支付<br/> YEE_NOBANKCARD 易宝点卡支付<br> KUAIQIAN_WEB 快钱网页支付<br/> KUAIQIAN_WAP 快钱移动网页支付<br/>BD_WEB 百度网页支付<br>BD_WAP 百度移动网页支付（必填）
totalFee | 订单总金额， 只能为整数，单位为分，例如 1，（必填）
billNo | 商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如(201506101035040000001),（必填）
title | 订单标题， 32个字节内，最长支持16个汉字，（必填）
optional | 附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，（选填）
returnUrl | 同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB 或 JD_WEB 或 JD_WAP时为必填，（选填）
openId | 微信公众号支付(WX_JSAPI)必填，（选填）
showUrl | 商品展示地址，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder，（选填）
qrPayMode | 二维码类型，二维码类型含义MODE_BRIEF_FRONT： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px<br>MODE_FRONT： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px<br>MODE_MINI_FRONT： 订单码-迷你前置模式, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px ，（选填）
billTimeoutValue | 订单失效时间，单位秒，非零正整数，快钱不支持此参数。例如：120（选填）
cardNo | 点卡卡号，每种卡的要求不一样，例如易宝支持的QQ币卡号是9位的，江苏省内部的QQ币卡号是15位，易宝不支付，当channel 参数为YEE_NOBANKCARD时必填，（选填）
cardPwd | 点卡密码，简称卡密当channel 参数为YEE_NOBANKCARD时必填，（选填）
frqid | 点卡类型编码：<br>骏网一卡通(JUNNET)<br>盛大卡(SNDACARD)<br>神州行(SZX)<br>征途卡(ZHENGTU)<br>Q币卡(QQCARD)<br>联通卡(UNICOM)<br>久游卡(JIUYOU)<br>易充卡(YICHONGCARD)<br>网易卡(NETEASE)<br>完美卡(WANMEI)<br>搜狐卡(SOHU)<br>电信卡(TELECOM)<br>纵游一卡通(ZONGYOU)<br>天下一卡通(TIANXIA)<br>天宏一卡通(TIANHONG)<br>32 一卡通(THIRTYTWOCARD)<br>当channel 参数为YEE_NOBANKCARD时必填，（选填）
return   |  BCPayResult对象， 根据type决定返回内容


### <a name="transfer">批量打款</a>
调用以下接口发起批量退款并将得到BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。

```java
List<TransferData> list = new ArrayList<TransferData>();
TransferData data1 = new TransferData("transfertest11223", "13584809743", "袁某某", 1, "赏赐");
TransferData data2 = new TransferData("transfertest11224", "13584809742", "张某某", 1, "赏赐");
list.add(data1);
list.add(data2);


bcPayResult = BCPay.startTransfer(PAY_CHANNEL.ALI, billNo, "苏州比可网络科技有限公司", list);
if (bcPayResult.getType().ordinal() == 0) {
	response.sendRedirect(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

代码中的各个参数含义如下：

key | 说明
---- | -----
channel | 渠道类型， 暂时只支持ALI，（必填）
batchNo | 批量付款批号， 此次批量付款的唯一标示，11-32位数字字母组合，（必填）
accountName | 付款方的支付宝账户名, 支付宝账户名称,例如:毛毛，（必填）  
transferData |  付款的详细数据 {TransferData} 的 List集合，（必填）  
return | BCPayResult, 根据type决定返回内容


### <a name="refund">退款</a>
退款接口接收BCRefundParameter参数对象，该对象封装了发起退款所需的各个具体参数。BCRefundParameter类提供了一个3个必填的具体退款参数作为参数的构造函数：
```java
public BCRefundParameter(String billNo, String refundNo, Integer refundFee)
```
发起退款将得到BCPayResult对象。BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。如果是ALI退款，则需要调用getUrl()方法并跳转至该url，输入支付密码完成退款。

```java
BCRefundParameter param = new BCRefundParameter(billNo, refundNo, 1);
param.setOptional(optional);

if (result.getType().ordinal() == 0) {
    if (result.getUrl() != null) {
        //阿里退款，跳转至退款url并输入支付密码完成退款
        response.sendRedirect(result.getUrl());
    } else {
        //其他渠道退款，返回"退款成功！" 
        out.println(result.getSucessMsg());
    }
} else {
    //handle the error message as you wish！
    out.println(result.getErrMsg());
    out.println(result.getErrDetail());
}
```

代码中的参数对象BCRefundParameter封装字段显示如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX  微信<br>ALI 支付宝<br>UN 银联<br>JD 京东<br>KUAIQIAN 快钱<br>YEE 易宝<br>BD 百度，（选填，可为NULL）
refundNo | 商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”，例如：201506101035040000001	（必填）
billNo | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一，（必填）  
refundFee | 退款金额，只能为整数，单位为分，例如1，（必填）  
optional   |  附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}, （选填）
needApproval | 标识该笔是预退款还是直接退款，true为预退款，false或者 null为直接退款，（选填）  
return | BCPayResult, 根据type决定返回内容

### <a name="billQuery">订单查询</a>

订单查询接口接收BCQueryParameter对象，该对象提供了一个无参的构造函数。
```java
BCQueryParameter param = new BCQueryParameter();
```
发起订单查询后返回BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用bcQueryResult.getBcOrders()方法返回订单(BCOrderBean)的list集合。调用者可任意遍历，显示这个订单的list对象。

```java
BCQueryParameter param = new BCQueryParameter();
param.setChannel(PAY_CHANNEL.ALI);

bcQueryResult = BCPay.startQueryBill(param);
if (bcQueryResult.getType().ordinal() == 0) {
    //handle the order list as you wish.
	pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
} else {
	out.println(bcQueryResult.getErrMsg());
	out.println(bcQueryResult.getErrDetail());
}
```

代码中的参数对象BCQueryParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE<br>ALI_WAP 支付宝移动网页支付 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付<br>KUAIQIAN<br>KUAIQIAN_WEB 快钱网页支付<br>KUAIQIAN_WAP 快钱移动网页支付<br>YEE<br>YEE_WEB 易宝网页支付<br>YEE_WAP 易宝移动网页支付<br>YEE_NOBANKCARD 易宝点卡支付<br>JD<br>JD_WEB 京东网页支付<br>JD_WAP 京东移动网页支付<br>PAYPAL<br>PAYPAL_SANDBOX<br>PAYPAL_LIVE<br>BD<br>BD_WEB 百度网页支付<br>BD_APP 百度APP支付<br>BD_WAP 百度移动网页支付,（选填）
billNo | 商户订单号，String类型，（选填）
startTime | 起始时间， Date类型，（选填）  
endTime | 结束时间， Date类型， （选填）  
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	, （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据	
return | BCQueryResult, 根据type决定返回内容

### <a name="refundQuery">退款查询</a>
退款查询接口接收BCRefundQueryParameter对象，该对象提供了一个无参的构造函数。
```java
BCRefundQueryParameter param = new BCRefundQueryParameter();
```
发起退款查询将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用bcQueryResult.getBcRefundList()方法返回退款记录(BCRefundBean)的list集合。调用者可任意遍历，显示这个退款记录的list对象。

```java
BCRefundQueryParameter param = new BCRefundQueryParameter();
param.setChannel(PAY_CHANNEL.ALI);

bcQueryResult = BCPay.startQueryRefund(param);
if (bcQueryResult.getType().ordinal() == 0) {
	pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
} else {
	out.println(bcQueryResult.getErrMsg());
	out.println(bcQueryResult.getErrDetail());
}
```

代码中的参数对象BCRefundQueryParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE<br>ALI_WAP 支付宝移动网页支付 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付<br>KUAIQIAN<br>KUAIQIAN_WEB 快钱网页支付<br>KUAIQIAN_WAP 快钱移动网页支付<br>YEE<br>YEE_WEB 易宝网页支付<br>YEE_WAP 易宝移动网页支付<br>JD<br>JD_WEB 京东网页支付<br>JD_WAP<br>BD<br>BD_WEB 百度网页支付<br>BD_APP 百度APP支付<br>BD_WAP 京东移动网页支付，（选填）
billNo | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一, （选填）
refundNo | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”	，（选填）
startTime | 起始时间， Date类型，（选填）  
endTime | 结束时间， Date类型， （选填）  
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	, （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据	
return | BCQueryResult, 根据type决定返回内容



### <a name="RefundStatusQuery">退款状态查询</a>
调用以下接口发起退款状态查询并将得到BCQueryStatusResult对象，BCQueryStatusResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryStatusResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。调用参数中，channel参数包含以下取值：
WX、YEE、KUAIQIAN、BD。

正确状态调用getRefundStatus()方法返回退款状态(Success, Processing, Fail ...)。调用者可任意处理这个值。

```java
BCQueryStatusResult result = BCPay.startRefundStatusQuery(refund_no, "YEE");
if (result.getType().ordinal() == 0 ) {
	out.println(result.getRefundStatus());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
refundNo | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是退款发起当日日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。，（必填）
channel | 渠道类型， 包含WX、YEE、KUAIQIAN和BD（必填）
return | BCQueryStatusResult, 根据type决定返回内容



### <a name="billQueryById">支付订单查询(指定ID)</a>
调用以下接口发起支付订单查询（指定ID）并将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getOrder()方法返回该笔订单对象。

```java
BCQueryResult result = BCPay.startQueryBillById(id);
	if (result.getType().ordinal() == 0) {
		pageContext.setAttribute("bill", result.getOrder());
	}else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
id | 待查询订单记录的唯一标识符，（必填）
return | BCQueryResult, 根据type决定返回内容



### <a name="refundQueryById">退款订单查询(指定ID)</a>
调用以下接口发起支付订单查询（指定ID）并将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getRefund()方法返回该笔退款记录对象。

```java
BCQueryResult result = BCPay.startQueryRefundById(id);
	if (result.getType().ordinal() == 0) {
		pageContext.setAttribute("refund", result.getRefund());
	}else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
id | 待查询订单记录的唯一标识符，（必填）
return | BCQueryResult, 根据type决定返回内容


## 民生电商

以下是民生电商的接口，具体可参考Demo


### <a name="msWeb">网关支付</a>

网关支付接口接收BCMSWebPayParameter参数对象，该对象封装了发起支付所需的各个具体参数。
发起支付将返回BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始民生网关支付。

```java
BCMSWebPayParameter param = new BCMSWebPayParameter(PAY_CHANNEL.MS_WEB, 100, billNo, title, subject);
param.setReturnUrl("http://www.msyidai.com");
			
bcPayResult = BCPay.startBCMSWebPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getObjectId());
	out.println(bcPayResult.getHtml());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```
代码中的参数对象BCMSWebPayParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型，此处固定为"MS_WEB"（必填）
totalFee | 订单总金额， 必须是正整数，单位为分，最低100分 (必填)
subject | 商品种类，该参数，是从民生电商处获得 (必填)
billNo | 商户订单号，**<mark>8到30位数字和或字母组合</mark>**，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复 (必填)
title | 订单标题，UTF8编码格式，32个字节内，最长支持16个汉字 (必填)
optional | 附加数据，用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据 (选填)
returnUrl | 同步返回页面，支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径,不要包含localhost，否则渠道会认为非法(必填)

### <a name="msWapSign">快捷鉴权</a>

快捷鉴权接口接收BCMSWapBill参数对象，该对象封装了发起快捷鉴权所需的各个具体参数。

发起快捷鉴权需要指定BCMSWapBill对象的flag属性值为"sign"。

此接口返回BCMSWapPayResult对象，BCMSWapPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSWapPayResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用BCMSWapPayResult对象的getWapBill().getToken()方法，查看鉴权接口返回的鉴权令牌,用来作为发起快捷支付的输入参数。

如果是首次鉴权，需设置type为有卡鉴权，即param.setType(MS_WAP_TYPE.CARD);有卡鉴权需要设置卡信息;若卡是信用卡，则
需设置cvv2和expiredDate,即param.getCardInfo().setExpiredDate(expiredDate);param.getCardInfo().setCvv2(cvv2)。

如果是再次鉴权，则需设置type为无卡鉴权，即param.setType(MS_WAP_TYPE.SAVED_CARD);无卡鉴权不需要设置卡信息。

注意：一个custId对应一张卡信息。鉴权或支付时每次传入的custId,务必唯一对应一张卡信息。
```java
BCMSWapBill param = new BCMSWapBill(msBillNo, 100, "test", PAY_CHANNEL.MS_WAP, null);
param.setCustId("001986098765432123");
param.setBankNo("03080000");
param.setPhoneNo("13861331391");
param.setFlag("sign");
/*无卡鉴权开始*/
param.setType(MS_WAP_TYPE.SAVED_CARD);
/*无卡鉴权结束*/
/*----------------或者------------------*/
/*有卡鉴权开始*/
param.setType(MS_WAP_TYPE.CARD);
param.getCardInfo().setCardNo(cardNo);
param.getCardInfo().setCustName(custName);
param.getCardInfo().setCustIdType("0");
param.getCardInfo().setCustIdNo(custIdNo);
param.getCardInfo().setCardType(CARD_TYPE.CREDICT);
param.getCardInfo().setExpiredDate(expiredDate);
param.getCardInfo().setCvv2(cvv2);
/*有卡鉴权开始*/

BCMSWapPayResult result = BCPay.startBCMSWapAuth(param);
	if (result.getType().ordinal() == 0) {
		System.out.println("phone token:" + result.getWapBill().getToken());
	}
	else {
		//handle the error message as you wish！
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
```

### <a name="msWapPay">快捷支付</a>

快捷支付接口接收BCMSWapBill参数对象，该对象封装了发起快捷支付所需的各个具体参数。

发起快捷支付传入的参数对象需为鉴权接口返回对象result包含的BCMSWapBill对象，即result.getWapBill()。此对象已包含鉴权返回的令牌result.getWapBill().getToken()。

发起快捷支付需要指定BCMSWapBill对象的flag属性值为"pay"，指定BCMSWapBill对象的verifyCode为用户收到的短信验证码，result.getWapBill().setVerifyCode(verifyCode)

此接口返回BCMSWapPayResult对象，BCMSWapPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSWapPayResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getMerTransDate()方法，getMerTransDate()方法返回商户交易时间,用来作为发起快捷单笔订单查询的输入参数。

注意：一个custId对应一张卡信息。鉴权或支付时每次传入不同的custId,务必唯一对应一张卡信息。

```java
result.getWapBill().setVerifyCode(param.getToken());
result.getWapBill().setFlag("pay");
result.getWapBill().setSubject(subject);


result = BCPay.startBCMSWapPay(result.getWapBill());
r
if (result.getType().ordinal() == 0) {
	out.println(result.getObjectId());
	out.println(result.getRefNo());
	out.println("merTransDate:" + result.getMerTransDate());
} else {
	//handle the error message as you wish！
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
	out.println("merTransDate:" + result.getMerTransDate());
}
```
发起快捷鉴权、支付代码中的参数对象BCMSWapBill封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型，此处固定为"MS_WAP"（必填）
totalFee | 订单总金额， 必须是正整数，单位为分，最低100分 (必填)
subject | 商品种类，该参数，是从民生电商处获得 (必填)
billNo | 商户订单号，**<mark>8到30位数字和/或字母组合</mark>**，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复 (必填)
title | 订单标题，UTF8编码格式，32个字节内，最长支持16个汉字 (必填)
optional | 附加数据，用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据 (选填)
custId | 客户号,22位以内的数字或字母的组合 (必填)
cardNo | 卡号,借记卡和信用卡的卡号 (必填)
custName | 客户姓名,UTF8编码格式，32个字节内，最长支持16个汉字 (必填)
custIdNo | 证件号,身份证号，军官证等 (必填)
custIdType | 客户证件类型：<br>0	身份证类型<br>1	护照类型<br>2	军官证<br>3	士兵证<br>4	港澳台通行证<br>5	临时身份证<br>6	户口本<br>7	其他类型证件<br>9	警官证<br>12	外国人居留证<br>15	回乡证<br>16	企业营业执照<br>17	法人代码证<br>18	台胞证0代表身份证 (必填)
bankNo | 银行编号,需要快捷支付的银行号:<br>03080000	招商银行<br> 01020000	中国工商银行<br>01030000	中国农业银行<br>01050000	中国建设银行<br>01040000	中国银行<br>03100000	浦发银行<br>03010000	中国交通银行 <br>03050000	中国民生银行<br>SDB（暂不支持）	深圳发展银行<br>03060000	广东发展银行<br>03020000	中信银行<br>03040000	华夏银行<br>03090000	兴业银行<br>14055810	广州农村商业银行<br>04135810	广州银行<br>CUPS（暂不支持）	中国银联<br>65012900	上海农村商业银行<br>POST（暂不支持）	中国邮政<br>04031000	北京银行<br>03170000	渤海银行<br>14181000	北京农商银行<br>04240001	南京银行<br>03030000	中国光大银行<br>26150704	东亚银行<br>01033320	宁波银行<br>04233310	杭州银行<br>05105840	平安银行<br>04403600	徽商银行<br>03160000	浙商银行<br>04012900	上海银行<br>01000000	中国邮政储蓄银行<br>05213000	江苏银行<br>04202220	大连银行 (必填)
phoneNo | 手机号,手机11位号码 (必填)
flag | 快捷鉴权或是支付的标志字段,"sign"代表鉴权，"pay"代表支付 (必填)
expiredDate | 信用卡有效日期, 信用卡的有效日期,使用信用卡时必填 (选填)
cvv2 | 信用卡验证码, 信用卡背后的三位验证码，使用信用卡时必填 (选填)
token | 手机校验码令牌，发起快捷支付时必填 (选填)
verifyCode | 一般为6位，是民生电商发给用户的， 发起快捷支付时必填 (选填)

### <a name="msWebQuery">网关单笔订单查询</a>

网关单笔订单查询接口，此接口返回BCMSWebQueryResult对象，BCMSWebQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSWebQueryResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getMsBean()方法，getMsBean()方法返回封装网关订单记录的BCMSWebOrderBean对象。

```java
BCMSWebQueryResult result = BCPay.startQueryMSWebBillById(billNo);
if (result.getType().ordinal() == 0) {
	pageContext.setAttribute("msBean", result.getMsBean());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
billNo | 商户订单号，8到30位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复，（必填）

### <a name="msWapQuery">快捷单笔订单查询</a>

快捷单笔订单查询接口，此接口返回BCMSWapQueryResult对象，BCMSWapQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSWapQueryResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态获取BCMSWapQueryResult的各个属性。

```java
BCMSWapQueryResult result = BCPay.startQueryMSWapBillById(billNo, merTransDate);
if (result.getType().ordinal() == 0) {
	out.println(result.getTxnType());
	out.println(result.getTxnStat());
	out.println(result.getAmount());
	out.println(result.getMerTransTime());
	out.println(result.getMerOrderId());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
billNo | 商户订单号，**<mark>8到30位数字和/或字母组合</mark>**，即快捷支付时传入的billNo（必填）
merTransDate | 商户交易时间，商户端交易日期，YYYYMMDDHHMMSS格式，即由快捷支付时返回的merTransDate，（必填）



### <a name="msWebQueryBatch">网关批量订单查询</a>

网关批量订单查询接口，此接口返回BCMSWebQueryResult对象，BCMSWebQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSWebQueryResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用BCMSWebQueryResult的getMsBeanList()方法获取封装网关订单对象BCMSWebOrderBean的集合及getCount()获取批量查询的总数量。

```java
BCMSWebQueryResult result = BCPay.startQueryMSWebBill(null, startDate, endDate);
if (result.getType().ordinal() == 0) {
	pageContext.setAttribute("msWebBills", result.getMsBeanList());
	pageContext.setAttribute("count", result.getCount());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
payBank | 支付银行，要查询的银行支付渠道；无值则查所有银行，以下是支持的银行:<br>CCB	中国建设银行<br>B2C 支付渠道<br>ABC	中国农业银行B2C支付渠道<br>ICBC	中国工商银行B2C支付渠道<br>BOC	交通银行B2C支付渠道<br>GDB	广东发展银行B2C支付渠道<br>CMB	招商银行B2C支付渠道<br>CMSB	中国民生银行B2C支付渠道<br>SPDB	上海浦东发展银行B2C支付渠道<br>HXB	华夏银行B2C支付渠道<br>FUDIAN	富滇银行B2C支付渠道<br>POST	中国邮政B2C支付渠道<br>BCN	中国银行<br>CITIC  中信银行B2C支付渠道（与银行对接中）<br>SZDB	 深圳发展银行B2C支付渠道<br>CIB	兴业银行B2C支付渠道<br>CEB	光大银行B2C支付渠道<br>B2B_CCB	中国建设银行B2B支付渠道<br>B2B_ABC	中国农业银行B2B支付渠道<br>B2B_ICBC	中国工商银行B2B支付渠道<br>B2B_ZSYH	招商银行B2B支付渠道（与银行对接中）<br>B2B_SPDB	浦发银行B2B支付渠道（与银行对接中）<br>MEM	所有会员支付<br>B2B	所有b2b支付<br>B2C	所有B2C支付（选填）
startDate | 开始时间， Date类型，（必填）
endDate | 结束时间， Date类型，（必填）

网关查询（包括网关单笔和批量查询）返回错误信息对照表

Error id	|信息
---- | ----
1	| 请求数据格式校验未通过
2	| 商户MAC校验不匹配
3	| 组织商户查询结果数据失败
4	| 获取新的MAC验证串失败
5	| 将商户订查询结果据发送给商户失败
7	| 商户编号为空
8	| 商户不存在
11	| 发送商户查询请求错误信息失败
13	| 币种格式错误
14	| 支付行错误
15	| 账户编号错误
16	| 交易号错误
17	| 开始时间错误
18	| 结束时间错误
19	| 时间间隔错误


### <a name="msWebRefund">网关退款</a>

网关退款接口，此接口返回BCMSRefundResult对象，BCMSRefundResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSRefundResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用BCMSRefundResult的getRefundId()方法获取退款流水号,用于单笔退款查询。
```java
BCMSRefundResult result = BCPay.startMSWebRefund(billNo, 100, refundNo);
if (result.getType().ordinal() == 0 ) {
	out.println(result.getObjectId());
	System.out.println(result.getObjectId());
	Thread.sleep(5000);
	out.println(result.getRefundId());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```

代码中的各个参数含义如下：

key | 说明
---- | -----
billNo | 商户订单号, 8到30位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复，（必填）
refundFee | 退款金额， 必须是正整数，单位为分，最低100分，（必填）
refundNo | 商户退款单号，格式为:退款日期(8位) + 流水号(3~24 位)。请自行确保在商户系统中唯一，且退款日期必须是发起退款的当天日期,同一退款单号不可重复提交，否则会造成退款单重复。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”，（必填）

退款返回错误信息对照表
返回码|	含义
---- | ---- 
0000	| 申请成功
0003	| 版本号不正确
0004	| 传输的关键数据为空
0005	| 自动退款处理,申请人字符过长
0006	| mac校验不通过
0007	| 未找到要退款的记录，可能已结算.
0008	| 自动退款处理错误,存在退款中的记录.
0009	| 退款金额大于可退款金额.
0010	| 自动退款处理,输入金额小于或者等于0,不能退款.
0011	| 该笔订单未汇总，请在系统汇总后再发起退款。（5点到23点之间，系统在支付完成后30分钟内完成汇总。）
9999	| 其它错误

### <a name="msWebBatchRefundQuery">网关批量退款查询</a>

网关批量退款查询接口，此接口返回BCMSWebQueryResult对象，BCMSWebQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCMSWebQueryResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用BCMSWebQueryResult的getMsRefundBeanList()方法获取封装网关退款对象BCMSWebRefundBean的集合及getCount()获取批量查询的总数量。

```java
BCMSWebQueryResult result = BCPay.startQueryMSWebRefund(startDate, endDate);
if (result.getType().ordinal() == 0) {
	pageContext.setAttribute("msRefundList", result.getMsRefundBeanList());
	pageContext.setAttribute("count", result.getCount());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}	
```
代码中的各个参数含义如下：

key | 说明
---- | -----
startDate | 开始日期 Date类型，（必填）
endDate | 结束日期 Date类型，（必填）

退款查询返回错误信息对照表

返回码|	含义
---- | -----
0000	| 查询成功
0005	| 版本号不正确
0006	| 传输的关键数据为空
0007	| 此商户不存
0008	| mac不匹配
0009	| 两个日期不完整
0010	| 开始时间或结束时间格式不正确
0011	| 日期或退款id不能为空
0012	| 退款id格式不正确
0013	| 此退款记录不存在
9999	| 其它错误

### <a name="msWebRefundUpdate">网关单笔退款更新</a>

网关单笔退款更新接口，此接口返回BCQueryStatusResult对象，BCQueryStatusResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryStatusResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用BCQueryStatusResult的getRefundStatus()方法获取网关退款状态。PROCESSING:退款中；SUCCESS:退款成功；FAIL：退款失败
```java
BCQueryStatusResult result = BCPay.startMSWebRefundUpdate(refundId);
if (result.getType().ordinal() == 0 ) {
	out.println(result.getRefundStatus());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
channelRefundNo | 平台退款流水号,在民生电商系统中唯一，用于单笔退款更新,从退款处获得(refundId)，（必填）


## Demo
项目文件夹demo为我们的样例项目，详细展示如何使用java sdk.  
•关于支付宝的return_url  
请参考demo中的 aliReturnUrl.jsp 

•关于银联的return_url  
请参考demo中的 unReturnUrl.jsp

•关于京东PC网页的return_url  
请参考demo中的 jdWebReturnUrl.jsp

•关于京东移动网页的return_url  
请参考demo中的 jdWapReturnUrl.jsp

•关于快钱的return_url  
请参考demo中的 kqReturnUrl.jsp

•关于易宝PC网页的return_url  
请参考demo中的 yeeWebReturnUrl.jsp

•关于易宝移动网页的return_url  
请参考demo中的 yeeWapReturnUrl.jsp

•关于百度钱包的return_url  
请参考demo中的 bdReturnUrl.jsp

•关于民生网关的return_url  
请参考demo中的 msReturnUrl.jsp

•关于weekhook的接收  
请参考demo中的 notifyUrl.jsp  文档请阅读 [webhook](https://github.com/beecloud/beecloud-webhook)

## 测试
TODO

## 常见问题
- 根据app_id找不到对应的APP/keyspace或者app_sign不正确,或者timestamp不是当前UTC，可能的原因：系统时间不准确 app_id和secret填写不正确，请以此排查如下：<br>
1.appid和appSecret填写是否一致<br>
2.校准系统时间
- 支付宝吊起支付返回调试错误，请回到请求来源地，重新发起请求。错误代码ILLEGAL_PARTNER，可能的原因：使用了测试账号test@beecloud.cn的支付宝支付参数。请使用自己申请的支付账号。



## 代码贡献
我们非常欢迎大家来贡献代码，我们会向贡献者致以最诚挚的敬意。

一般可以通过在Github上提交[Pull Request](https://github.com/beecloud/beecloud-java)来贡献代码。

Pull Request要求

•代码规范 


•代码格式化 


•必须添加测试！ - 如果没有测试（单元测试、集成测试都可以），那么提交的补丁是不会通过的。


•记得更新文档 - 保证 README.md 以及其他相关文档及时更新，和代码的变更保持一致性。


•创建feature分支 - 最好不要从你的master分支提交 pull request。


•一个feature提交一个pull请求 - 如果你的代码变更了多个操作，那就提交多个pull请求吧。


•清晰的commit历史 - 保证你的pull请求的每次commit操作都是有意义的。如果你开发中需要执行多次的即时commit操作，那么请把它们放到一起再提交pull请求。


## 联系我们
•如果有什么问题，可以到 321545822 BeeCloud开发者大联盟QQ群提问

•更详细的文档，见源代码的注释以及[官方文档](https://beecloud.cn/doc/?index=4)

•如果发现了bug，欢迎提交[issue](https://github.com/beecloud/beecloud-java/issues)

•如果有新的需求，欢x迎提交[issue](https://github.com/beecloud/beecloud-java/issues)

## 代码许可
The MIT License (MIT).

## BeeCloud Java SDK (Open Source)
[![Build Status](https://travis-ci.org/beecloud/beecloud-java.svg?branch=master)](https://travis-ci.org/beecloud/beecloud-java)
![license](https://img.shields.io/badge/license-MIT-brightgreen.svg) ![v3.1.0](https://img.shields.io/badge/Version-v3.1.0-blue.svg) 

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
    <version>3.1.0</version>
</dependency>
```
工程名以及版本号需要保持更新。（更新可参考本项目的pom.xml，文件最顶端）


## 注册

三个步骤，2分钟轻松搞定： 

1. 注册开发者：猛击这里注册成为[BeeCloud](https://beecloud.cn/register/)开发者

2. 注册应用：使用注册的账号登陆[控制台](https://beecloud.cn/login/)后，点击"+创建App"创建新应用

3. 在代码中注册：

  BeeCloud.registerApp(appId, testSecret, appSecret, masterSecret);


## LIVE模式使用方法
BeeCloud.registerApp(appId, **testSecret**, appSecret, masterSecret);  

**LIVE**模式**testSecret**可为**null**  

**默认开启LIVE模式**


## SANDBOX模式使用方法
BeeCloud.registerApp(appId, testSecret, **appSecret**, **masterSecret**);    
BeeCloud.setSandbox(**true**);

**SANDBOX**模式**appSecret**、**masterSecret**可为**null**  

**设置sandbox属性为true，开启SANDBOX模式** <br><br>    

  
## LIVE模式部分

### <a name="INPayment">国际支付</a>

国际支付接口接收BCInternationlOrder参数对象，该对象封装了发起国际支付所需的各个具体参数。  

成功发起国际支付接口将会返回带objectId的BCInternationlOrder对象。  

若是跳转至paypal支付，返回的BCInternationlOrder对象包含跳转支付url，用户跳转至此url，登陆paypal便可完成支付。
若是直接使用信用卡支付，直接支付成功，返回的BCInternationlOrder对象包含信用卡ID，此ID在快捷支付时需要。  
若是通过信用卡ID支付，直接支付成功。
  
发起国际支付异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。

#### <a name="paypal_paypal">PAYPAL内支付</a>
```java
BCInternationlOrder internationalOrder = new BCInternationlOrder();
/*
 * PAYPAL内支付
 */
internationalOrder.setChannel(PAY_CHANNEL.PAYPAL_PAYPAL);
internationalOrder.setBillNo(billNo);
internationalOrder.setCurrency(PAYPAL_CURRENCY.USD);
internationalOrder.setTitle("paypal test");
internationalOrder.setTotalFee(1);
internationalOrder.setReturnUrl(paypalReturnUrl);
 try {
	 internationalOrder = BCPay.startBCInternatioalPay(internationalOrder);
	 out.println(internationalOrder.getObjectId());
     response.sendRedirect(internationalOrder.getUrl());
 } catch (BCException e) {
     log.error(e.getMessage(), e);
     out.println(e.getMessage());
 }
```

#### <a name="paypal_credit_card">PAYPAL信用卡支付</a>

```java
BCInternationlOrder internationalOrder = new BCInternationlOrder();
/*
 * 信用卡支付
 */
CreditCardInfo creditCardInfo = new CreditCardInfo();
creditCardInfo.setCardNo("5183182005528540");
creditCardInfo.setExpireMonth(11);
creditCardInfo.setExpireYear(19);
creditCardInfo.setCvv(350);
creditCardInfo.setFirstName("SAN");
creditCardInfo.setLastName("ZHANG");
creditCardInfo.setCardType(CARD_TYPE.mastercard);
internationalOrder.setBillNo(billNo);
internationalOrder.setChannel(PAY_CHANNEL.PAYPAL_CREDITCARD);
internationalOrder.setCreditCardInfo(creditCardInfo);
internationalOrder.setCurrency(PAYPAL_CURRENCY.USD);
internationalOrder.setTitle("paypal credit card test");
internationalOrder.setTotalFee(1);
try {
   	internationalOrder = BCPay.startBCInternatioalPay(internationalOrder);
   	out.println(internationalOrder.getObjectId());
   	out.println("PAYPAL_CREDITCARD 支付成功！");
   	out.println(internationalOrder.getCreditCardId());
   	request.getSession().setAttribute("creditCardId", internationalOrder.getCreditCardId());//存储信用卡ID
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="paypal_save_credit_id">PAYPAL信用卡ID支付</a>
```java
/*
 * 信用卡ID支付
 */
internationalOrder.setBillNo(billNo);
internationalOrder.setChannel(PAY_CHANNEL.PAYPAL_SAVED_CREDITCARD);
internationalOrder.setCurrency(PAYPAL_CURRENCY.USD);
internationalOrder.setTitle("PAYPAL_SAVED_CREDITCARD test");
internationalOrder.setTotalFee(1);
internationalOrder.setBillNo(request.getSession().getAttribute("creditCardId").toString());//使用信用卡ID
try {
   	internationalOrder = BCPay.startBCInternatioalPay(internationalOrder);
   	out.println(internationalOrder.getObjectId());
   	out.println("PAYPAL_SAVED_CREDITCARD 支付成功！");
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

代码中的参数对象BCInternationlOrder封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>PAYPAL_PAYPAL paypal内支付<br/>PAYPAL_CREDITCARD 使用信用卡支付<br/>PAYPAL_SAVED_CREDITCARD 使用存储的信用卡id支付（必填）
totalFee | 订单总金额， 只能为整数，单位为分，例如 1，（必填）
billNo | 商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如(201506101035040000001),（必填）
title | 订单标题， 32个字节内，最长支持16个汉字，（必填）
currency | 货币种类代码，包含：<br/>AUD<br/>BRL<br/>CAD<br/>CZK<br/>DKK<br/>EUR<br/>HKD<br/>HUF<br/>ILS<br/>JPY<br/>MYR<br/>MXN<br/>TWD<br/>NZD<br/>NOK<br/>PHP<br/>PLN<br/>GBP<br/>SGD<br/>SEK<br/>CHF<br/>THB<br/>TRY<br/>THB<br/>USD（必填）
creditCardInfo | 信用卡信息， 当channel为PAYPAL_CREDITCARD必填， （选填）
creditCardId | 信用卡id，当使用PAYPAL_CREDITCARD支付完成后会返回一个信用卡id， 当channel为PAYPAL_SAVED_CREDITCARD必填，（选填）
returnUrl | 同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。当channel为PAYPAL_PAYPAL时为必填，（选填）
objectId | 境外支付订单唯一标识, 下单成功后返回
url | 当channel 为PAYPAL_PAYPAL时返回，跳转支付的url

信用卡信息对象CreditCardInfo封装字段含义如下：

key | 说明
---- | -----
cardNo | 卡号，（必填）
expireMonth | 过期时间中的月，（必填）
expireYear | 过期时间中的年，（必填）
cvv | 信用卡的三位cvv码，（必填）
firstName | 用户名字，（必填）
lastName | 用户的姓，（必填）
cardType | 卡类别 visa/mastercard/discover/amex，（必填）


### <a name="payment">国内支付</a>
国内支付接口接收BCOrder参数对象，该对象封装了发起国内际支付所需的各个具体参数。  

成功发起国内支付接口将会返回带objectId的BCOrder对象。
  
发起国内支付异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。

#### <a name="ali_web">支付宝网页调用</a>
返回的BCOrder对象包含表单支付html和跳转支付url,开发者提交支付表单或者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.ALI_WEB, 1, billNo, title);
bcOrder.setBillTimeout(360);
bcOrder.setReturnUrl(aliReturnUrl);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    Thread.sleep(3000);
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="ali_wap">支付宝移动网页调用</a>
返回的BCOrder对象包含表单支付html和跳转支付url,开发者提交支付表单或者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.ALI_WAP, 1, billNo, title);
bcOrder.setBillTimeout(360);
bcOrder.setReturnUrl(aliReturnUrl);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    Thread.sleep(3000);
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="ali_qrcode">支付宝扫码调用</a>
返回的BCOrder对象包含表单支付html和跳转支付url,开发者提交支付表单或者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.ALI_QRCODE, 1, billNo, title);
bcOrder.setBillTimeout(360);
bcOrder.setReturnUrl(aliReturnUrl);
bcOrder.setQrPayMode(QR_PAY_MODE.MODE_FRONT);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    Thread.sleep(3000);
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="wx_native">微信扫码调用</a>

返回的BCOrder对象包含code url,格式为：weixin://wxpay/bizpayurl?sr=XXXXX。
调用第三方库将返回的code url生成二维码图片。
该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.WX_NATIVE, 1, billNo, title);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    out.println(bcOrder.getCodeUrl());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```
#### <a name="wx_jsapi">微信公众号调用</a>

返回的BCOrder对象包wxJSAPIMap对象；__获取openId，并使用wxJSAPIMap对象完成支付。进一步实现参考demo__
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.WX_JSAPI, 1, billNo, title);
bcOrder.setBillTimeout(360);
String openId = resultObject.get("openid").toString();//获取openId
bcOrder.setOpenId(openId);
bcOrder = BCPay.startBCPay(bcOrder);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    Map<String, String> map = bcOrder.getWxJSAPIMap();
    jsapiAppid = map.get("appId").toString();
    timeStamp = map.get("timeStamp").toString();
    nonceStr = map.get("nonceStr").toString();
    jsapipackage = map.get("package").toString();
    signType = map.get("signType").toString();
    paySign = map.get("paySign").toString();
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="un_web">银联网页调用</a>
返回的BCOrder对象包含表单支付html，开发者提交支付表单即可完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.UN_WEB, 1, billNo, title);
bcOrder.setReturnUrl(unReturnUrl);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="kuaiqian_web">快钱网页调用</a>
返回的BCOrder对象包含表单支付html，开发者提交支付表单即可完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, title);
bcOrder.setReturnUrl(kqReturnUrl);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="kuaiqian_wap">快钱移动网页调用</a>
返回的BCOrder对象包含表单支付html，开发者提交支付表单即可完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, title);
bcOrder.setReturnUrl(kqReturnUrl);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="jd_web">京东网页调用</a>
返回的BCOrder对象包含表单支付html，开发者提交支付表单即可完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.JD_WEB, 1, billNo, title);
bcOrder.setReturnUrl(jdReturnUrl);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="jd_wap">京东移动网页调用</a>
返回的BCOrder对象包含表单支付html，开发者提交支付表单即可完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.JD_WAP, 1, billNo, title);
bcOrder.setReturnUrl(jdReturnUrl);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    out.println(bcOrder.getHtml());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="yee_web">易宝网页调用</a>
返回的BCOrder对象包含跳转支付url,开发者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.YEE_WEB, 1, billNo, title);
bcOrder.setReturnUrl(yeeWebReturnUrl);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    response.sendRedirect(bcOrder.getUrl());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="yee_wap">易宝移动网页调用</a>
返回的BCOrder对象包含跳转支付url,开发者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.YEE_WAP, 1, billNo, title);
bcOrder.setBillTimeout(360);
bcOrder.setReturnUrl(yeeWapReturnUrl);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    response.sendRedirect(bcOrder.getUrl());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="yee_nobankcard">易宝点卡支付调用</a>
返回的BCOrder对象包含objectId, 支付完成。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.YEE_NOBANKCARD, 1, billNo, title);
String cardNo = "15078120125091678";
String cardPwd = "121684730734269992";
String frqid = "SZX";
bcOrder.setTotalFee(10);
bcOrder.setCardNo(cardNo);
bcOrder.setCardPwd(cardPwd);
bcOrder.setFrqid(frqid);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println("点卡支付成功！");
    out.println(bcOrder.getObjectId());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="bd_wap">百度移动网页调用</a>
返回的BCOrder对象包含跳转支付url,开发者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.BD_WAP, 1, billNo, title);
bcOrder.setReturnUrl(bdReturnUrl);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    response.sendRedirect(bcOrder.getUrl());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="bd_web">百度网页调用</a>
返回的BCOrder对象包含跳转支付url,开发者跳转至url完成支付。
```java
BCOrder bcOrder = new BCOrder(PAY_CHANNEL.BD_WEB, 1, billNo, title);
bcOrder.setReturnUrl(bdReturnUrl);
bcOrder.setBillTimeout(360);
try {
    bcOrder = BCPay.startBCPay(bcOrder);
    out.println(bcOrder.getObjectId());
    response.sendRedirect(bcOrder.getUrl());
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
```


<a name="payParam"/>代码中的参数对象BCOrder封装字段含义如下：
请求参数及返回字段：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX_NATIVE 微信公众号二维码支付<br/>WX_JSAPI 微信公众号支付<br/>ALI_WEB 支付宝网页支付<br/>ALI_QRCODE 支付宝内嵌二维码支付<br>ALI_WAP 支付宝移动网页支付 <br/>UN_WEB 银联网页支付<br>JD_WEB 京东网页支付<br/> JD_WAP 京东移动网页支付<br/> YEE_WEB 易宝网页支付<br/> YEE_WAP 易宝移动网页支付<br/> YEE_NOBANKCARD 易宝点卡支付<br> KUAIQIAN_WEB 快钱网页支付<br/> KUAIQIAN_WAP 快钱移动网页支付<br/>BD_WEB 百度网页支付<br>BD_WAP 百度移动网页支付（必填）
totalFee | 订单总金额， 只能为整数，单位为分，例如 1，（必填）
billNo | 商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如(201506101035040000001),（必填）
title | 订单标题， 32个字节内，最长支持16个汉字，（必填）
optional | 附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，（选填）
returnUrl | 同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径，必须为http://或者https://开头。当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB 或 JD_WEB 或 JD_WAP时为必填，（选填）
openId | 微信公众号支付(WX_JSAPI)必填，（选填）
showUrl | 商品展示地址，当channel为ALI_WEB时选填，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder，（选填）
qrPayMode | 二维码类型，ALI_QRCODE的必填参数，二维码类型含义<br>MODE_BRIEF_FRONT： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px<br>MODE_FRONT： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px<br>MODE_MINI_FRONT： 订单码-迷你前置模式, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px ，（选填）
billTimeoutValue | 订单失效时间，单位秒，非零正整数，建议最短失效时间间隔必须大于360秒，快钱不支持此参数。例如：360（选填）
cardNo | 点卡卡号，每种卡的要求不一样，例如易宝支持的QQ币卡号是9位的，江苏省内部的QQ币卡号是15位，易宝不支付，当channel 参数为YEE_NOBANKCARD时必填，（选填）
cardPwd | 点卡密码，简称卡密当channel 参数为YEE_NOBANKCARD时必填，（选填）
frqid | 点卡类型编码：<br>骏网一卡通(JUNNET)<br>盛大卡(SNDACARD)<br>神州行(SZX)<br>征途卡(ZHENGTU)<br>Q币卡(QQCARD)<br>联通卡(UNICOM)<br>久游卡(JIUYOU)<br>易充卡(YICHONGCARD)<br>网易卡(NETEASE)<br>完美卡(WANMEI)<br>搜狐卡(SOHU)<br>电信卡(TELECOM)<br>纵游一卡通(ZONGYOU)<br>天下一卡通(TIANXIA)<br>天宏一卡通(TIANHONG)<br>32 一卡通(THIRTYTWOCARD)<br>当channel 参数为YEE_NOBANKCARD时必填，（选填）
objectId   |  支付订单唯一标识, 下单成功后返回
codeUrl   |  微信扫码code url， 微信扫码支付下单成功时返回
url   |  支付跳转url，当渠道为ALI_WEB 或 ALI_QRCODE 或 ALI_WAP 或 YEE_WAP 或 YEE_WEB 或 BD_WEB 或 BD_WAP，并且下单成功时返回
html   |  支付提交html， 当渠道为ALI_WEB 或 ALI_QRCODE 或 ALI_WAP 或 UN_WEB 或 JD_WAP 或 JD_WEB 或 KUAIQIAN_WAP 或 KUAIQIAN_WEB，并且下单成功时返回
wxJSAPIMap   |  微信公众号支付要素，微信公众号支付下单成功时返回


<a name="billQueryJump"/>查询返回字段：

key | 说明
---- | -----
objectId   |  支付订单唯一标识, 可通过查询获得
billNo   |  商户订单号, 可通过查询获得
totalFee   |  订单总金额, 可通过查询获得
title   |  订单标题, 可通过查询获得
channel   |  渠道类型, 可通过查询获得
channelTradeNo   |  渠道交易号， 支付完成之后可通过查询获得
resulted   |  是否支付， 可通过查询获得
refundResult   |  是否退款， 可通过查询获得
revertResult   |  订单是否撤销， 可通过查询获得
messageDetail   |  渠道详细信息，默认为"不显示"， 当needDetail为true时，并于支付完成之后可通过查询获得
dateTime   |  订单创建时间，yyyy-MM-dd HH:mm:ss格式，可通过查询获得
optionalString   |  optional json字符串， 可通过查询获得

### <a name="transfer">单笔打款</a>
单笔打款接口接收TransferParameter参数对象，该对象封装了发起单笔打款所需的各个具体参数。  

成功发起单笔打款将会返回单笔打款跳转url或者空字符串。
  
发起单笔打款异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。

#### <a name="ali_transfer">支付宝单笔打款</a>
返回跳转打款url,开发者跳转至url完成打款。
```java
TransferParameter param = new TransferParameter();
param.setChannel(TRANSFER_CHANNEL.ALI_TRANSFER);
param.setChannelUserId(aliUserId);
param.setChannelUserName(aliUserName);
param.setTotalFee(1);
param.setDescription("支付宝单笔打款！");
param.setAccountName("苏州比可网络科技有限公司");
param.setTransferNo(aliTransferNo);
try {
    String url = BCPay.startTransfer(param);
    response.sendRedirect(url);
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="wx_redpack">微信红包</a>
返回空字符串，完成打款。
```java
TransferParameter param = new TransferParameter();
param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
param.setChannelUserId(openId);
param.setTransferNo(redpackTransferNo);
param.setTotalFee(200);
RedpackInfo redpackInfo = new RedpackInfo();
redpackInfo.setActivityName(activityName);
redpackInfo.setSendName(sendName);
redpackInfo.setWishing(wishing);
param.setRedpackInfo(redpackInfo);
param.setDescription("发红包");
try {
    String result = BCPay.startTransfer(param);
    out.println("微信红包发送成功！");
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

#### <a name="ali_transfer">微信单笔打款</a>
返回空字符串，完成打款。
```java
TransferParameter param = new TransferParameter();
param.setChannel(TRANSFER_CHANNEL.WX_TRANSFER);
param.setChannelUserId(openId);
param.setTransferNo(wxTransferNo);
param.setTotalFee(200);
param.setDescription("微信单笔打款！");
try {
    String result = BCPay.startTransfer(param);
    out.println("微信单笔打款成功！");
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

代码中的参数对象TransferParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX_TRANSFER 支付宝单笔打款<br/>WX_REDPACK 微信红包<br/>WX_TRANSFER 微信单笔打款，（必填）
transferNo | 打款单号，支付宝为11-32位数字字母组合， 微信为10位数字，（必填）
totalFee | 打款金额，此次打款的金额,单位分,正整数(微信红包1.00-200元，微信打款>=1元)，（必填）
description | 打款说明，此次打款的说明，（必填）
channelUserId | 用户id，支付渠道方内收款人的标示, 微信为openid, 支付宝为支付宝账户，（必填）
channelUserName | 用户名，支付渠道内收款人账户名，支付宝必填，（选填）
redpackInfo | 红包信息，微信红包的详细描述，微信红包必填，（选填）
accountName | 打款方账号名称，打款方账号名全称，支付宝必填，例如：苏州比可网络科技有限公司，（选填）

红包信息对象redpackInfo封装字段含义如下：

key | 说明
---- | -----
sendName | 红包发送者名称 32位，（必填）
wishing | 红包祝福语 128 位，（必填）
activityName | 红包活动名称 32位，（必填）


### <a name="transfer">批量打款</a>
批量打款接口接收TransfersParameter参数对象，该对象封装了发起批量打款所需的各个具体参数。  

成功发起批量打款将会返回批量打款跳转url。
  
发起批量打款异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
TransfersParameter para = new TransfersParameter();
para.setBatchNo(batchNo);
para.setAccountName(accountName);
para.setTransferDataList(list);
List<ALITransferData> list = new ArrayList<ALITransferData>();
ALITransferData data1 = new ALITransferData("transfertest11223", "13584809743", "袁某某", 1, "赏赐");
ALITransferData data2 = new ALITransferData("transfertest11224", "13584809742", "张某某", 1, "赏赐");
list.add(data1);
list.add(data2);
try {
    String url = BCPay.startTransfers(para);
    response.sendRedirect(url);
} catch (BCException e) {
    log.error(e.getMessage(), e);
    out.println(e.getMessage());
}
```

代码中的TransfersParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 暂时只支持ALI，（必填）
batchNo | 批量付款批号， 此次批量付款的唯一标示，11-32位数字字母组合，（必填）
accountName | 付款方的支付宝账户名, 支付宝账户名称,例如:毛毛，（必填）  
transferDataList |  付款的详细数据 {ALITransferData} 的 List集合，（必填）  

付款详细数据对象ALITransferData封装字段含义如下：

key | 说明
---- | -----
transferId | 付款流水号，32位以内数字字母，（必填）
receiverAccount | 收款方账户，（必填）
receiverName | 收款方账号姓名，（必填）
transferFee | 打款金额，单位为分，（必填）
transferNote | 打款备注，（必填）


### <a name="refund">退款</a>

退款接口接收BCRefund参数对象，该对象封装了发起退款所需的各个具体参数。  

成功发起退款接口将会返回带objectId的BCRefund对象。
退款接口分为直接退款和预退款功能，当BCRefund的**needApproval**属性设置为**true**时，开启预退款功能，当BCRefund的**needApproval**属性为**空**或者**false**, 开启直接退款功能，并在channel为ALI时返回带支付宝退款跳转url的BCRefund对象, 开发者跳转至url输入支付密码完成退款。

发起退款异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCRefund refund = new BCRefund(billNo, refundNo, 1);
try {
    BCRefund refund = BCPay.startBCRefund(refund);
    if (refund.getAliRefundUrl() != null) {//直接退款（支付宝）
        response.sendRedirect(refund.getAliRefundUrl());
    } else {
    	if (refund.isNeedApproval() != null && refund.isNeedApproval()) {//预退款
    		out.println("预退款成功！");
    		out.println(refund.getObjectId());
    	} else {//直接退款
        	out.println("退款成功！WX、易宝、百度、快钱渠道还需要定期查询退款结果！");
        	out.println(refund.getObjectId());
    	}
    }
} catch (BCException e) {
    out.println(e.getMessage());
    e.printStackTrace();
}
```

代码中的参数对象BCRefund封装字段含义如下：
请求参数及返回字段：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX  微信<br>ALI 支付宝<br>UN 银联<br>JD 京东<br>KUAIQIAN 快钱<br>YEE 易宝<br>BD 百度，（选填，可为NULL）
refundNo | 商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”，例如：201506101035040000001	（必填）
billNo | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一，（必填）  
refundFee | 退款金额，只能为整数，单位为分，例如1，（必填）
optional   |  附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}, （选填）
needApproval | 标识该笔是预退款还是直接退款，true为预退款，false或者 null为直接退款，（选填）  
objectId | 退款记录唯一标识，发起退款成功后返回
aliRefundUrl | 阿里退款跳转url，支付宝发起直接退款成功后返回


<a name="refundQueryJump"/>查询返回字段：

key | 说明
---- | -----
objectId | 退款记录唯一标识，可通过查询返回
billNo | 商户订单号，可通过查询返回
refundNo | 商户退款单号，可通过查询返回
totalFee | 订单总金额，可通过查询返回
refundFee | 退款金额，可通过查询返回
channel | 渠道类型，可通过查询返回
optionalString | 附加数据json字符串，可通过查询返回
title | 标题，可通过查询返回
finished | 退款是否结束，可通过查询返回
refunded | 退款是否成功，可通过查询返回
dateTime   |  订单创建时间，yyyy-MM-dd HH:mm:ss格式，可通过查询获得
messageDetail | 渠道详细信息，默认为"不显示"， 当needDetail为true时，可通过查询获得


### <a name="refund">预退款批量审核</a>
预退款批量审核接口接收BCBatchRefund参数对象，该对象封装了发起预退款批量审核所需的各个具体参数。  

成功发起预退款批量审核接口将会返回审核后的BCBatchRefund对象。

预退款批量审核接口分为批量同意和批量否决，当BCBatchRefund的**agree**属性设置为**false**时，开启批量否决，当BCBatchRefund的**agree**属性为**true**, 开启批量同意，返回的BCBatchRefund对象包含每笔预退款真正退款后的结果消息的idResult（Map<String, String）对象，并在channel为ALI时返回带支付宝退款跳转url的BCBatchRefund对象, 开发者跳转至url输入支付密码完成退款。

发起预退款批量审核异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCBatchRefund batchRefundAgree = new BCBatchRefund();
batchRefundAgree.setIds(Arrays.asList(ids));
batchRefundAgree.setChannel(channel);
batchRefundAgree.setAgree(true);//批量同意
try {
	BCBatchRefund result = BCPay.startBatchRefund(batchRefundAgree);
	out.println("<div>");
    for (String key : result.getIdResult().keySet()) {
        String info = result.getIdResult().get(key);
        out.println(key + ":" + info + "<br/>");
    }
    if (channel.equals(PAY_CHANNEL.ALI))
        response.sendRedirect(result.getAliRefundUrl());
} catch(BCException ex) {
	ex.printStackTrace();
    out.println(ex.getMessage());
}

BCBatchRefund batchRefundDeny = new BCBatchRefund();
batchRefundDeny.setIds(Arrays.asList(ids));
batchRefundDeny.setChannel(channel);
batchRefundDeny.setAgree(false);//批量否决
try {
	BCBatchRefund result = BCPay.startBatchRefund(batchRefundDeny);
    out.println("<h3>批量驳回成功!</h3>");
} catch(BCException ex) {
	ex.printStackTrace();
    out.println(ex.getMessage());
}
```
代码中的参数对象BCBatchRefund封装字段含义如下：
请求参数及返回字段：

key | 说明
---- | -----
ids | 退款记录id列表，批量审核的退款记录的唯一标识符集合，（必填）
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br/>WX、ALI、UN、YEE、JD、KUAIQIAN、BD（必填）
agree | 同意或者驳回，批量驳回传false，批量同意传true，（必填）
idResult | 退款id、结果信息集合，Map类型，key为退款记录id,当退款成功时，value值为"OK"；当退款失败时， value值为具体的错误信息
aliRefundUrl | 支付宝批量退款跳转url，支付宝预退款批量同意处理成功后返回


### <a name="billQuery">订单查询</a>

订单查询接收BCQueryParameter参数对象，该对象封装了发起订单查询所需的各个具体参数。  

成功发起订单查询接口将会返回BCOrder对象的集合。

发起订单查询异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCQueryParameter param = new BCQueryParameter();
param.setNeedDetail(true);//设置返回messgeDetail
param.setChannel(channel);//设置查询条件channel
try {
    List<BCOrder> bcOrders = BCPay.startQueryBill(param);
    System.out.println("billSize:" + bcOrders.size());
} catch (BCException e) {
    out.println(e.getMessage());
}
```

代码中的参数对象BCQueryParameter封装字段含义如下：<a name="billQueryParam"/>

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE<br>ALI_WAP 支付宝移动网页支付 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付<br>KUAIQIAN<br>KUAIQIAN_WEB 快钱网页支付<br>KUAIQIAN_WAP 快钱移动网页支付<br>YEE<br>YEE_WEB 易宝网页支付<br>YEE_WAP 易宝移动网页支付<br>YEE_NOBANKCARD 易宝点卡支付<br>JD<br>JD_WEB 京东网页支付<br>JD_WAP 京东移动网页支付<br>PAYPAL<br>PAYPAL_SANDBOX<br>PAYPAL_LIVE<br>BD<br>BD_WEB 百度网页支付<br>BD_APP 百度APP支付<br>BD_WAP 百度移动网页支付,（选填）
billNo | 商户订单号，String类型，（选填）
startTime | 起始时间， Date类型，（选填）  
endTime | 结束时间， Date类型， （选填）  
payResult |支付成功与否标识，（选填）
needDetail | 是否需要返回渠道详细信息，不返回可减少网络开销，（选填）
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	, （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据

返回的BCOrder集合包含字段参考国内支付部分的[查询返回](#billQueryJump)字段。

### <a name="billCountQuery">订单总数查询</a>

订单总数查接收BCQueryParameter参数对象，该对象封装了发起订单总数查所需的各个具体参数。  

成功发起订单总数查询接口将会返回订单总数。

发起订单总数查询异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCQueryParameter param = new BCQueryParameter();
try {
    int count = BCPay.startQueryBillCount(param);
    pageContext.setAttribute("count", count);
} catch (BCException e) {
    out.println(e.getMessage());
}
```

代码中的参数对象BCQueryParameter可设置查询条件参考[订单查询的参数](#billQueryParam)含义部分，并排除**skip**, **limit**, **needDetail**三个参数。

### <a name="billQueryById">单笔订单查询</a>

单笔订单查询接收订单的唯一标识。

成功发起单笔订单查询接口将会返回BCOrder对象。

发起单笔订单查询异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
try {
    BCOrder result = BCPay.startQueryBillById(id);
    pageContext.setAttribute("bill", result);
} catch (BCException e) {
    out.println(e.getMessage());
}
```

返回的BCOrder对象包含字段参考国内支付部分的[查询返回](#billQueryJump)字段。


### <a name="refundQuery">退款查询</a>
退款查询接收BCQueryParameter参数对象，该对象封装了发起退款查询所需的各个具体参数。  

成功发起退款查询接口将会返回BCRefund对象的集合。

发起退款查询异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCQueryParameter param = new BCQueryParameter();
param.setChannel(channel);
param.setNeedDetail(true);
try {
    List<BCRefund> bcRefunds = BCPay.startQueryRefund(param);
    pageContext.setAttribute("refundList", bcRefunds);
    System.out.println("refundList:" + bcRefunds.size());
} catch (BCException e) {
    e.printStackTrace();
    out.println(e.getMessage());
}
```

代码中的参数对象BCQueryParameter封装字段含义如下：<a name="refundQueryParam"/>

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE<br>ALI_WAP 支付宝移动网页支付 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付<br>KUAIQIAN<br>KUAIQIAN_WEB 快钱网页支付<br>KUAIQIAN_WAP 快钱移动网页支付<br>YEE<br>YEE_WEB 易宝网页支付<br>YEE_WAP 易宝移动网页支付<br>JD<br>JD_WEB 京东网页支付<br>JD_WAP<br>BD<br>BD_WEB 百度网页支付<br>BD_APP 百度APP支付<br>BD_WAP 京东移动网页支付，（选填）
billNo | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一, （选填）
refundNo | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”	，（选填）
startTime | 起始时间， Date类型，（选填）  
endTime | 结束时间， Date类型， （选填）  
needDetail | 是否需要返回渠道详细信息，不返回可减少网络开销，（选填）
needApproval | 是否是预退款，（选填）
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	, （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据  

返回的BCRefund集合包含字段参考退款部分的[查询返回](#refundQueryJump)字段。


### <a name="refundCountQuery">退款总数查询</a>

退款总数查询接收BCQueryParameter参数对象，该对象封装了发起退款总数查询所需的各个具体参数。  

成功发起退款总数查询接口将会返回订单总数。

发起退款总数查询异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCQueryParameter param = new BCQueryParameter();
try {
    int count = BCPay.startQueryRefundCount(param);
    pageContext.setAttribute("count", count);
} catch (BCException e) {
    out.println(e.getMessage());
}
```

代码中的参数对象BCQueryParameter可设置查询条件参考[退款查询的参数](#refundQueryParam)含义部分，并排除**skip**, **limit**, **needDetail**三个参数。

### <a name="refundQueryById">单笔退款查询</a>

单笔退款查询接收订单的唯一标识。

成功发起单笔退款查询接口将会返回BCRefund对象。

发起单笔退款查询异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
try {
    BCRefund result = BCPay.startQueryRefundById(id);
    pageContext.setAttribute("refund", result);
} catch (BCException e) {
    out.println(e.getMessage());
}
```

返回的BCRefund包含字段参考退款部分的[查询返回](#refundQueryJump)字段。

### <a name="RefundStatusQuery">退款状态更新</a>
退款状态更新接收channel和refundNo参数，__调用参数中，只有当channel是WX、YEE、KUAIQIAN或BD时，才需要并且必须调用退款状态更新接口，其他渠道的退款已经在退款接口中完成__。

成功发起退款状态更新接口将会返回退款状态字符串（SUCCESS, PROCESSING, FAIL ...）。

发起退款状态更新异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
try {
    String result = BCPay.startRefundUpdate(channel, refund_no);
    out.println(result);
} catch(BCException ex) {
	out.println(ex.getMessage());
	log.info(ex.getMessage());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
refundNo | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是退款发起当日日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。，（必填）
channel | 渠道类型， 包含WX、YEE、KUAIQIAN和BD（必填）
<br>

### <a name="BCTransfer">BC代付</a>
发起代付请求。BCTransferParameter对象包含了发起BC代付所需要的所有参数。
发起BC代付异常情况将抛出BCException, 开发者需要捕获此异常进行相应失败操作 开发者可根据异常消息判断异常的具体信息，异常信息的格式为<mark>"resultCode:xxx;resultMsg:xxx;errDetail:xxx"</mark>。
```java
BCTransferParameter param = new BCTransferParameter();
param.setBillNo("1111111111");//设置订单号 8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
param.setTitle("subject");//设置标题 UTF8编码格式，32个字节内，最长支持16个汉字
param.setTotalFee(1);//设置下发订单总金额 必须是正整数，单位为分
param.setTradeSource("OUT_PC");//UTF8编码格式，目前只能填写OUT_PC
param.setBankCode("BOC");//设置银行缩写，BOC代表中国银行
param.setBankAssociatedCode("111111");//设置银行联行行号，该值需要用户自己去查询
param.setBankFullName("中国银行");//银行全称，不能缩写
param.setCardType("DE");//卡类型 DE代表借记卡，CR代表信用卡，其他值为非法
param.setAccountType("P");//账户类型 区分对公和对私 P代表私户，C代表公户，其他值为非法
param.setAccountNo("123456789");//收款方的银行卡号
param.setAccountName("beecloud");//收款方的姓名或者单位名
try {
	BCPay.startBCTransfer(param);
}
 catch (Exception e) {
	out.println(ex.getMessage());
	og.info(ex.getMessage());
}		
```

## SANDBOX模式部分

### <a name="sandboxPayment">国内支付</a>
国内支付接口完全参考[LIVE模式](#payment)订单查询, **暂不支持WX_JSAPI**
沙箱模式调起支付返回的支付要素(html、url、codeUrl)为BeeCloud提供的模拟支付要素

### <a name="sandboxBillQuery">订单查询</a>
订单查询接口完全参考[LIVE模式](#billQuery)订单查询

### <a name="sandboxBillQuery">订单总数查询</a>
订单总数查询接口完全参考[LIVE模式](#billCountQuery)订单总数查询

### <a name="sandboxBillQueryById">单笔订单查询</a>
单笔订单查询接口完全参考[LIVE模式](#billQueryById)单笔订单查询  

  
**其他接口暂不支持SANDBOX模式**  



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

•关于PAYPAL内支付的return_url  
请参考demo中的 paypalReturnUrl.jsp

•关于weekhook的接收  
请参考demo中的 webhook_receiver.jsp以及webhook_sandbox_receiver.jsp  
文档请阅读 [webhook](https://github.com/beecloud/beecloud-webhook)

## 测试
- 下载安装maven后，进入sdk文件夹，执行mvn test。
- 导入sdk至eclipse或者IDEA, 在src/test/java包下找到BCPayTest类，运行javaSDKTest()方法。

## 常见问题
- 根据app_id找不到对应的APP/keyspace或者app_sign不正确,或者timestamp不是当前UTC，可能的原因：系统时间不准确 app_id和secret填写不正确，请以此排查如下：
1.appid和appSecret填写是否一致  
2.校准系统时间
- 支付宝吊起支付返回调试错误，请回到请求来源地，重新发起请求。错误代码ILLEGAL_PARTNER，可能的原因：使用了测试账号test@beecloud.cn的支付宝支付参数。请使用自己申请的支付账号。
- SDK jar包导入项目时找不到依赖包或者报NoSuchMethodException异常等问题，可能的原因:相同jar包依赖不同导致的冲突，相同jar包版本不同导致的冲突，解决方法如下：  
1.使用Maven配置依赖引入sdk, 删掉导致冲突的SDK的依赖包。
2.若不使用Maven配置依赖，分开导入无依赖的sdk包和sdk依赖的包(可从[Release](https://github.com/beecloud/beecloud-java)部分下载)，删除导致冲突的sdk依赖包。  
3.手动加入错误提示找不到的依赖包。



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

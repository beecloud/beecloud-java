/**
 * *************************
 *
 * @Date: Mar 18, 2015
 * @Time: 4:50:02 PM
 * @Author: Rui.Feng
 * <p/>
 * **************************
 */
package cn.beecloud;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;   
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCMSRefundResult;
import cn.beecloud.bean.BCMSWapPayParameter;
import cn.beecloud.bean.BCMSWapQueryResult;
import cn.beecloud.bean.BCMSWebOrderBean;
import cn.beecloud.bean.BCMSWebPayParameter;
import cn.beecloud.bean.BCMSWebQueryResult;
import cn.beecloud.bean.BCMSWebRefundBean;
import cn.beecloud.bean.BCOrderBean;
import cn.beecloud.bean.BCPayParameter;
import cn.beecloud.bean.BCQueryParameter;
import cn.beecloud.bean.BCRefundBean;
import cn.beecloud.bean.BCRefundParameter;
import cn.beecloud.bean.BCRefundQueryParameter;
import cn.beecloud.bean.TransferData;
import net.sf.json.JSONObject;

/**
 * This is the core class of BC payment for external invocation consist of start payment, start refund, start query bill
 * start refund query and check refund status functionality.
 * 
 * @author Ray
 * @since 2015/7/11
 */
public class BCPay {
	
	/**
	 * @param para {@link BCPayParameter}支付参数
	 * (必填)
	 * @return 调起比可支付后的返回结果
	 */
    public static BCPayResult startBCPay(BCPayParameter para) {
    	
    	BCPayResult result;
    	result = ValidationUtil.validateBCPay(para);
    	
    	if (result.getType().ordinal()!=0) {
    		return result;
    	}
    	
        Map<String, Object> param = new HashMap<String, Object>();
        
        buildPayParam(param, para);
        result = new BCPayResult();
        
        PAY_CHANNEL channel = para.getChannel();
        Client client = BCAPIClient.client;
        WebTarget target = client.target(BCUtilPrivate.getkApiPay());
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));
                if (isSuccess) {
                	result.setObjectId(ret.get("id").toString());
                	if (channel.equals(PAY_CHANNEL.WX_NATIVE)){
	                    if (ret.containsKey("code_url") && null != ret.get("code_url")) {
	                        result.setCodeUrl(ret.get("code_url").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    } 
                	} else if (channel.equals(PAY_CHANNEL.WX_JSAPI)) {
                    	result.setType(RESULT_TYPE.OK);
                    	result.setWxJSAPIMap(generateWXJSAPIMap(ret));
                    } else if (channel.equals(PAY_CHANNEL.ALI_WEB) || channel.equals(PAY_CHANNEL.ALI_QRCODE) || channel.equals(PAY_CHANNEL.ALI_WAP)) {
                		if (ret.containsKey("html") && null != ret.get("html") && 
                				ret.containsKey("url") && null != ret.get("url")) {
	                        result.setHtml(ret.get("html").toString());
	                        result.setUrl(ret.get("url").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    }
                	} else if (channel.equals(PAY_CHANNEL.UN_WEB) || channel.equals(PAY_CHANNEL.JD_WAP)
                			|| channel.equals(PAY_CHANNEL.JD_WEB) || channel.equals(PAY_CHANNEL.KUAIQIAN_WAP) 
                			|| channel.equals(PAY_CHANNEL.KUAIQIAN_WEB)) {
                		if (ret.containsKey("html") && null != ret.get("html")) {
	                        result.setHtml(ret.get("html").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    }
                	} else if (channel.equals(PAY_CHANNEL.YEE_WAP) || channel.equals(PAY_CHANNEL.YEE_WEB) || 
                			channel.equals(PAY_CHANNEL.BD_WEB) || 
                			channel.equals(PAY_CHANNEL.BD_WAP) ) {
                		if (ret.containsKey("url") && null != ret.get("url")) {
	                        result.setUrl(ret.get("url").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    }
                	} else if (channel.equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
                		result.setSucessMsg(ValidationUtil.PAY_SUCCESS);
                		result.setType(RESULT_TYPE.OK);
                	}
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }
    

	/**
	 * @param para {@link BCRefundParameter}退款参数
	 * @return 发起退款的返回结果
	 */
    public static BCPayResult startBCRefund(BCRefundParameter para) {
    	 
    	BCPayResult result;
    	result = ValidationUtil.validateBCRefund(para);
    	
    	if (result.getType().ordinal()!=0) {
    		return result;
    	}
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	buildRefundParam(para, param);
         
     	result = new BCPayResult();
     
     	Client client = BCAPIClient.client;

     	WebTarget target = client.target(BCUtilPrivate.getkApiRefund());
     	try {
             Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
             if (response.getStatus() == 200) {
                 Map<String, Object> ret = response.readEntity(Map.class);

                 boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                 .toStr(ret.get("result_code")).equals("0"));

                 if (isSuccess) {
                	 result.setObjectId(ret.get("id").toString());
             		if (ret.containsKey("url")) {
            			result.setUrl(ret.get("url").toString());
            		} 
             		result.setType(RESULT_TYPE.OK);
        			result.setSucessMsg(ValidationUtil.REFUND_SUCCESS);
                 } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                 	result.setErrDetail(ret.get("err_detail").toString());
                 	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                 }
             } else {
             	result.setErrMsg("Not correct response!");
             	result.setType(RESULT_TYPE.RUNTIME_ERROR);
             }
         } catch (Exception e) {
         	result.setErrMsg("Network error!");
         	result.setType(RESULT_TYPE.RUNTIME_ERROR);
         }
         return result;
    }
    
    /**
     * @param para {@link BCQueryParameter}订单总数查询参数
     * @return 订单总数查询返回的结果
     */
    public static BCQueryResult startQueryBillCount(BCQueryParameter para) {
    	
    	BCQueryResult result;
    	
    	result = ValidationUtil.validateQueryBill(para);
    	
    	if (result.getType().ordinal() != 0) {
    		return result;
    	}
    	 
    	Map<String, Object> param = new HashMap<String, Object>();
        buildQueryCountParam(param, para);
         
        result = new BCQueryResult();
    	
    	Client client = BCAPIClient.client;
    	  
    	StringBuilder sb = new StringBuilder();   
        sb.append(BCUtilPrivate.getkApiQueryBillCount());
        
        try {
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("count")
                                    && !StrUtil.empty(ret.get("count"))) {
                    	result.setTotalCount((Integer)ret.get("count"));
                    }
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	return result;
    }


	/**
     * @param para {@link BCQueryParameter}订单查询参数
     * @return 订单查询返回的结果
     */
    public static BCQueryResult startQueryBill(BCQueryParameter para) {
    	
    	BCQueryResult result;
    	
    	result = ValidationUtil.validateQueryBill(para);
    	
    	if (result.getType().ordinal() != 0) {
    		return result;
    	}
    	 
    	Map<String, Object> param = new HashMap<String, Object>();
        buildQueryParam(param, para);
         
        result = new BCQueryResult();
    	
    	Client client = BCAPIClient.client;
    	  
    	StringBuilder sb = new StringBuilder();   
        sb.append(BCUtilPrivate.getkApiQueryBill());
        
        try {
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("bills")
                                    && !StrUtil.empty(ret.get("bills"))) {
                        result.setBcOrders(generateBCOrderList((List<Map<String, Object>>)ret.get("bills")));
                    }
                    result.setTotalCount((Integer)ret.get("count"));
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	return result;
    }
    
    /**
     * Bill Query by Id.
     * @param objectId the id to query by.
     * @return BCQueryResult
     */
    public static BCQueryResult startQueryBillById(String objectId) {
    	
    	 BCQueryResult result;
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         
         result = new BCQueryResult();
    	
    	 Client client = BCAPIClient.client;
    	  
    	 StringBuilder sb = new StringBuilder();   
         sb.append(BCUtilPrivate.getkApiQueryBillById());
        
         try {
        	sb.append("/" + objectId);
        	sb.append("?para=");
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("pay")
                                    && ret.get("pay") != null) {
                        result.setOrder(generateBCOrder((Map<String, Object>)ret.get("pay")));
                    }
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    /**
     * @param para {@link BCRefundQueryParameter}退款总数查询参数
     * @return 退款总数查询返回的结果
     */
    public static BCQueryResult startQueryRefundCount(BCRefundQueryParameter para) {
    	
    	BCQueryResult result;
    	
    	result = ValidationUtil.validateQueryRefund(para);
    	
    	if (result.getType().ordinal() != 0) {
    		return result;
    	}
    	 
    	Map<String, Object> param = new HashMap<String, Object>();
        buildQueryCountParam(param, para);
        if (para.getRefundNo() != null) {
        	param.put("refund_no", para.getRefundNo());
        }
         
        result = new BCQueryResult();
    	
    	Client client = BCAPIClient.client;
    	  
    	StringBuilder sb = new StringBuilder();   
        sb.append(BCUtilPrivate.getkApiQueryRefundCount());
        
        try {
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("count")
                                    && !StrUtil.empty(ret.get("count"))) {
                    	result.setTotalCount((Integer)ret.get("count"));
                    }
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	return result;
    }
    
    
	/**
	 * @param para {@link BCRefundQueryParameter}}
	 * @return 退款查询返回的结果
	 */
    public static BCQueryResult startQueryRefund(BCRefundQueryParameter para) {
    	
    	BCQueryResult result;
    	result = ValidationUtil.validateQueryRefund(para);
		if (result.getType().ordinal() != 0) {
			return result;
		}
		
		result = new BCQueryResult();
		
		Map<String, Object> param = new HashMap<String, Object>();
        buildQueryParam(param, para);
        if (para.getRefundNo() != null) {
        	param.put("refund_no", para.getRefundNo());
        }
        
	    Client client = BCAPIClient.client;
     	
     	StringBuilder sb = new StringBuilder();
     	sb.append(BCUtilPrivate.getkApiQueryRefund());
         
        try {
             sb.append(URLEncoder.encode(
                             JSONObject.fromObject(param).toString(), "UTF-8"));

             WebTarget target = client.target(sb.toString());
             Response response = target.request().get();
             if (response.getStatus() == 200) {
                 Map<String, Object> ret = response.readEntity(Map.class);

                 boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                 .toStr(ret.get("result_code")).equals("0"));

                 if (isSuccess) {
                 	result.setType(RESULT_TYPE.OK);
                     if (ret.containsKey("refunds")
                                     && ret.get("refunds") != null) {
                         result.setBcRefundList(generateBCRefundList((List<Map<String, Object>>)ret.get("refunds")));
                     }
                     result.setTotalCount((Integer)ret.get("count"));
                 } else {
                 	result.setErrMsg(ret.get("result_msg").toString());
                 	result.setErrDetail(ret.get("err_detail").toString());
                 	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                 }
             } else {
             	result.setErrMsg("Not correct response!");
             	result.setErrDetail("Not correct response!");
             	result.setType(RESULT_TYPE.RUNTIME_ERROR);
             }
         } catch (Exception e) {
         	result.setErrMsg("Network error!");
         	result.setErrDetail(e.getMessage());
         	result.setType(RESULT_TYPE.RUNTIME_ERROR);
         }
     	
     	return result;
    }
    
    /**
     * Bill Query by Id.
     * @param objectId the id to query by.
     * @return BCQueryResult
     */
    public static BCQueryResult startQueryRefundById(String objectId) {
    	
    	 BCQueryResult result;
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         
         result = new BCQueryResult();
    	
    	 Client client = BCAPIClient.client;
    	  
    	 StringBuilder sb = new StringBuilder();   
         sb.append(BCUtilPrivate.getkApiQueryRefundById());
        
         try {
        	sb.append("/" + objectId);
        	sb.append("?para=");
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("refund")
                                    && ret.get("refund") != null) {
                        result.setRefund(generateBCRefund((Map<String, Object>)ret.get("refund")));
                    }
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    
    /**
     * @param refundNo
     * （必填）商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。	
     * @param channel
     * (必填) 渠道类型， 根据不同场景选择不同的支付方式，包含：
     *  YEE 易宝
	 *  WX 微信
	 *  KUAIQIAN 快钱
	 *  BD 百度
     * @return BCQueryStatusResult
     */
    public static BCQueryStatusResult startRefundUpdate(PAY_CHANNEL channel, String refundNo) {

    	BCQueryStatusResult result;
    	result = ValidationUtil.validateQueryRefundStatus(refundNo);
    	
		if (result.getType().ordinal() != 0) {
			return result;
		}
    	
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("refund_no", refundNo);
        
        result = new BCQueryStatusResult();
        StringBuilder sb = new StringBuilder();   
        sb.append(BCUtilPrivate.getkApiRefundUpdate());
        
        
        Client client = BCAPIClient.client;
        
        try {
        	sb.append(URLEncoder.encode(
                    JSONObject.fromObject(param).toString(), "UTF-8"));
        	WebTarget target = client.target(sb.toString());
		    Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setRefundStatus(ret.get("refund_status").toString());
                    result.setType(RESULT_TYPE.OK);
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    	
    }
    
    /**
     * @param channel
     * （必填）渠道类型， 暂时只支持ALI 
     * @param batchNo 
     * （必填） 批量付款批号， 此次批量付款的唯一标示，11-32位数字字母组合
     * @param accountName
     * （必填） 付款方的支付宝账户名, 支付宝账户名称,例如:毛毛
     * @param transferData
     * （必填） 付款的详细数据 {TransferData} 的 List集合。
     * @return BCPayResult
     */
    public static BCPayResult startTransfer(PAY_CHANNEL channel, String batchNo, String accountName, List<TransferData> transferData) {
    	BCPayResult result;
    	result = ValidationUtil.validateBCTransfer(channel, batchNo, accountName, transferData);
    	
    	if (result.getType().ordinal()!=0) {
    		return result;
    	}
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("app_id", BCCache.getAppID());
    	param.put("timestamp", System.currentTimeMillis());
    	param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
		param.put("channel", channel.toString());
    	param.put("batch_no", batchNo);
    	param.put("account_name", accountName);
    	List<Map<String, Object>> transferList = new ArrayList<Map<String, Object>>();
    	for (TransferData data : transferData) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("transfer_id", data.getTransferId());
    		map.put("receiver_account", data.getReceiverAccount());
    		map.put("receiver_name", data.getReceiverName());
    		map.put("transfer_fee", data.getTransferFee());
    		map.put("transfer_note", data.getTransferNote());
    		transferList.add(map);
    	}
    	param.put("transfer_data", transferList);
         
     	result = new BCPayResult();
     
     	Client client = BCAPIClient.client;

     	WebTarget target = client.target(BCUtilPrivate.getkApiTransfer());
     	try {
             Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
             if (response.getStatus() == 200) {
                 Map<String, Object> ret = response.readEntity(Map.class);

                 boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                 .toStr(ret.get("result_code")).equals("0"));

                 if (isSuccess) {
        			result.setUrl(ret.get("url").toString());
        			result.setType(RESULT_TYPE.OK);
          
                 } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                 	result.setErrDetail(ret.get("err_detail").toString());
                 	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                 }
             } else {
             	result.setErrMsg("Not correct response!");
             	result.setType(RESULT_TYPE.RUNTIME_ERROR);
             }
         } catch (Exception e) {
         	result.setErrMsg("Network error!");
         	result.setType(RESULT_TYPE.RUNTIME_ERROR);
         }
         return result;
    }
    
    /**
     * 发起明生网关支付
	 * @param para {@link BCMSWebPayParameter}支付参数
	 * (必填)
	 * @return 调起明生电商网关支付后的返回结果
	 */
    public static BCPayResult startBCMSWebPay(BCMSWebPayParameter para) {
    	
    	BCPayResult result;
    	
        Map<String, Object> param = new HashMap<String, Object>();
        
        buildMSWebPayParam(param, para);
        result = new BCPayResult();
        
        Client client = BCAPIClient.client;
        WebTarget target = client.target(BCUtilPrivate.getkApiMingShengPay());
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));
                if (isSuccess) {
                	result.setObjectId(ret.get("id").toString());
                	result.setHtml(ret.get("html").toString());
                	result.setType(RESULT_TYPE.OK);
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }
    
    /**
     * 发起明生快捷支付或鉴权
	 * @param para {@link BCMSWapPayParameter}支付参数
	 * (必填)
	 * @return 调起明生电商快捷：获取令牌或者发起支付
	 */
    public static BCMSWapPayResult startBCMSWapPay(BCMSWapPayParameter para) {
    	
    	BCMSWapPayResult result;
    	
        Map<String, Object> param = new HashMap<String, Object>();
        
        buildMSWapPayParam(param, para);
        
        param.put("cust_id", para.getCustId());
    	param.put("card_no", para.getCardNo());
    	param.put("cust_name", para.getCustName());
    	param.put("cust_id_no", para.getCustIdNo());
    	param.put("cust_id_type", para.getCustIdType());
    	param.put("bank_no", para.getBankNo());
    	param.put("phone_no", para.getPhoneNo());
    	if (para.getExpiredDate() != null) {
    		param.put("expired_date", para.getExpiredDate());
    	}
    	if (para.getCvv2() != null) {
    		param.put("cvv2", para.getCvv2());
    	}
    	if (para.getPhoneToken() != null) {
    		param.put("phone_token", para.getPhoneToken());
    	}
    	if (para.getPhoneVerCode() != null) {
    		param.put("phone_ver_code", para.getPhoneVerCode());
    	}
    	param.put("flag", para.getFlag());
    
        result = new BCMSWapPayResult();
        
        Client client = BCAPIClient.client;
        WebTarget target = client.target(BCUtilPrivate.getkApiMingShengPay());
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));
                if (ret.containsKey("client_date")) {
                	result.setMerTransDate(ret.get("client_date").toString());
                }
                if (isSuccess) {
            		if (ret.containsKey("phoneToken")) {
                		result.setPhoneToken(ret.get("phoneToken").toString());
                	} else {
                		result.setObjectId(ret.get("id").toString());
                		result.setSucessMsg("交易成功！");
                	}
                	result.setType(RESULT_TYPE.OK);
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }
    
    /**
     * 发起明生网关退款
	 * @param billNo 商户订单号, 8到30位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复	
	 * （必填）
	 * @param refundFee 退款金额， 必须是正整数，单位为分，最低100分
	 * （必填）
	 * @param refundNo 商户退款单号，格式为:退款日期(8位) + 流水号(3~24 位)。请自行确保在商户系统中唯一，且退款日期必须是发起退款的当天日期,同一退款单号不可重复提交，否则会造成退款单重复。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”
	 * （必填）
	 * @return BCMSRefundResult 发起退款的返回结果
	 */
    public static BCMSRefundResult startMSWebRefund(String billNo, Integer refundFee, String refundNo) {
    	 
    	BCMSRefundResult result;
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("app_id", BCCache.getAppID());
    	param.put("timestamp", System.currentTimeMillis());
    	param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
    	param.put("bill_no", billNo);
    	param.put("refund_fee", refundFee);
    	param.put("refund_no", refundNo);
    	
     	result = new BCMSRefundResult();
     
     	Client client = BCAPIClient.client;

     	WebTarget target = client.target(BCUtilPrivate.getkApiMingShengRefund());
     	try {
             Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
             if (response.getStatus() == 200) {
                 Map<String, Object> ret = response.readEntity(Map.class);

                 boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                 .toStr(ret.get("result_code")).equals("0"));

                 if (isSuccess) {
                	result.setObjectId(ret.get("id").toString());
             		result.setRefundId(ret.get("refundId").toString());
             		result.setType(RESULT_TYPE.OK);
        			result.setSucessMsg(ValidationUtil.REFUND_SUCCESS);
                 } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                 	result.setErrDetail(ret.get("err_detail").toString());
                 	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                 }
             } else {
             	result.setErrMsg("Not correct response!");
             	result.setType(RESULT_TYPE.RUNTIME_ERROR);
             }
         } catch (Exception e) {
         	result.setErrMsg("Network error!");
         	result.setType(RESULT_TYPE.RUNTIME_ERROR);
         }
         return result;
    }
    
    /**
     * 发起明生快捷之单笔订单查询
     * @param channelTradeNo 渠道交易号，就是快捷支付返回的refNo，由快捷支付时返回的
     * （必填）
     * @return BCMSWapQueryResult
     */
    public static BCMSWapQueryResult startQueryMSWapBillById(String billNo, String merTransDate) {
    	
    	 BCMSWapQueryResult result;
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         param.put("bill_no", billNo);
         param.put("mer_trans_date", merTransDate);
         
         result = new BCMSWapQueryResult();
    	
    	 Client client = BCAPIClient.client;
    	  
    	 StringBuilder sb = new StringBuilder();   
         sb.append(BCUtilPrivate.getkApiQueryMingShengWapBillById());
        
         try {
        	sb.append("?para=");
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                    result.setAmount(ret.get("amount").toString());
                    result.setTxnStat(ret.get("txnStat").toString());
                    result.setTxnType(ret.get("txnType").toString());
                    result.setMerTransTime(ret.get("merTransTime").toString());
                    result.setMerOrderId(ret.get("merOrderId").toString());
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    /**
     * 发起明生网关之单笔订单查询
     * @param billNo 商户订单号，8到30位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
     * （必填）
     * @return BCMSWebQueryResult
     */
    public static BCMSWebQueryResult startQueryMSWebBillById(String billNo) {
    	
    	BCMSWebQueryResult result;
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         param.put("bill_no", billNo);
         
         result = new BCMSWebQueryResult();
    	
    	 Client client = BCAPIClient.client;
    	  
    	 StringBuilder sb = new StringBuilder();   
         sb.append(BCUtilPrivate.getkApiQueryMingShengWebBillById());
        
         try {
        	sb.append("?para=");
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                	if (!ret.get("recordLength").equals("0")) {
                		result.setMsBean(new BCMSWebOrderBean());
	                    result.getMsBean().setAmountSum(ret.get("amountSum").toString());
	                    result.getMsBean().setPayOrderId(ret.get("payOrderId").toString());
	                    result.getMsBean().setMerOrderId(ret.get("merOrderId").toString());
	                    result.getMsBean().setMerSendTime(ret.get("merSendTime").toString());
	                    result.getMsBean().setPayBank(ret.get("payBank").toString());
	                    result.getMsBean().setType(ret.get("type").toString());
	                    result.getMsBean().setState(ret.get("state").toString());
                	}
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    /**
     * 发起明生网关之批量订单查询
     * @param payBank 支付银行，要查询的银行支付渠道；无值则查所有银行，一下是支持的银行
     * CCB	中国建设银行B2C支付渠道
	 * ABC	中国农业银行B2C支付渠道
	 * ICBC	中国工商银行B2C支付渠道
	 * BOC	交通银行B2C支付渠道
	 * GDB	广东发展银行B2C支付渠道
	 * CMB	招商银行B2C支付渠道
	 * CMSB	中国民生银行B2C支付渠道
	 * SPDB	上海浦东发展银行B2C支付渠道
	 * HXB	华夏银行B2C支付渠道
	 * FUDIAN	富滇银行B2C支付渠道
	 * POST	中国邮政B2C支付渠道
	 * BCN	中国银行
	 * CITIC  中信银行B2C支付渠道（与银行对接中）
	 * SZDB	 深圳发展银行B2C支付渠道
	 * CIB	兴业银行B2C支付渠道
	 * CEB	光大银行B2C支付渠道
	 * B2B_CCB	中国建设银行B2B支付渠道
	 * B2B_ABC	中国农业银行B2B支付渠道
	 * B2B_ICBC	中国工商银行B2B支付渠道
	 * B2B_ZSYH	招商银行B2B支付渠道（与银行对接中）
	 * B2B_SPDB	浦发银行B2B支付渠道（与银行对接中）
	 * MEM	所有会员支付
	 * B2B	所有b2b支付
	 * B2C	所有B2C支付
	 * （选填）
     * @param startDate 开始时间， Date类型
     * （必填）
     * @param endDate 结束时间， Date类型
     * （必填）
     * @return BCMSWebQueryResult
     */
    public static BCMSWebQueryResult startQueryMSWebBill(String payBank, Date startDate, Date endDate) {
    	
    	BCMSWebQueryResult result;
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         if (payBank != null) {
        	 param.put("pay_bank", payBank);
         }
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         param.put("start_time", sdf.format(startDate));
         param.put("end_time", sdf.format(endDate));
         
         result = new BCMSWebQueryResult();
    	
    	 Client client = BCAPIClient.client;
    	  
    	 StringBuilder sb = new StringBuilder();   
         sb.append(BCUtilPrivate.getkApiQueryMingShengWebBill());
        
         try {
        	sb.append("?para=");
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                	result.setCount((Integer)ret.get("count"));
                	if (result.getCount() != 0) {
	                	for (Map<String, Object> map :  (List<Map<String, Object>>)ret.get("bills")){
	                		BCMSWebOrderBean bean = new BCMSWebOrderBean();
	                		bean.setAmountSum(map.get("amountSum").toString());
	                		bean.setPayOrderId(map.get("payOrderId").toString());
	                		bean.setMerOrderId(map.get("merOrderId").toString());
	                		bean.setMerSendTime(map.get("merSendTime").toString());
	                		bean.setPayBank(map.get("payBank").toString());
	                		bean.setType(map.get("type").toString());
	                		bean.setState(map.get("state").toString());
	                		result.getMsBeanList().add(bean);
	                	}
                	}
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    /**
     * 发起明生网关之批量退款查询
     * @param startDate 开始日期 Date类型
     * (必填)
     * @param endDate 结束日期 Date类型
     * （必填）
     * @return BCMSWebQueryResult
     */
    public static BCMSWebQueryResult startQueryMSWebRefund(Date startDate, Date endDate) {
    	
    	BCMSWebQueryResult result;
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         param.put("begin_date", sdf.format(startDate));
         param.put("end_date", sdf.format(endDate));
         
         result = new BCMSWebQueryResult();
    	
    	 Client client = BCAPIClient.client;
    	  
    	 StringBuilder sb = new StringBuilder();   
         sb.append(BCUtilPrivate.getkApiQueryMingShengWebRefund());
        
         try {
        	sb.append("?para=");
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setType(RESULT_TYPE.OK);
                	result.setCount((Integer)ret.get("count"));
                	if (result.getCount() != 0) {
	                	for (Map<String, Object> map :  (List<Map<String, Object>>)ret.get("refunds")){
	                		BCMSWebRefundBean bean = new BCMSWebRefundBean();
	                		bean.setAmount(map.get("amount").toString());
	                		bean.setApplyDate(map.get("applyDate").toString());
	                		bean.setStartFlag(map.get("startflag").toString());
	                		bean.setMerchantId(map.get("merchantId").toString());
	                		bean.setMerOrderId(map.get("merOrderId").toString());
	                		bean.setPayorderId(map.get("payorderId").toString());
	                		bean.setState(map.get("state").toString());
	                		bean.setRefundId(map.get("refundId").toString());
	                		bean.setRefundAmount(map.get("refundAmount").toString());
	                		result.getMsRefundBeanList().add(bean);
	                	}
                	}
                    
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    /**
     * 明生网关之单笔退款更新
     * @param channelRefundNo 平台退款流水号, 平台退款流水号,在民生电商系统中唯一，用于单笔退款查询,从退款处获得
     * （必填）
     * @return BCQueryStatusResult
     */
    public static BCQueryStatusResult startMSWebRefundUpdate(String channelRefundNo) {

    	BCQueryStatusResult result;
    	
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel_refund_no", channelRefundNo);
        
        result = new BCQueryStatusResult();
        StringBuilder sb = new StringBuilder();   
        sb.append(BCUtilPrivate.getkApiMingShengWebRefundUpdate());
        
        
        Client client = BCAPIClient.client;
        
        try {
        	sb.append("?para=");
        	sb.append(URLEncoder.encode(
                    JSONObject.fromObject(param).toString(), "UTF-8"));
        	WebTarget target = client.target(sb.toString());
		    Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                	result.setRefundStatus(ret.get("refund_status").toString());
                    result.setType(RESULT_TYPE.OK);
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErrDetail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErrDetail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErrDetail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    	
    }
    
    
    /**
     * @param sign
     *            Webhook提供的签名
     * @param timestamp
     *            Webhook提供的timestamp，注意是String格式
     * @return 签名是否正确
     */
    public static boolean verifySign(String sign, String timestamp) {
        String mySign = MD5.sign(BCCache.getAppID() + BCCache.getAppSecret(),
                        timestamp, "UTF-8");
        
        if (sign.equals(mySign))
            return true;
        else
            return false;
    }
    
    /**
     * Build Payment parameters
     * @param param to be built
     * @param para used for building 
     */
    private static void buildPayParam(Map<String, Object> param,
			BCPayParameter para) {
    	
    	param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", para.getChannel().toString());
        param.put("total_fee", para.getTotalFee());
        param.put("bill_no", para.getBillNo());
        param.put("title", para.getTitle());
        
		if (para.getReturnUrl() != null) {
			param.put("return_url", para.getReturnUrl());
		} 
		if (para.getOptional() != null && para.getOptional().size() > 0) {
			param.put("optional", para.getOptional());
		}
		if (para.getOpenId() != null) {
			param.put("openid", para.getOpenId());
		}
		if (para.getShowUrl() != null) {
			param.put("show_url", para.getShowUrl());
		}
		if (para.getQrPayMode() != null) {
			if (para.getQrPayMode().ordinal() == 2) {
        		param.put("qr_pay_mode", String.valueOf(para.getQrPayMode().ordinal() +1));
        	} else {
        		param.put("qr_pay_mode", String.valueOf(para.getQrPayMode().ordinal()));
        	}
		}
		if (para.getBillTimeout() != null) {
        	param.put("bill_timeout", para.getBillTimeout());
        }
        if (para.getChannel().equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
        	param.put("cardno", para.getCardNo());
        	param.put("cardpwd", para.getCardPwd());
        	param.put("frqid", para.getFrqid());
        } 
	}
    
    /**
     * Build Refund parameters
     * @param param to be built
     * @param para used for building 
     */
    private static void buildRefundParam(BCRefundParameter para,
			Map<String, Object> param) {
		
		param.put("app_id", BCCache.getAppID());
    	param.put("timestamp", System.currentTimeMillis());
    	param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
    	param.put("refund_no", para.getRefundNo());
    	param.put("bill_no", para.getBillNo());
    	param.put("refund_fee", para.getRefundFee());
    	
		if (para.getChannel() != null) {
    		param.put("channel", para.getChannel().toString());
    	}
    	if (para.getNeedApproval() != null) {
    		param.put("need_approval", para.getNeedApproval());
    	}
    	if (para.getOptional() != null && para.getOptional().size() > 0)
    		param.put("optional", para.getOptional());
	}
    
    /**
     * Build Query parameters
     * @param param to be built
     * @param para used for building 
     */
	private static void buildQueryParam(Map<String, Object> param,
			BCQueryParameter para) {
		param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        if (para.getChannel() != null) {
    		param.put("channel", para.getChannel().toString());
    	}
        if (para.getBillNo() != null) {
        	param.put("bill_no", para.getBillNo());
        }
        if (para.getSkip() != null) {
        	param.put("skip", para.getSkip());
        }
        if (para.getLimit() != null) {
        	param.put("limit", para.getLimit());
        }
        
        if (para.getStartTime() != null) {
        	param.put("start_time", para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
       	 param.put("end_time", para.getEndTime().getTime());
        }
        if (para.getNeedDetail() != null && para.getNeedDetail()) {
        	param.put("need_detail", true);
        }
	}
    
	/**
     * Build Query Count parameters
     * @param param to be built
     * @param para used for building 
     */
	private static void buildQueryCountParam(Map<String, Object> param,
			BCQueryParameter para) {
    	param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        if (para.getChannel() != null) {
    		param.put("channel", para.getChannel().toString());
    	}
        if (para.getBillNo() != null) {
        	param.put("bill_no", para.getBillNo());
        }
        if (para.getStartTime() != null) {
        	param.put("start_time", para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
       	 param.put("end_time", para.getEndTime().getTime());
        }
	}
	
    /**
     * The method is used to generate Order list by query.
     * @param bills
     * @return list of BCOrderBean
     */
    private static List<BCOrderBean> generateBCOrderList(List<Map<String, Object>> bills) {
			
		List<BCOrderBean> bcOrderList = new ArrayList<BCOrderBean>();
		for (Map bill : bills){
			BCOrderBean bcOrder = new BCOrderBean();
			bcOrder.setBillNo(bill.get("bill_no").toString());
			bcOrder.setTotalFee(bill.get("total_fee").toString());
			bcOrder.setTitle(bill.get("title").toString());
			bcOrder.setChannel(bill.get("channel").toString());
//			bcOrder.setCreatedTime((Long)bill.get("create_time"));
			bcOrder.setCreatedTime((Long)bill.get("created_time"));
			bcOrder.setSpayResult(((Boolean)bill.get("spay_result")));
//			bcOrder.setSubChannel((bill.get("sub_channel").toString()));
//			if (bill.containsKey("trade_no") && bill.get("trade_no") != null) {
//				bcOrder.setChannelTradeNo(bill.get("trade_no").toString());
//			}
//			bcOrder.setOptional(bill.get("optional").toString());
//			bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)bill.get("create_time")));
			bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)bill.get("created_time")));
//			if (bill.containsKey("message_detail")) {
//				bcOrder.setMessageDetail(bill.get("message_detail").toString());
//			}
//			bcOrder.setRefundResult((Boolean)bill.get("refund_result"));
			bcOrderList.add(bcOrder);
		}
		return bcOrderList;
	}
    
    /**
     * The method is used to generate Refund list by query.
     * @param refundList
     * @return list of refund
     */
    private static List<BCRefundBean> generateBCRefundList(List<Map<String, Object>> refundList) {
    	
    	List<BCRefundBean> bcRefundList = new ArrayList<BCRefundBean>();
		for (Map refund : refundList){
			BCRefundBean bcRefund = new BCRefundBean();
			bcRefund.setBillNo(refund.get("bill_no").toString());
	    	bcRefund.setChannel(refund.get("channel").toString());
//	    	bcRefund.setSubChannel(refund.get("sub_channel").toString());
	    	bcRefund.setFinished((Boolean)refund.get("finish"));
//	    	bcRefund.setCreatedTime((Long)refund.get("create_time"));
	    	bcRefund.setCreatedTime((Long)refund.get("created_time"));
//	    	bcRefund.setOptional(refund.get("optional").toString());
	    	bcRefund.setRefunded((Boolean)refund.get("result"));
	    	bcRefund.setTitle(refund.get("title").toString());
	    	bcRefund.setTotalFee(refund.get("total_fee").toString());
	    	bcRefund.setRefundFee(refund.get("refund_fee").toString());
	    	bcRefund.setRefundNo(refund.get("refund_no").toString());
//	    	bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)refund.get("create_time")));
	    	bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)refund.get("created_time")));
//			if (refund.containsKey("message_detail")) {
//				bcRefund.setMessageDetail(refund.get("message_detail").toString());
//			}
	    	bcRefundList.add(bcRefund);
		}
		return bcRefundList;
    }
    
    /**
     * Generate a map for JSAPI payment to receive.
     * @param ret
     * @return
     */
    private static Map<String, Object> generateWXJSAPIMap(
			Map<String, Object> ret) {
		HashMap map = new HashMap<String, Object>();
		map.put("appId", ret.get("app_id"));
		map.put("package", ret.get("package"));
		map.put("nonceStr", ret.get("nonce_str"));
		map.put("timeStamp", ret.get("timestamp"));
		map.put("paySign", ret.get("pay_sign"));
		map.put("signType", ret.get("sign_type"));
		
		return map;
	}

    private static BCOrderBean generateBCOrder(Map<String, Object> bill) {
			BCOrderBean bcOrder = new BCOrderBean();
			bcOrder.setBillNo(bill.get("bill_no").toString());
			bcOrder.setTotalFee(bill.get("total_fee").toString());
			bcOrder.setTitle(bill.get("title").toString());
			bcOrder.setChannel(bill.get("channel").toString());
			bcOrder.setSpayResult(((Boolean)bill.get("spay_result")));
			bcOrder.setSubChannel((bill.get("sub_channel").toString()));
			bcOrder.setCreatedTime((Long)bill.get("create_time"));
			if (bill.containsKey("trade_no") && bill.get("trade_no") != null) {
				bcOrder.setChannelTradeNo(bill.get("trade_no").toString());
			}
			bcOrder.setOptional(bill.get("optional").toString());
			bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)bill.get("create_time")));
			bcOrder.setMessageDetail(bill.get("message_detail").toString());
			bcOrder.setRefundResult((Boolean)bill.get("refund_result"));
			return bcOrder;
	}
    
    private static BCRefundBean generateBCRefund(Map<String, Object> refund) {
    	BCRefundBean bcRefund = new BCRefundBean();
    	bcRefund.setBillNo(refund.get("bill_no").toString());
    	bcRefund.setChannel(refund.get("channel").toString());
    	bcRefund.setSubChannel(refund.get("sub_channel").toString());
    	bcRefund.setFinished((Boolean)refund.get("finish"));
    	bcRefund.setCreatedTime((Long)refund.get("create_time"));
    	bcRefund.setOptional(refund.get("optional").toString());
    	bcRefund.setRefunded((Boolean)refund.get("result"));
    	bcRefund.setTitle(refund.get("title").toString());
    	bcRefund.setTotalFee(refund.get("total_fee").toString());
    	bcRefund.setRefundFee(refund.get("refund_fee").toString());
    	bcRefund.setRefundNo(refund.get("refund_no").toString());
    	bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)refund.get("create_time")));
    	bcRefund.setMessageDetail(refund.get("message_detail").toString());
    	return bcRefund;
    }
    
    /**
     * Build Payment parameters
     * @param param to be built
     * @param para used for building 
     */
    private static void buildMSWebPayParam(Map<String, Object> param,
			BCMSWebPayParameter para) {
    	
    	param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", para.getChannel().toString());
        param.put("total_fee", para.getTotalFee());
        param.put("bill_no", para.getBillNo());
        param.put("title", para.getTitle());
		param.put("return_url", para.getReturnUrl());
		param.put("subject", para.getSubject());
		if (para.getOptional() != null && para.getOptional().size() > 0) {
			param.put("optional", para.getOptional());
		}
	}
    
    /**
     * Build Payment parameters
     * @param param to be built
     * @param para used for building 
     */
    private static void buildMSWapPayParam(Map<String, Object> param,
			BCMSWapPayParameter para) {
    	
    	param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", para.getChannel().toString());
        param.put("total_fee", para.getTotalFee());
        param.put("bill_no", para.getBillNo());
        param.put("title", para.getTitle());
		param.put("subject", para.getSubject());
		if (para.getOptional() != null && para.getOptional().size() > 0) {
			param.put("optional", para.getOptional());
		}
	}
}

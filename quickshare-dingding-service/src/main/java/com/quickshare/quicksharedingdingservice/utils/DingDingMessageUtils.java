package com.quickshare.quicksharedingdingservice.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.constant.DingDingApi;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.util.TextUtils;

/**
 * @Author: Jiang
 * @Description: 钉钉消息工具类
 * @Date: 2020-08-05 16:33
 * @Version: 1.0
 * @Update:
 */
public class DingDingMessageUtils {
    /**
     * 发送消息封装
     *
     * @param methodName        请求方法名称
     * @param dingAppProperties 钉钉配置信息
     * @param phone             手机号
     * @param title             标题
     * @param singleTitle       子标题
     * @param markDownStr       基地址
     * @param url               链接地址
     * @param toAllUser         是否发给所有人
     * @return
     * @throws Exception 异常信息
     */
    public static OapiMessageCorpconversationGetsendresultResponse sendNormalDingDingMessage(String methodName, DingDingAppProperties dingAppProperties,
                                                                                             String phone, String title, String singleTitle, String url,
                                                                                             String markDownStr, boolean toAllUser) throws Exception {
        //换取token
        OapiGettokenResponse response;
        try {
            response = DingDingRequestUtils.getOapiGettokenResponse(dingAppProperties.getAppKey(), dingAppProperties.getAppsecret(), dingAppProperties.getBaseUrl());
            CommonUtil.writeNormalInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取token：" + JsonUtils.toJson(response));
        } catch (Exception e) {
            CommonUtil.writeErrorInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取token：" + ExceptionUtils.getStackTrace(e));
            throw new Exception(methodName + "接口，" + "换取token异常:" +e.toString());
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
        }

        //换取用户手机号
        OapiUserGetByMobileResponse executePhone;
        try {
            executePhone = DingDingRequestUtils.getOapiUserGetByMobileResponse(phone, response.getAccessToken(), dingAppProperties.getBaseUrl());
            CommonUtil.writeNormalInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取手机号结果：" + JsonUtils.toJson(executePhone));
        } catch (Exception e) {
            CommonUtil.writeErrorInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取手机号结果：" + ExceptionUtils.getStackTrace(e));
            throw new Exception(methodName + "接口，" + "换取用户手机号异常:" +e.toString());
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
        }

//                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";
//        System.out.println(response.getAccessToken());
        //发送消息
        OapiMessageCorpconversationAsyncsendV2Response responseSend;
        try {
            responseSend = DingDingRequestUtils.sendActionCardMessage("123321", dingAppProperties.getAgentId(), toAllUser,
                    title, markDownStr, singleTitle, url,
                    Constant.DingDinngMessageType.ActionCard, response.getAccessToken(), dingAppProperties.getBaseUrl());
            CommonUtil.writeNormalInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",发送消息结果：" + JsonUtils.toJson(responseSend));
        } catch (Exception e) {
            CommonUtil.writeErrorInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",发送消息结果：" + ExceptionUtils.getStackTrace(e));
            throw new Exception(methodName + "接口，" + "发送消息异常:" + e.toString());
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
        }

        //查询发送消息的状态
        OapiMessageCorpconversationGetsendresultResponse oapiMessageCorpconversationGetsendresultResponse = null;
        if (responseSend.getErrcode() == Constant.Response.ok) {
            try {
                oapiMessageCorpconversationGetsendresultResponse = DingDingRequestUtils.getOapiSendResuktResponse(responseSend.getTaskId(),
                        dingAppProperties.getAgentId(), response.getAccessToken(), dingAppProperties.getBaseUrl());
                CommonUtil.writeNormalInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + "," +
                        responseSend.getTaskId() + "查询发送消息结果：" + JsonUtils.toJson(responseSend));
            } catch (Exception e) {
                CommonUtil.writeErrorInfo(methodName + "接口，" + "查询发送消息消息结果异常:" + ExceptionUtils.getStackTrace(e));
                throw new Exception(e.toString());
//                booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//                return booleanReturnBean;
            }
        }

        return oapiMessageCorpconversationGetsendresultResponse;
    }
}
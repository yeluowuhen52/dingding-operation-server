package com.quickshare.quicksharedingdingservice.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.constant.DingDingApi;
import com.taobao.api.TaobaoResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.util.TextUtils;

import static com.quickshare.quicksharedingdingservice.utils.CommonUtil.isErrorResponse;

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
        //2021年5月24号的accessToken
        dingAppProperties.setAccessToken("1a13137dbb8c3835be5e737ce7fa72b0");

        if (TextUtils.isEmpty(dingAppProperties.getAccessToken())) {
//            response = DingDingRequestUtils.getOapiGettokenResponse(dingAppProperties.getAppKey(), dingAppProperties.getAppsecret(), dingAppProperties.getBaseUrl());
            getAccessToken(dingAppProperties, methodName, dingAppProperties.getAppKey(), dingAppProperties.getAppsecret(), dingAppProperties.getBaseUrl(), true);
        }

        //换取用户手机号
        OapiUserGetByMobileResponse executePhone;
//        try {
        executePhone = getPhoneInfo(dingAppProperties, methodName, phone);
//        if (executePhone.getErrcode().equals(Constant.ErrorCode.tokenError1) || executePhone.getErrcode().equals(Constant.ErrorCode.tokenError2) ||
//                executePhone.getErrcode().equals(Constant.ErrorCode.tokenError3)) {
        if (isErrorResponse(executePhone)) {
//            }
            getAccessToken(dingAppProperties, methodName, dingAppProperties.getAppKey(), dingAppProperties.getAppsecret(), dingAppProperties.getBaseUrl(), true);
            executePhone = getPhoneInfo(dingAppProperties, methodName, phone);
        }
//        } catch (Exception e) {
//            CommonUtil.writeErrorInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取手机号结果：" + ExceptionUtils.getStackTrace(e));
//            throw new Exception(methodName + "接口，" + "换取用户手机号异常:" + e.toString());
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
//        }

//                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";
//        System.out.println(response.getAccessToken());
        //发送消息
        OapiMessageCorpconversationAsyncsendV2Response responseSend;
//        try {
        responseSend = sendMessgae(dingAppProperties, executePhone.getUserid(), toAllUser, methodName, title, markDownStr, singleTitle, url);
//            responseSend = DingDingRequestUtils.sendActionCardMessage(executePhone.getUserid(), dingAppProperties.getAgentId(), toAllUser,
//                    title, markDownStr, singleTitle, url,
//                    Constant.DingDinngMessageType.ActionCard, dingAppProperties.getAccessToken(), dingAppProperties.getBaseUrl());
//        CommonUtil.writeNormalInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",发送消息结果：" + JsonUtils.toJson(responseSend));
//        if (executePhone.getErrcode().equals(Constant.ErrorCode.tokenError1) || executePhone.getErrcode().equals(Constant.ErrorCode.tokenError2) ||
//                executePhone.getErrcode().equals(Constant.ErrorCode.tokenError3)) {
        if (isErrorResponse(executePhone)) {
            getAccessToken(dingAppProperties, methodName, dingAppProperties.getAppKey(), dingAppProperties.getAppsecret(), dingAppProperties.getBaseUrl(), true);
            responseSend = sendMessgae(dingAppProperties, executePhone.getUserid(), toAllUser, methodName, title, markDownStr, singleTitle, url);
        }
//        } catch (Exception e) {
//            CommonUtil.writeErrorInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",发送消息结果：" + ExceptionUtils.getStackTrace(e));
//            throw new Exception(methodName + "接口，" + "发送消息异常:" + e.toString());
////            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
////            return booleanReturnBean;
//        }

        //查询发送消息的状态
        OapiMessageCorpconversationGetsendresultResponse oapiMessageCorpconversationGetsendresultResponse;
//        if (responseSend.getErrcode() == Constant.Response.ok) {
//            try {
        oapiMessageCorpconversationGetsendresultResponse = getSendMessgaeState(dingAppProperties, methodName, responseSend);
//                oapiMessageCorpconversationGetsendresultResponse = DingDingRequestUtils.getOapiSendResuktResponse(responseSend.getTaskId(),
//                        dingAppProperties.getAgentId(), dingAppProperties.getAccessToken(), dingAppProperties.getBaseUrl());
//        CommonUtil.writeNormalInfo(methodName + "接口，" + "钉钉信息：" + JsonUtils.toJson(dingAppProperties) + "," +
//                responseSend.getTaskId() + "查询发送消息结果：" + JsonUtils.toJson(responseSend));
//        if (executePhone.getErrcode().equals(Constant.ErrorCode.tokenError1) || executePhone.getErrcode().equals(Constant.ErrorCode.tokenError2) ||
//                executePhone.getErrcode().equals(Constant.ErrorCode.tokenError3)) {
        if (isErrorResponse(oapiMessageCorpconversationGetsendresultResponse)) {
            getAccessToken(dingAppProperties, methodName, dingAppProperties.getAppKey(), dingAppProperties.getAppsecret(), dingAppProperties.getBaseUrl(), true);
            oapiMessageCorpconversationGetsendresultResponse = getSendMessgaeState(dingAppProperties, methodName, responseSend);
        }

//            } catch (Exception e) {
//                CommonUtil.writeErrorInfo(methodName + "接口，" + "查询发送消息消息结果异常:" + ExceptionUtils.getStackTrace(e));
//                throw new Exception(e.toString());
////                booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
////                return booleanReturnBean;
//            }
//        }
        return oapiMessageCorpconversationGetsendresultResponse;
    }

    /**
     * 获取accessToken
     *
     * @param dingAppProperties
     * @param methodName
     * @param appKey
     * @param appSecret
     * @param baseUrl
     * @throws Exception
     */
    public static void getAccessToken(DingDingAppProperties dingAppProperties, String methodName, String appKey, String appSecret, String baseUrl, boolean isRepeat) throws Exception {
        //换取token
        OapiGettokenResponse response = null;
        try {
            response = DingDingRequestUtils.getOapiGettokenResponse(appKey, appSecret, baseUrl);
            CommonUtil.writeNormalInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取token：" + JsonUtils.toJson(response));
            dingAppProperties.setAccessToken(response.getAccessToken());
        } catch (Exception e) {
            CommonUtil.writeErrorInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取token：" + ExceptionUtils.getStackTrace(e));
            if (isRepeat) {
                getAccessToken(dingAppProperties, methodName, appKey, appSecret, baseUrl, false);
                CommonUtil.writeNormalInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取token失败：" + JsonUtils.toJson(response) + "正在尝试重新获取!");
            } else {
                throw new Exception(methodName + "接口，换取token异常:" + e.toString());
            }
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
        }
    }

    /**
     * 获取手机号关联信息
     *
     * @param methodName        请求方法名称
     * @param dingAppProperties 钉钉配置信息
     * @param phone             手机号
     * @throws Exception
     */
    public static OapiUserGetByMobileResponse getPhoneInfo(DingDingAppProperties dingAppProperties, String methodName, String phone) throws Exception {
        OapiUserGetByMobileResponse executePhone;
        try {
            executePhone = DingDingRequestUtils.getOapiUserGetByMobileResponse(phone, dingAppProperties.getAccessToken(), dingAppProperties.getBaseUrl());
            CommonUtil.writeNormalInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取手机号结果：" + JsonUtils.toJson(executePhone));

        } catch (Exception e) {
            CommonUtil.writeErrorInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",换取手机号结果：" + ExceptionUtils.getStackTrace(e));
            throw new Exception(methodName + "接口，换取用户手机号异常:" + e.toString());
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
        }
        return executePhone;
    }

    /**
     * 发送消息
     *
     * @param methodName        请求方法名称
     * @param dingAppProperties 钉钉配置信息
     * @param title             标题
     * @param singleTitle       子标题
     * @param markDownStr       基地址
     * @param url               链接地址
     * @param toAllUser         是否发给所有人
     * @throws Exception
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendMessgae(DingDingAppProperties dingAppProperties, String userId, boolean toAllUser,
                                                                             String methodName, String title, String markDownStr, String singleTitle, String url) throws Exception {
        OapiMessageCorpconversationAsyncsendV2Response responseSend;
        try {
            responseSend = DingDingRequestUtils.sendActionCardMessage(userId, dingAppProperties.getAgentId(), toAllUser,
                    title, markDownStr, singleTitle, url,
                    Constant.DingDinngMessageType.ActionCard, dingAppProperties.getAccessToken(), dingAppProperties.getBaseUrl());
            CommonUtil.writeNormalInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",发送消息结果：" + JsonUtils.toJson(responseSend));
        } catch (Exception e) {
            CommonUtil.writeErrorInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + ",发送消息结果：" + ExceptionUtils.getStackTrace(e));
            throw new Exception(methodName + "接口，发送消息异常:" + e.toString());
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
        }
        return responseSend;
    }

    /**
     * 查询发送消息状态
     *
     * @param methodName        请求方法名称
     * @param dingAppProperties 钉钉配置信息
     * @throws Exception
     */
    public static OapiMessageCorpconversationGetsendresultResponse getSendMessgaeState(DingDingAppProperties dingAppProperties, String methodName,
                                                                                       OapiMessageCorpconversationAsyncsendV2Response responseSend) throws Exception {
        //查询发送消息的状态
        OapiMessageCorpconversationGetsendresultResponse oapiMessageCorpconversationGetsendresultResponse = null;
        if (responseSend.getErrcode() == Constant.Response.ok) {
            try {
                oapiMessageCorpconversationGetsendresultResponse = DingDingRequestUtils.getOapiSendResuktResponse(responseSend.getTaskId(),
                        dingAppProperties.getAgentId(), dingAppProperties.getAccessToken(), dingAppProperties.getBaseUrl());
                CommonUtil.writeNormalInfo(methodName + "接口，钉钉信息：" + JsonUtils.toJson(dingAppProperties) + "," +
                        responseSend.getTaskId() + "查询发送消息结果：" + JsonUtils.toJson(responseSend));
            } catch (Exception e) {
                CommonUtil.writeErrorInfo(methodName + "接口，查询发送消息消息结果异常:" + ExceptionUtils.getStackTrace(e));
                throw new Exception(e.toString());
//                booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//                return booleanReturnBean;
            }
        }
        return oapiMessageCorpconversationGetsendresultResponse;
    }
}
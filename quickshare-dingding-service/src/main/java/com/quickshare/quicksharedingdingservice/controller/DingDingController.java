package com.quickshare.quicksharedingdingservice.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.quickshare.quicksharedingdingservice.beans.BooleanReturnBean;
import com.quickshare.quicksharedingdingservice.config.DingDingProperties;
import com.quickshare.quicksharedingdingservice.utils.CommonUtil;
import com.quickshare.quicksharedingdingservice.utils.Constant;
import com.quickshare.quicksharedingdingservice.utils.DingDingApiUrl;
import com.quickshare.quicksharedingdingservice.utils.DingDingRequestUtils;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Jiang
 * @Description: 钉钉消息推送
 * @Date: 2020-08-04 16:02
 * @Version: 1.0
 * @Update:
 */
@RestController
public class DingDingController {
    @Autowired
    DingDingProperties dingDingProperties;

    @GetMapping("/sendDingDingMessage")
    public BooleanReturnBean sendDingDingMessage() {
        BooleanReturnBean booleanReturnBean = null;

        String appKey = dingDingProperties.getAppConfigs().get(0).getAppKey();
        String appSecret = dingDingProperties.getAppConfigs().get(0).getAppsecret();
        String baseUrl = dingDingProperties.getAppConfigs().get(0).getBaseUrl();
        Long agentId = dingDingProperties.getAppConfigs().get(0).getAgentid();

        OapiGettokenResponse response = null;
        try {
            response = DingDingRequestUtils.getOapiGettokenResponse(appKey, appSecret, baseUrl, DingDingApiUrl.Agent.getToken);
        } catch (Exception e) {
            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
        }

        if (booleanReturnBean != null) {
            return booleanReturnBean;
        }

        OapiUserGetByMobileResponse execute111 = null;
        try {
            execute111 = DingDingRequestUtils.getOapiUserGetByMobileResponse("15212273352", response.getAccessToken(), baseUrl, DingDingApiUrl.User.getByMobile);
        } catch (Exception e) {
            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
        }

        if (booleanReturnBean != null) {
            return booleanReturnBean;
        }

        String markDownStr = "### 消息通知" + System.currentTimeMillis()
                + "\n" + "## 测试123111"
                + "\n" + "## 测试123111";
//                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";
        System.out.println(response.getAccessToken());
        OapiMessageCorpconversationAsyncsendV2Response responseSend = null;
        try {
            responseSend = DingDingRequestUtils.sendActionCardMessage(execute111.getUserid(), agentId, false,
                    "xxx123411111" + System.currentTimeMillis(), markDownStr, "详情", "https://www.baidu.com",
                    "action_card", response.getAccessToken(), baseUrl, DingDingApiUrl.TopApi.getCorpconversationAsyncsend_v2);
        } catch (Exception e) {
            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
        }

        if (booleanReturnBean != null) {
            return booleanReturnBean;
        }

        if (responseSend.getErrcode() != Constant.Response.ok) {
            try {
                DingDingRequestUtils.getOapiSendResponse(responseSend.getTaskId(), agentId, response.getAccessToken(), baseUrl, DingDingApiUrl.TopApi.getSendprogress);
            } catch (Exception e) {
                booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
                CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
            }
        }

        if (booleanReturnBean == null) {
            booleanReturnBean = CommonUtil.getTrueReturnBean("消息发送成功!");
        }

        return booleanReturnBean;
    }

}

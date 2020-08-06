package com.quickshare.quicksharedingdingservice.controller;

import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.quickshare.quicksharedingdingservice.beans.BooleanReturnBean;
import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.constant.DingDingApi;
import com.quickshare.quicksharedingdingservice.utils.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
//    @Autowired
//    DingDingProperties dingDingProperties;

    @GetMapping("/sendDingDingMessage")
    public BooleanReturnBean sendDingDingMessage() {
        BooleanReturnBean booleanReturnBean = null;
        //切换钉钉企业
        DingDingAppProperties defaultConfig = DingDingSwitchUtils.switchDingDingConfig(null, null);

        String appKey = defaultConfig.getAppKey();
        String appSecret = defaultConfig.getAppsecret();
        String baseUrl = defaultConfig.getBaseUrl();
        Long agentId = defaultConfig.getAgentId();

        OapiGettokenResponse response = null;
        try {
            response = DingDingRequestUtils.getOapiGettokenResponse(appKey, appSecret, baseUrl, DingDingApi.Agent.GetToken);
        } catch (Exception e) {
            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
            return booleanReturnBean;
        }

        OapiUserGetByMobileResponse execute111 = null;
        try {
            execute111 = DingDingRequestUtils.getOapiUserGetByMobileResponse("15212273352", response.getAccessToken(), baseUrl);
        } catch (Exception e) {
            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
            return booleanReturnBean;
        }

        String markDownStr = "### 消息通知" + System.currentTimeMillis()
                + "\n" + "## 测试123111"
                + "\n" + "## 测试123111";
//                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";
//        System.out.println(response.getAccessToken());
        OapiMessageCorpconversationAsyncsendV2Response responseSend = null;
        try {
            responseSend = DingDingRequestUtils.sendActionCardMessage(execute111.getUserid(), agentId, false,
                    "xxx123411111" + System.currentTimeMillis(), markDownStr, "详情", "https://www.baidu.com",
                    Constant.DingDinngMessageType.ActionCard, response.getAccessToken(), baseUrl);
        } catch (Exception e) {
            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
            return booleanReturnBean;
        }
//        OapiMessageCorpconversationGetsendprogressResponse oapiMessageCorpconversationGetsendprogressResponse = null;
        //查询发送消息的状态
        if (responseSend.getErrcode() == Constant.Response.ok) {
            try {
                DingDingRequestUtils.getOapiSendResponse(responseSend.getTaskId(), agentId, response.getAccessToken(), baseUrl);
            } catch (Exception e) {
                booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
                CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
                return booleanReturnBean;
            }
        }

        if (booleanReturnBean == null) {
            booleanReturnBean = CommonUtil.getTrueReturnBean("消息发送成功!");
        }

        return booleanReturnBean;
    }

}

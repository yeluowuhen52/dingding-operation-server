package com.quickshare.quicksharedingdingservice.controller;

import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.quickshare.quicksharedingdingservice.beans.BooleanReturnBean;
import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.constant.DingDingApi;
import com.quickshare.quicksharedingdingservice.utils.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public BooleanReturnBean sendDingDingMessage(@RequestParam(required = false) String phone) {
        BooleanReturnBean booleanReturnBean;
        //切换钉钉企业
        DingDingAppProperties defaultConfig = DingDingSwitchUtils.switchDingDingConfig(null, null);

//        String appKey = defaultConfig.getAppKey();
//        String appSecret = defaultConfig.getAppsecret();
//        String baseUrl = defaultConfig.getBaseUrl();
//        Long agentId = defaultConfig.getAgentId();
        String errorMsg = "";

        String markDownStr = "### 消息通知" + System.currentTimeMillis()
                + "\n" + "## 测试123111"
                + "\n" + "## 测试123111";
        OapiMessageCorpconversationGetsendresultResponse oapiMessageCorpconversationGetsendresultResponse = null;

        try {
            oapiMessageCorpconversationGetsendresultResponse = DingDingMessageUtils.sendNormalDingDingMessage("sendDingDingMessage", defaultConfig,
                    phone, "xxx123411111" + System.currentTimeMillis(),
                    "详情", "https://www.baidu.com", markDownStr, false);
        } catch (Exception e) {
            errorMsg = "异常信息:" + e.toString();
        }

        if (oapiMessageCorpconversationGetsendresultResponse != null) {
            if (oapiMessageCorpconversationGetsendresultResponse.getErrcode() == Constant.Response.ok) {
                errorMsg = CommonUtil.getDingDingSendResult(oapiMessageCorpconversationGetsendresultResponse.getSendResult());
            } else {
                errorMsg = oapiMessageCorpconversationGetsendresultResponse.getErrmsg();
            }
        }
        //else 不做处理，直接取原来的异常

        if (TextUtils.isEmpty(errorMsg)) {
            booleanReturnBean = CommonUtil.getTrueReturnBean("消息发送成功!");
        } else {
            booleanReturnBean = CommonUtil.getTrueReturnBean(errorMsg);
        }

        return booleanReturnBean;
        //换取token
//        OapiGettokenResponse response;
//        try {
//            response = DingDingRequestUtils.getOapiGettokenResponse(appKey, appSecret, baseUrl);
//            CommonUtil.writeNormalInfo("钉钉信息：" + JsonUtils.toJson(defaultConfig) + ",换取token：" + JsonUtils.toJson(response));
//        } catch (Exception e) {
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
//        }
//
//        //换取用户手机号
//        OapiUserGetByMobileResponse execute111;
//        try {
//            execute111 = DingDingRequestUtils.getOapiUserGetByMobileResponse(phone, response.getAccessToken(), baseUrl);
//        } catch (Exception e) {
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
//        }
//
////                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";
////        System.out.println(response.getAccessToken());
//        //发送消息
//        OapiMessageCorpconversationAsyncsendV2Response responseSend;
//        try {
//            responseSend = DingDingRequestUtils.sendActionCardMessage(execute111.getUserid(), agentId, false,
//                    "xxx123411111" + System.currentTimeMillis(), markDownStr, "详情", "https://www.baidu.com",
//                    Constant.DingDinngMessageType.ActionCard, response.getAccessToken(), baseUrl);
//        } catch (Exception e) {
//            booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
//            return booleanReturnBean;
//        }
//
//        //查询发送消息的状态
//        OapiMessageCorpconversationGetsendresultResponse oapiMessageCorpconversationGetsendresultResponse = null;
//        if (responseSend.getErrcode() == Constant.Response.ok) {
//            try {
//                oapiMessageCorpconversationGetsendresultResponse = DingDingRequestUtils.getOapiSendResuktResponse(responseSend.getTaskId(), agentId, response.getAccessToken(), baseUrl);
//            } catch (Exception e) {
//                booleanReturnBean = CommonUtil.getTrueReturnBean(ExceptionUtils.getStackTrace(e));
//                CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
//                return booleanReturnBean;
//            }
//        }

    }

}

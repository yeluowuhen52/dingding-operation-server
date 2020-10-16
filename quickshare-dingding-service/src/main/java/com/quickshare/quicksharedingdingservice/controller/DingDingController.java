package com.quickshare.quicksharedingdingservice.controller;

import com.dingtalk.api.response.*;
import com.quickshare.quicksharedingdingservice.beans.BooleanReturnBean;
import com.quickshare.quicksharedingdingservice.beans.GeneralReturnBean;
import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.constant.DingDingApi;
import com.quickshare.quicksharedingdingservice.utils.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

        String errorMsg = "";

        String markDownStr = "### 消息通知" + System.currentTimeMillis()
                + "\n" + "## 测试123111"
                + "\n" + "## 测试123111";

        //请求参数和基地址拼接
        HashMap<String, String> map = new HashMap<>();
        map.put("customCode", "customCode");
        map.put("appid", "appid");

        String url = CommonUtil.getRedirectDingDingUrl("http://a2610377b5.zicp.vip/H5View/#/PlanOrderDetailPageDingDing", map, "dingoa7ntlpbq3z2nu523m");

        OapiMessageCorpconversationGetsendresultResponse oapiMessageCorpconversationGetsendresultResponse = null;

        try {
            oapiMessageCorpconversationGetsendresultResponse = DingDingMessageUtils.sendNormalDingDingMessage("sendDingDingMessage", defaultConfig,
                    phone, "xxx123411111" + System.currentTimeMillis(),
                    "详情", url, markDownStr, false);
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
    }

}

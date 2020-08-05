package com.quickshare.quicksharedingdingservice.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.quickshare.quicksharedingdingservice.config.DingDingProperties;
import com.quickshare.quicksharedingdingservice.utils.CommonUtil;
import com.quickshare.quicksharedingdingservice.utils.DingDingApiUrl;
import com.taobao.api.ApiException;
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
    public String sendDingDingMessage() {
        //        CorpId:ding4ea5826e2e1e1c0fee0f45d8e4f7c288
//        AgentId：844092190
//        AppKey：dingjlapmolmuexyjgxd
//        AppSecret：plDvOLLUjf4ayNjlWlj1py-fD9e25BUqtGQ-oJYx88_70hGDxUZjIriHeELM89Ls

        String appKey = dingDingProperties.getAppConfigs().get(0).getAppKey();
        String appSecret = dingDingProperties.getAppConfigs().get(0).getAppsecret();
        String baseUrl = dingDingProperties.getAppConfigs().get(0).getBaseUrl();
        Long agentid = dingDingProperties.getAppConfigs().get(0).getAgentid();
        CommonUtil.writeErrorInfo("试下日志");

        DefaultDingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApiUrl.Agent.getToken);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        DingTalkClient client111 = new DefaultDingTalkClient(baseUrl + DingDingApiUrl.User.getByMobile);
        OapiUserGetByMobileRequest request111 = new OapiUserGetByMobileRequest();
        request111.setMobile("15212273352");

        OapiUserGetByMobileResponse execute111 = null;

        try {
            execute111 = client111.execute(request111, response.getAccessToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }

        DingTalkClient client222 = new DefaultDingTalkClient(baseUrl + DingDingApiUrl.TopApi.getCorpconversationAsyncsend_v2);
        OapiMessageCorpconversationAsyncsendV2Request request222 = new OapiMessageCorpconversationAsyncsendV2Request();
        request222.setUseridList(execute111.getUserid());
        request222.setAgentId(agentid);
        request222.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("xxx123411111" + System.currentTimeMillis());
//        String markDownStr = "### 测试123111"+System.currentTimeMillis();
        String markDownStr = "### 消息通知" + System.currentTimeMillis()
                + "\n" + "## 测试123111"
                + "\n" + "## 测试123111";
//                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";

        msg.getActionCard().setMarkdown(markDownStr);
        msg.getActionCard().setSingleTitle("详情");
        msg.getActionCard().setSingleUrl("https://www.baidu.com");
        msg.setMsgtype("action_card");
        request222.setMsg(msg);

        System.out.println(response.getAccessToken());
        OapiMessageCorpconversationAsyncsendV2Response response222 = null;

        try {
            response222 = client222.execute(request222, response.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(response222.getErrmsg());
        }
        System.out.println(response222.getTaskId());

        return "ok";
    }

}

package com.quickshare.quicksharedingdingservice.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendprogressRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendprogressResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import org.apache.http.util.TextUtils;

/**
 * @Author: Jiang
 * @Description: 钉钉请求工具类
 * @Date: 2020-08-05 16:33
 * @Version: 1.0
 * @Update:
 */
public class DingDingRequestUtils {
    /**
     * 获取用户的AccessToken
     *
     * @param appKey    appKey
     * @param appSecret appSecret
     * @param baseUrl   基地址
     * @param apiUrl    api地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiGettokenResponse getOapiGettokenResponse(String appKey, String appSecret, String baseUrl, String apiUrl) throws Exception {
        OapiGettokenResponse response = null;
        DefaultDingTalkClient client = new DefaultDingTalkClient(baseUrl + apiUrl);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        response = client.execute(request);
        return response;
    }

    /**
     * 通过手机号获取用户的信息
     *
     * @param mobile      手机号
     * @param accessToken accessToken
     * @param baseUrl     基地址
     * @param apiUrl      api地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiUserGetByMobileResponse getOapiUserGetByMobileResponse(String mobile, String accessToken, String baseUrl, String apiUrl) throws Exception {
        DingTalkClient client111 = new DefaultDingTalkClient(baseUrl + DingDingApiUrl.User.getByMobile);
        OapiUserGetByMobileRequest request111 = new OapiUserGetByMobileRequest();
        request111.setMobile(mobile);
        OapiUserGetByMobileResponse response = null;
        response = client111.execute(request111, accessToken);
        return response;
    }

    /**
     * 发送消息
     *
     * @param userIds
     * @param agentId
     * @param toAllUser
     * @param title
     * @param content
     * @param singleTitle
     * @param url
     * @param messageType
     * @param accessToken
     * @param baseUrl
     * @param apiUrl
     * @return
     * @throws Exception 异常信息
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String userIds, Long agentId, boolean toAllUser,
                                                                                       String title, String content, String singleTitle, String url, String messageType,
                                                                                       String accessToken, String baseUrl, String apiUrl) throws Exception {
        DingTalkClient client222 = new DefaultDingTalkClient(baseUrl + apiUrl);
        OapiMessageCorpconversationAsyncsendV2Request request222 = new OapiMessageCorpconversationAsyncsendV2Request();
        request222.setUseridList(userIds);
        request222.setAgentId(agentId);
        request222.setToAllUser(toAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(title);
//        String markDownStr = "### 测试123111"+System.currentTimeMillis();
//        String markDownStr = "### 消息通知" + System.currentTimeMillis()
//                + "\n" + "## 测试123111"
//                + "\n" + "## 测试123111";
//                +"\n"+"<font face=\"STCAIYUN\">我是华文彩云</font>";

        msg.getActionCard().setMarkdown(content);
        msg.getActionCard().setSingleTitle(singleTitle);
        if (!TextUtils.isEmpty(url)) {
            msg.getActionCard().setSingleUrl(url);
        }
        msg.setMsgtype(messageType);
        request222.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response222 = null;
        response222 = client222.execute(request222, accessToken);
        return response222;
    }

    /**
     * 获取消息发送状态
     *
     * @param taskId
     * @param agentId
     * @param accessToken
     * @param baseUrl
     * @param apiUrl
     * @return
     * @throws Exception 异常信息
     */
    public static OapiMessageCorpconversationGetsendprogressResponse getOapiUserGetByMobileResponse(Long taskId, Long agentId, String accessToken, String baseUrl, String apiUrl) throws Exception {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress");
        DingTalkClient client = new DefaultDingTalkClient(baseUrl + apiUrl);
        OapiMessageCorpconversationGetsendprogressRequest request = new OapiMessageCorpconversationGetsendprogressRequest();
        request.setAgentId(agentId);
        request.setTaskId(taskId);
        OapiMessageCorpconversationGetsendprogressResponse response = client.execute(request, accessToken);
        return response;
    }
}

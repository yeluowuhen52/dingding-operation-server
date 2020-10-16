package com.quickshare.quicksharedingdingservice.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.constant.DingDingApi;
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
     * @return
     * @throws Exception 异常信息
     */
    public static OapiGettokenResponse getOapiGettokenResponse(String appKey, String appSecret, String baseUrl) throws Exception {
        OapiGettokenResponse response;
        DefaultDingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApi.Agent.GetToken);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        response = client.execute(request);
        if (response.getErrcode() != 0) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }

    /**
     * 通过手机号获取用户的信息
     *
     * @param mobile      手机号
     * @param accessToken accessToken
     * @param baseUrl     基地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiUserGetByMobileResponse getOapiUserGetByMobileResponse(String mobile, String accessToken, String baseUrl) throws Exception {
        DingTalkClient client111 = new DefaultDingTalkClient(baseUrl + DingDingApi.User.GetByMobile);
        OapiUserGetByMobileRequest request111 = new OapiUserGetByMobileRequest();
        request111.setMobile(mobile);
        OapiUserGetByMobileResponse response;
        response = client111.execute(request111, accessToken);
        if (response.getErrcode() != Constant.Response.ok) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }

    /**
     * 发送消息
     *
     * @param userIds     用户id，可以用逗号隔开
     * @param agentId     agentId
     * @param toAllUser   发送给所有用户
     * @param title       标题
     * @param content     内容支持markdown
     * @param singleTitle 链接提示标题
     * @param url         链接地址
     * @param messageType 消息类型
     * @param accessToken accessToken
     * @param baseUrl     基地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String userIds, Long agentId, boolean toAllUser,
                                                                                       String title, String content, String singleTitle, String url, String messageType,
                                                                                       String accessToken, String baseUrl) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApi.TopApi.GetCorpconversationAsyncsend_v2);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userIds);
        request.setAgentId(agentId);
        request.setToAllUser(toAllUser);

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
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response;
        response = client.execute(request, accessToken);
        if (response.getErrcode() != Constant.Response.ok) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }

    /**
     * 获取消息发送状态
     *
     * @param taskId      任务id，发送消息成功之后返回的
     * @param agentId     agentId
     * @param accessToken accessToken
     * @param baseUrl     基地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiMessageCorpconversationGetsendprogressResponse getOapiSendResponse(Long taskId, Long agentId, String accessToken, String baseUrl) throws Exception {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress");
        DingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApi.TopApi.GetSendprogress);
        OapiMessageCorpconversationGetsendprogressRequest request = new OapiMessageCorpconversationGetsendprogressRequest();
        request.setAgentId(agentId);
        request.setTaskId(taskId);
        OapiMessageCorpconversationGetsendprogressResponse response = client.execute(request, accessToken);
        if (response.getErrcode() != Constant.Response.ok) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }

    /**
     * 获取消息返回状态
     *
     * @param taskId      任务id，发送消息成功之后返回的
     * @param agentId     agentId
     * @param accessToken accessToken
     * @param baseUrl     基地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiMessageCorpconversationGetsendresultResponse getOapiSendResuktResponse(Long taskId, Long agentId, String accessToken, String baseUrl) throws Exception {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendresult");
        DingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApi.TopApi.GetSendresult);
        OapiMessageCorpconversationGetsendresultRequest request = new OapiMessageCorpconversationGetsendresultRequest();
        request.setAgentId(agentId);
        request.setTaskId(taskId);
        OapiMessageCorpconversationGetsendresultResponse response = client.execute(request, accessToken);
        if (response.getErrcode() != Constant.Response.ok) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }

    /**
     * 服务端通过临时授权码获取授权用户的个人信息
     *
     * @param code      网页上返回的code
     * @param appId     appId
     * @param appSecret appSecret
     * @param baseUrl   基地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiSnsGetuserinfoBycodeResponse getUnionidByCode(String code, String appId, String appSecret, String baseUrl) throws Exception {
//        DefaultDingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        DingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApi.TopApi.Getuserinfo_bycode);
        OapiSnsGetuserinfoBycodeRequest request = new OapiSnsGetuserinfoBycodeRequest();
        request.setTmpAuthCode(code);
        OapiSnsGetuserinfoBycodeResponse response = client.execute(request, appId, appSecret);
        if (response.getErrcode() != Constant.Response.ok) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }

    /**
     * 根据unionid获取userid
     *
     * @param unionId     员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变
     * @param accessToken accessToken
     * @param baseUrl     基地址
     * @return
     * @throws Exception 异常信息
     */
    public static OapiUserGetUseridByUnionidResponse getUseridByUnionid(String unionId, String accessToken, String baseUrl) throws Exception {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getUseridByUnionid");
        DingTalkClient client = new DefaultDingTalkClient(baseUrl + DingDingApi.TopApi.GetUseridByUnionid);
        OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
        request.setUnionid(unionId);
        request.setHttpMethod("GET");
        OapiUserGetUseridByUnionidResponse response = client.execute(request, accessToken);
        if (response.getErrcode() != Constant.Response.ok) {
            throw new Exception(response.getErrmsg());
        }
        return response;
    }
}

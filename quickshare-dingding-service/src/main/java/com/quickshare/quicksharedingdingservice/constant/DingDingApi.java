package com.quickshare.quicksharedingdingservice.constant;

/**
 * @Author: Jiang
 * @Description: 钉钉请求地址
 * @Date: 2020-08-04 16:33
 * @Version: 1.0
 * @Update:
 */
public class DingDingApi {
    /**
     * 基础信息接口
     */
    public static class Agent {
        /**
         * 获取AccessToken
         */
        public static String GetToken = "/gettoken";
    }

    /**
     * 用户相关接口
     */
    public static class User {
        /**
         * 通过手机号获取用户信息
         */
        public static String GetByMobile = "/user/get_by_mobile";
    }

    /**
     * 消息相关接口
     */
    public static class TopApi {
        /**
         * 发送消息
         */
        public static String GetCorpconversationAsyncsend_v2 = "/topapi/message/corpconversation/asyncsend_v2";
        /**
         * 获取消息发送返回进度
         */
        public static String GetSendprogress = "/topapi/message/corpconversation/getsendprogress";
        /**
         * 获取消息发送返回结果
         */
        public static String GetSendresult = "/topapi/message/corpconversation/getsendresult";
    }
}

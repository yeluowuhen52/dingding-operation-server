package com.quickshare.quicksharedingdingservice.constant;

/**
 * @Author: Jiang 常量
 * @Description:
 * @Date: 2020-08-05 13:16
 * @Version: 1.0
 * @Update:
 */
public class Constant {
    /**
     * 默认钉钉配置信息
     */
    public static String DefaultDingDingConfig = "default_dingding_config";
    /**
     * 默认钉钉配置信息(appId)
     */
    public static String DefaultDingDingAppIdConfig = "default_dingding_appId_config";
    /**
     * 默认钉钉基地址
     */
    public static String DefaultDingDingUrl = "https://oapi.dingtalk.com";

    /**
     * 返回值对象
     */
    public static class Response {
        /**
         * 服务返回正确信息
         */
        public static int ok = 0;
    }

    /**
     * 返回错误码
     */
    public static class ErrorCode {
        //临时授权只能使用一次后就不能在使用。 需要重新执行授权操作有开放平台推送新的临时授权码。
        public static Long tokenError1 = 40102L;
        //请检查该suitetoken是否已经过期。
        public static Long tokenError2 = 42009L;
        //请检查该suitetoken是否已经过期或不正确。
        public static Long tokenError3 = 48003L;
        //不合法的accessToken
        public static Long tokenError4 = 40014L;
    }

    /**
     * 返回错误码
     */
    public static class ErrorCodeStr {
        //临时授权只能使用一次后就不能在使用。 需要重新执行授权操作有开放平台推送新的临时授权码。
        public static String tokenError1 = "40102";
        //请检查该suitetoken是否已经过期。
        public static String tokenError2 = "42009";
        //请检查该suitetoken是否已经过期或不正确。
        public static String tokenError3 = "48003";
        //不合法的accessToken
        public static String tokenError4 = "40014";
    }

    /**
     * 钉钉消息类型
     */
    public static class DingDinngMessageType {
        /**
         * 卡片消息
         */
        public static String ActionCard = "action_card";
        /**
         * 文本消息
         */
        public static String TEXT = "text";
        /**
         * 图片消息
         */
        public static String IMAGE = "image";
        /**
         * 语音消息
         */
        public static String VOICE = "voice";
        /**
         * 文件消息
         */
        public static String FILE = "file";
        /**
         * 链接消息
         */
        public static String LINK = "link";
        /**
         * OA消息
         */
        public static String OA = "oa";
        /**
         * markdown消息
         */
        public static String MARKDOWN = "markdown";
    }
}

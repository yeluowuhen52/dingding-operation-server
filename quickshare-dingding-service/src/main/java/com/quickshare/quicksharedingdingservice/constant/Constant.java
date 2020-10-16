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

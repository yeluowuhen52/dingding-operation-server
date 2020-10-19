package com.quickshare.quicksharedingdingservice.config;

import com.quickshare.quicksharedingdingservice.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Jiang
 * @Description: 钉钉消息推送(企业信息 + 第三方应用appId信息)
 * @Date: 2020-08-04 16:26
 * @Version: 1.0
 * @Update:
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dingding")
//@Component
public class DingDingProperties {
    /**
     * 设置钉钉的corpId
     */
    private String corpId;
    /**
     * 基地址
     */
    private String baseUrl;
    /**
     * 企业用信息
     */
    private List<DingDingAppProperties> appConfigs;
    /**
     * 企业appId信息
     */
    private List<DingDingAppidsProperties> appidConfigs;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}

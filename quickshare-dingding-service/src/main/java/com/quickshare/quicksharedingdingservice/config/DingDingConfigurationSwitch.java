package com.quickshare.quicksharedingdingservice.config;

import com.google.common.collect.Maps;
import com.quickshare.quicksharedingdingservice.utils.ConcurrentHashMapCacheUtilsInMemory;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Binary Wang(https://github.com/binarywang)
 * @update Jiang 新增获取默认wxCpService方法
 */
@Configuration
@EnableConfigurationProperties(DingDingProperties.class)
public class DingDingConfigurationSwitch {
    private DingDingProperties properties;


    private static Map<String, DingDingAppProperties> dingDingAppPropertiesMap = Maps.newHashMap();
    private static Map<String, DingDingAppidsProperties> dingDingIdAppPropertiesMap = Maps.newHashMap();

    @Autowired
    public DingDingConfigurationSwitch(DingDingProperties properties) {
        this.properties = properties;
    }


    public static DingDingAppProperties getConfigService(String corpId, Long agentId) {
        return dingDingAppPropertiesMap.get(corpId + String.valueOf(agentId));
    }

    public static DingDingAppidsProperties getAppidConfigService(String appId) {
        return dingDingIdAppPropertiesMap.get(appId);
    }

    /**
     * 获取默认配置信息
     *
     * @return
     */
    public static DingDingAppProperties getDefaultConfig() {
        return dingDingAppPropertiesMap.get(ConcurrentHashMapCacheUtilsInMemory.getCache(Constant.DefaultDingDingConfig));
    }

    /**
     * 获取默认AppId配置信息
     *
     * @return
     */
    public static DingDingAppidsProperties getDefaultAppIdConfig() {
        return dingDingIdAppPropertiesMap.get(ConcurrentHashMapCacheUtilsInMemory.getCache(Constant.DefaultDingDingAppIdConfig));
    }

    /**
     * 新增配置信息
     *
     * @return
     */
    public static void addDingDingConfig(DingDingAppProperties dingDingAppProperties) {
        dingDingAppPropertiesMap.put(String.valueOf(dingDingAppProperties.getCorpId()) + String.valueOf(dingDingAppProperties.getAgentId()), dingDingAppProperties);
    }

    /**
     * 新增(AppId)配置信息
     *
     * @return
     */
    public static void addDingDingAppIdConfig(DingDingAppidsProperties dingDingAppidsProperties) {
        dingDingIdAppPropertiesMap.put(dingDingAppidsProperties.getAppId(), dingDingAppidsProperties);
    }

    @PostConstruct
    public void initServices() {
        dingDingAppPropertiesMap = this.properties.getAppConfigs().stream().map(a -> {
            val configStorage = new DingDingAppProperties();
            configStorage.setCorpId(this.properties.getCorpId());
            configStorage.setAppKey(a.getAppKey());
            configStorage.setAppsecret(a.getAppsecret());
            configStorage.setBaseUrl(this.properties.getBaseUrl());
            configStorage.setAgentId(a.getAgentId());

            if (null == ConcurrentHashMapCacheUtilsInMemory.getCache(Constant.DefaultDingDingConfig)) {
                ConcurrentHashMapCacheUtilsInMemory.setCache(Constant.DefaultDingDingConfig, String.valueOf(this.properties.getCorpId()) + String.valueOf(a.getAgentId()));
            }

            return configStorage;
        }).collect(Collectors.toMap(service -> String.valueOf(this.properties.getCorpId()) + String.valueOf(service.getAgentId()), a -> a));

        dingDingIdAppPropertiesMap = this.properties.getAppidConfigs().stream().map(a -> {
            val configStorage = new DingDingAppidsProperties();
            configStorage.setAppId(a.getAppId());
            configStorage.setAppSecret(a.getAppSecret());
            configStorage.setCorpId(this.properties.getCorpId());

            if (null == ConcurrentHashMapCacheUtilsInMemory.getCache(Constant.DefaultDingDingAppIdConfig)) {
                ConcurrentHashMapCacheUtilsInMemory.setCache(Constant.DefaultDingDingAppIdConfig, String.valueOf(a.getAppId()));
            }

            return configStorage;
        }).collect(Collectors.toMap(service -> String.valueOf(service.getAppId()), a -> a));
    }

}

package com.quickshare.quicksharedingdingservice.utils;

import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.config.DingDingConfigurationSwitch;
import org.apache.http.util.TextUtils;

/**
 * @Author: Jiang
 * @Description: 切换钉钉信息(支持多企业切换)
 * @Date: 2020-08-05 13:30
 * @Version: 1.0
 * @Update:
 */
public class DingDingSwitchUtils {
    /**
     * 切换钉钉配置信息
     *
     * @param corpId  企业id
     * @param agentId 应用id
     */
    public static DingDingAppProperties switchDingDingConfig(String corpId, Long agentId) {
        DingDingAppProperties selectConfig;
        if (TextUtils.isEmpty(corpId) || agentId == null) {
            //测试新增配置信息
//            DingDingAppProperties dingDingAppProperties = new DingDingAppProperties();
//
//            dingDingAppProperties.setCorpId("exewfewfewf");
//            dingDingAppProperties.setBaseUrl("http://www.baidu.com");
//            dingDingAppProperties.setAppsecret("sfasfasf");
//            dingDingAppProperties.setAppKey("aaaaaa");
//            dingDingAppProperties.setAgentId(641732742L);
//
//            DingDingConfigurationSwitch.addDingDingConfig(dingDingAppProperties);
//            DingDingSwitchUtils.switchDingDingConfig("exewfewfewf", 641732742L);

            selectConfig = DingDingConfigurationSwitch.getDefaultConfig();
        } else {
            selectConfig = DingDingConfigurationSwitch.getConfigService(corpId, agentId);
        }

        if (selectConfig == null) {
            CommonUtil.writeNormalInfo("切换企业:" + corpId + ",应用:" + agentId + "失败，切换回默认企业...");
            selectConfig = DingDingConfigurationSwitch.getDefaultConfig();
        }
        CommonUtil.writeNormalInfo("切换企业信息最终结果，企业信息:" + JsonUtils.toJson(selectConfig));
        return selectConfig;
    }
}

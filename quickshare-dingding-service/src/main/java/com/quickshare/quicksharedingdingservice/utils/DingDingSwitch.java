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
public class DingDingSwitch {
    /**
     * 切换钉钉配置信息
     *
     * @param corpId  企业id
     * @param agentid 应用id
     */
    public static DingDingAppProperties switchDingDingConfig(String corpId, Integer agentid) {
        DingDingAppProperties selectConfig;
        if (TextUtils.isEmpty(corpId) || agentid == null) {
            selectConfig = DingDingConfigurationSwitch.getDefaultConfig();
        } else {
            selectConfig = DingDingConfigurationSwitch.getConfigService(corpId, agentid);
        }

        if (selectConfig == null) {
            CommonUtil.writeNormalInfo("切换企业:" + corpId + ",应用:" + agentid + "失败，切换回默认企业...");
            selectConfig = DingDingConfigurationSwitch.getDefaultConfig();
        }
        CommonUtil.writeNormalInfo("切换企业信息最终结果，企业信息:" + JsonUtils.toJson(selectConfig));
        return selectConfig;
    }
}

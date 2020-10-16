package com.quickshare.quicksharedingdingservice.config;

import com.quickshare.quicksharedingdingservice.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Jiang
 * @Description: 钉钉消息推送(应用信息)
 * @Date: 2020-08-04 16:26
 * @Version: 1.0
 * @Update:
 */
@Getter
@Setter
public class DingDingAppidsProperties {
  /**
   * 钉钉corpId
   */
  private String corpId;
  /**
   * 钉钉appId
   */
  private String appId;
  /**
   * 钉钉appSecret
   */
  private String appSecret;

  @Override
  public String toString() {
    return JsonUtils.toJson(this);
  }
}

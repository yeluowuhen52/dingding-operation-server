package com.quickshare.quicksharedingdingservice.config;

import com.quickshare.quicksharedingdingservice.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Jiang
 * @Description: 钉钉消息推送(企业信息)
 * @Date: 2020-08-04 16:26
 * @Version: 1.0
 * @Update:
 */
@Getter
@Setter
public class DingDingAppProperties {
  /**
   * 钉钉appKey
   */
  private String appKey;
  /**
   * 钉钉appsecret
   */
  private String appsecret;
  /**
   * 钉钉应用agentid
   */
  private Long agentid;
  /**
   * 钉钉基地址
   */
  private String baseUrl;

  @Override
  public String toString() {
    return JsonUtils.toJson(this);
  }
}
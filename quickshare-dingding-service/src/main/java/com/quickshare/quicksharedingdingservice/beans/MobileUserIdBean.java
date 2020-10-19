package com.quickshare.quicksharedingdingservice.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Jiang
 * @Description: 用户通过手机号换取的UserId
 * @Date: 2020-10-19 12:38
 * @Version: 1.0
 * @Update:
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileUserIdBean {
    //返回信息
    private String userId;

    public MobileUserIdBean(String userId) {
        this.userId = userId;
    }
}

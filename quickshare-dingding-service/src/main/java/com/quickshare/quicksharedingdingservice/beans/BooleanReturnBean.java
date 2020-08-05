package com.quickshare.quicksharedingdingservice.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Jiang
 * @Description: 布尔类型返回值
 * @Date: 2019-07-29 12:38
 * @Version: 1.0
 * @Update:
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BooleanReturnBean {
    //返回code 0：成功 负数：失败
    private int code;
    //返回信息
    private String message;
    //返回值 前端进行判断
    private Boolean data;
    /**
     * 是否成功 ：true：成功 false：失败
     */
    private Boolean issuccess;

    public BooleanReturnBean() {
    }

    public BooleanReturnBean(int code, String message, Boolean data, Boolean issuccess) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.issuccess = issuccess;
    }
}

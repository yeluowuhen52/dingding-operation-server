package com.quickshare.quicksharedingdingservice.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Jiang
 * @Description: 通用返回Bean
 * @Date: 2019-10-09 13:48
 * @Version: 1.0
 * @Update:
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralReturnBean<T> {
    //返回code 0：成功 负数：失败
    private int code;
    //返回信息
    private String message;
    //符合筛选条件的记录总数
    private int total;
    //返回值
    private T data;
    /**
     * 是否成功 ：true：成功 false：失败
     */
    private Boolean issuccess;

    public GeneralReturnBean() {
    }

    public GeneralReturnBean(int code, String message, T data, Boolean issuccess) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.issuccess = issuccess;
    }
}

package com.quickshare.quicksharedingdingservice.controller.common;

import com.dingtalk.api.response.*;
import com.quickshare.quicksharedingdingservice.beans.BooleanReturnBean;
import com.quickshare.quicksharedingdingservice.beans.GeneralReturnBean;
import com.quickshare.quicksharedingdingservice.config.DingDingAppProperties;
import com.quickshare.quicksharedingdingservice.constant.Constant;
import com.quickshare.quicksharedingdingservice.utils.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: Jiang
 * @Description: 钉钉消息推送
 * @Date: 2020-08-04 16:02
 * @Version: 1.0
 * @Update:
 */
@RestController
public class CommonOperationController {
//    @Autowired
//    DingDingProperties dingDingProperties;

      @GetMapping("/GetUnionidByCode")
    public GeneralReturnBean<OapiSnsGetuserinfoBycodeResponse.UserInfo> getUnionidByCode(String code, @RequestParam(required = false) String appid,
                                                                                         @RequestParam(required = false) String appSecret) {
        String errorMsg = "";
        //切换钉钉企业
        DingDingAppProperties defaultConfig = DingDingSwitchUtils.switchDingDingConfig(null, null);
        OapiSnsGetuserinfoBycodeResponse oapiSnsGetuserinfoBycodeResponse = null;
        try {
            oapiSnsGetuserinfoBycodeResponse = DingDingRequestUtils.getUnionidByCode(code, "dingoa7ntlpbq3z2nu523m",
                    "Af1zUlPoXVffwjsM6_PCcUiP35E9iMpTUeBp1oUwYBZ-nnz_JyzqYw0skJkqppKD", defaultConfig.getBaseUrl());
        } catch (Exception e) {
            errorMsg = e.toString();
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
        }

        GeneralReturnBean<OapiSnsGetuserinfoBycodeResponse.UserInfo> generalReturnBean = new GeneralReturnBean<>();

        if (TextUtils.isEmpty(errorMsg) && oapiSnsGetuserinfoBycodeResponse != null && oapiSnsGetuserinfoBycodeResponse.getErrcode() != Constant.Response.ok) {
            generalReturnBean.setCode(0);
            generalReturnBean.setMessage("");
            generalReturnBean.setData(oapiSnsGetuserinfoBycodeResponse.getUserInfo());
        } else {
            generalReturnBean.setCode(-1);
            generalReturnBean.setMessage("异常信息:" + errorMsg);
            generalReturnBean.setIssuccess(false);
        }

        return generalReturnBean;
    }

    /**
     * unionid换取用户userid
     *
     * @param unionid
     * @return
     */
    @GetMapping("/GetUnionidByUnionid")
    public GeneralReturnBean<String> getUseridByUnionid(String unionid) {
        //切换钉钉企业
        DingDingAppProperties defaultConfig = DingDingSwitchUtils.switchDingDingConfig(null, null);
        String errorMsg = "";

        //换取token
        OapiGettokenResponse response = null;
        try {
            response = DingDingRequestUtils.getOapiGettokenResponse(defaultConfig.getAppKey(), defaultConfig.getAppsecret(), defaultConfig.getBaseUrl());
            CommonUtil.writeNormalInfo("钉钉信息：" + JsonUtils.toJson(defaultConfig) + ",换取token：" + JsonUtils.toJson(response));
        } catch (Exception e) {
            errorMsg += e.toString();
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
        }

        if (response != null) {
            //换取用户信息
            OapiUserGetUseridByUnionidResponse oapiUserGetUseridByUnionidResponse = null;
            try {
                oapiUserGetUseridByUnionidResponse = DingDingRequestUtils.getUseridByUnionid(unionid, response.getAccessToken(), defaultConfig.getBaseUrl());
            } catch (Exception e) {
                errorMsg += e.toString();
                CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
            }

            GeneralReturnBean<String> generalReturnBean = new GeneralReturnBean<>();

            if (TextUtils.isEmpty(errorMsg) && oapiUserGetUseridByUnionidResponse != null && Constant.Response.ok == oapiUserGetUseridByUnionidResponse.getErrcode().intValue()) {
                generalReturnBean.setCode(0);
                generalReturnBean.setMessage("");
                generalReturnBean.setData(oapiUserGetUseridByUnionidResponse.getUserid());
            } else {
                generalReturnBean.setCode(-1);
                generalReturnBean.setMessage("异常信息:" + errorMsg);
                generalReturnBean.setIssuccess(false);
            }

            return generalReturnBean;
        } else {
            GeneralReturnBean<String> generalReturnBean = new GeneralReturnBean<>();
            generalReturnBean.setCode(-1);
            generalReturnBean.setMessage("异常信息:" + errorMsg);
            generalReturnBean.setIssuccess(false);
            return generalReturnBean;
        }

    }

    /**
     * 手机号换取用户userid
     *
     * @param phone
     * @return
     */
    @GetMapping("/GetUserInfoGetByMobileResponse")
    public GeneralReturnBean<String> getUserInfoGetByMobileResponse(String phone) {
        //切换钉钉企业
        DingDingAppProperties defaultConfig = DingDingSwitchUtils.switchDingDingConfig(null, null);
        String errorMsg = "";

        //换取token
        OapiGettokenResponse response = null;
        try {
            response = DingDingRequestUtils.getOapiGettokenResponse(defaultConfig.getAppKey(), defaultConfig.getAppsecret(), defaultConfig.getBaseUrl());
            CommonUtil.writeNormalInfo("钉钉信息：" + JsonUtils.toJson(defaultConfig) + ",换取token：" + JsonUtils.toJson(response));
        } catch (Exception e) {
            errorMsg += e.toString();
            CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
        }

        if (response != null) {
            //换取用户手机号
            OapiUserGetByMobileResponse executePhone = null;
            try {
                executePhone = DingDingRequestUtils.getOapiUserGetByMobileResponse(phone, response.getAccessToken(), defaultConfig.getBaseUrl());
            } catch (Exception e) {
                errorMsg += e.toString();
                CommonUtil.writeErrorInfo(ExceptionUtils.getStackTrace(e));
            }

            GeneralReturnBean<String> generalReturnBean = new GeneralReturnBean<>();

            if (TextUtils.isEmpty(errorMsg) && executePhone != null && Constant.Response.ok == executePhone.getErrcode().intValue()) {
                generalReturnBean.setCode(0);
                generalReturnBean.setMessage("");
                generalReturnBean.setData(executePhone.getUserid());
            } else {
                generalReturnBean.setCode(-1);
                generalReturnBean.setMessage("异常信息:" + errorMsg);
                generalReturnBean.setIssuccess(false);
            }

            return generalReturnBean;
        } else {
            GeneralReturnBean<String> generalReturnBean = new GeneralReturnBean<>();
            generalReturnBean.setCode(-1);
            generalReturnBean.setMessage("异常信息:" + errorMsg);
            generalReturnBean.setIssuccess(false);
            return generalReturnBean;
        }

    }

}

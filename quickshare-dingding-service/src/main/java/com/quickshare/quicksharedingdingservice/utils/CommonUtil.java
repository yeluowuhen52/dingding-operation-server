package com.quickshare.quicksharedingdingservice.utils;

import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.quickshare.quicksharedingdingservice.beans.BooleanReturnBean;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公共工具类
 *
 * @Author: Jiang
 * @Description:
 * @Date: 2019-07-29 13:40
 * @Version: 1.0
 * @Update:
 */
public class CommonUtil {
    //日志类
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    //bool类型返回值封装
//    private static BooleanReturnBean booleanReturnBean;
    //通用返回值(客户信息)
//    private static GeneralReturnBean<List<CustomInfoBean>> generalCustomReturnBean;
    //通用返回值(消息模板信息)
//    private static GeneralReturnBean<List<MessageTemplateConf>> generalMessageTemplateReturnBean;
    //通用返回值(消息配置信息)
//    private static GeneralReturnBean<List<CustomMessageConf>> generalCustomMessageConfReturnBean;
    //验证码信息封装
//    private static MsgTimeBean msgTimeBean;
    //是否写日志
    private static boolean isWriteLog = true;


    /**
     * 错误Boolean信息返回封装
     *
     * @param message
     * @return
     */
    public static BooleanReturnBean getFalseReturnBean(String message) {
//        if (booleanReturnBean == null) {
//            synchronized (CommonUtil.class) {
//                if (booleanReturnBean == null) {
//                    booleanReturnBean = new BooleanReturnBean();
//                }
//            }
//        }
        BooleanReturnBean booleanReturnBean = new BooleanReturnBean();
        booleanReturnBean.setIssuccess(false);
        booleanReturnBean.setMessage(message);
        booleanReturnBean.setData(false);
        booleanReturnBean.setCode(-1);
        return booleanReturnBean;
    }

    /**
     * 成功Boolean信息返回封装
     *
     * @param message
     * @return
     */
    public static BooleanReturnBean getTrueReturnBean(String message) {
//        if (booleanReturnBean == null) {
//            synchronized (CommonUtil.class) {
//                if (booleanReturnBean == null) {
//                    booleanReturnBean = new BooleanReturnBean();
//                }
//            }
//        }
        BooleanReturnBean booleanReturnBean = new BooleanReturnBean();
        booleanReturnBean.setIssuccess(true);
        booleanReturnBean.setMessage(message);
        booleanReturnBean.setData(true);
        booleanReturnBean.setCode(0);
        return booleanReturnBean;
    }


    /**
     * 获得当天零时零分零秒
     *
     * @param DayDelay 天数变更值
     * @return
     */
    public static Date getDateByDay(int DayDelay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得倒计时时间
     *
     * @param satrtTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long getCountDownTime(long satrtTime, long endTime) {
        return satrtTime - endTime;
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String getCurrentTime() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        return df.format(new Date());
    }

    /**
     * 获取guid
     *
     * @return
     */
    public static String getGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    /**
     * 写正常日志
     *
     * @param msg
     */
    public static void writeNormalInfo(String msg) {
        if (isWriteLog) {
            logger.info(msg);
        }
    }

    /**
     * 写错误日志
     *
     * @param msg
     */
    public static void writeErrorInfo(String msg) {
        if (isWriteLog) {
            logger.info(msg);
        }
    }

    /**
     * 根据传参获取返回List(String)
     *
     * @param stringList
     */
    public static List<String> getStringList(String stringList) {
        List<String> returnList = new ArrayList<>();
        String[] split;
        if (!TextUtils.isEmpty(stringList)) {
            split = stringList.split(",");
            for (int i = 0; i < split.length; i++) {
                returnList.add(split[i]);
            }
        }
        return returnList;
    }

    /**
     * 根据传参获取返回List(Integer)
     *
     * @param stringList
     */
    public static List<Integer> getIntegerList(String stringList) {
        List<Integer> returnList = new ArrayList<>();
        String[] split;
        if (!TextUtils.isEmpty(stringList)) {
            split = stringList.split(",");
            for (int i = 0; i < split.length; i++) {
                returnList.add(Integer.valueOf(split[i]));
            }
        }
        return returnList;
    }


    /**
     * 从网络Url中下载文件（预留给未来下载网络图片使用）
     *
     * @param urlStr   下载地址
     * @param fileName 文件名
     * @param savePath 保存路径
     */
    public static boolean downLoadFromUrl(String urlStr, String fileName, String savePath) {
        Boolean isDownloaded = true;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception ex) {
            //异常日志
            logger.error("下载图片" + urlStr + "失败，请检查！异常原因:" + ex.toString());
            isDownloaded = false;
        }
        // System.out.println("info:" + url + " download success");
        return isDownloaded;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 拼接正确的url(微信消息详情)
     *
     * @return
     */
    public static String getRealWecharUrl(String baseUrl, String params, String values) {
        String realUrl;
        try {
            String[] urls = baseUrl.split("#");
            realUrl = urls[0] + "?" + params + "=" + values + "#" + urls[1];
        } catch (Exception ex) {
            realUrl = "";
        }
        return realUrl;
    }

    /**
     * 拼接正确的url(微信消息详情多个参数)
     *
     * @return
     */
    public static String getMultiRealWecharUrl(String baseUrl, HashMap<String, String> params) {
        String realUrl;
        String paramStr = "";
        try {
            for (String key : params.keySet()) {
                paramStr = paramStr + key + "=" + params.get(key) + "&";
            }
            if (!TextUtils.isEmpty(paramStr)) {
                paramStr = paramStr.substring(0, paramStr.length() - 1);
            }
            String[] urls = baseUrl.split("#");
            realUrl = urls[0] + "?" + paramStr + "#" + urls[1];
//            realUrl = baseUrl+"?"+paramStr;
        } catch (Exception ex) {
            realUrl = "";
        }
        return realUrl;
    }

    /**
     * 获取消息详情重定向地址
     *
     * @param baseUrl
     * @param params
     * @param appId
     * @return
     */
    public static String getRedirectDingDingUrl(String baseUrl, HashMap<String, String> params, String appId) {
        String multiAddrsss = getMultiRealWecharUrl(baseUrl, params);

        String urlMultiEnCode = "";

        try {
            urlMultiEnCode = URLEncoder.encode(multiAddrsss + "?", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String finalUrl = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=" + appId +
                "&response_type=code&scope=snsapi_auth&state=STATE&redirect_uri=" + urlMultiEnCode;
        return finalUrl;
    }

    /**
     * 获取微信小程序拼接地址
     *
     * @param baseUrl
     * @param params
     * @return
     */
    public static String getMiniProgramUrl(String baseUrl, HashMap<String, String> params) {
        String returnUrl = baseUrl + "?";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            returnUrl = returnUrl + entry.getKey() + "=" + entry.getValue() + "&";
        }

        try {
            returnUrl = returnUrl.substring(0, returnUrl.length() - 1);
        } catch (Exception ex) {
            CommonUtil.writeErrorInfo("小程序地址转换异常:" + ExceptionUtils.getStackTrace(ex));
        }

        return returnUrl;
    }

    /**
     * 获取字符串
     *
     * @param string
     * @return
     */
    public static String getTextIfEmpty(String string) {
        String returnStr = "";
        if (!TextUtils.isEmpty(string)) {
            returnStr = string;
        }
        return returnStr;
    }

    /**
     * 获取拒收状态
     *
     * @param isAllRefuse
     * @return
     */
    public static String getRefuseState(int isAllRefuse) {
        String returnStr = "";
        switch (isAllRefuse) {
            case 0:
                returnStr = "";
                break;
            case 1:
                returnStr = "全部";
                break;
            case 2:
                returnStr = "部分";
                break;
            default:
                returnStr = "";
                break;
        }
        return returnStr;
    }

    /**
     * APP_TYEP取值（1-微信模板消息 2-短信消息  3-微信小程序)
     *
     * @param appType
     * @return
     */
    public static String getWxAppIdCustomInfoAppTypeName(Integer appType) {
        String returnStr = "";
        switch (appType) {
            case 1:
                returnStr = "微信模板消息";
                break;
            case 2:
                returnStr = "短信消息";
                break;
            case 3:
                returnStr = "微信小程序";
                break;
            default:
                returnStr = "";
                break;
        }
        return returnStr;
    }

    /**
     * 获取启用状态
     *
     * @param state STATE取值（0-新建 1-启用 ）
     * @return
     */
    public static String getWxCustomAppIdInfosState(Integer state) {
        String returnStr = "";
        switch (state) {
            case 0:
                returnStr = "新建";
                break;
            case 1:
                returnStr = "启用";
                break;
            default:
                returnStr = "";
                break;
        }
        return returnStr;
    }

    /**
     * 获取钉钉发送消息的返回
     *
     * @param response 钉钉消息返回
     * @return
     */
    public static String getDingDingSendResult(OapiMessageCorpconversationGetsendresultResponse.AsyncSendResult response) {
        String errorMsg = "";

        if (response.getForbiddenList() != null && response.getForbiddenList().size() != 0) {
            errorMsg += "推送被禁止的具体原因列表：" + JsonUtils.toJson(response.getForbiddenList()) + ";";
        }
        // forbidden_list 推送被禁止的具体原因列表
        // failed_user_id_list 发送失败的用户id
        if (response.getFailedUserIdList() != null && response.getFailedUserIdList().size() != 0) {
            errorMsg += "发送失败的用户id：" + JsonUtils.toJson(response.getForbiddenList()) + ";";
        }
        //invalid_dept_id_list 无效的部门id
        if (response.getInvalidDeptIdList() != null && response.getInvalidDeptIdList().size() != 0) {
            errorMsg += "发送失败的无效的部门id：" + JsonUtils.toJson(response.getInvalidDeptIdList()) + ";";
        }
        //invalid_user_id_list 无效的用户id
        if (response.getInvalidUserIdList() != null && response.getInvalidUserIdList().size() != 0) {
            errorMsg += "发送失败的无效的用户id：" + JsonUtils.toJson(response.getInvalidUserIdList()) + ";";
        }
        if (response.getForbiddenUserIdList() != null && response.getForbiddenUserIdList().size() != 0) {
            errorMsg += "发送失败的无效的用户id：" + JsonUtils.toJson(response.getInvalidUserIdList()) + ";";
        }
        //forbidden_list 推送被禁止的具体原因列表
        if (response.getForbiddenList() != null && response.getForbiddenList().size() != 0) {
            errorMsg += "发送失败的推送被禁止的具体原因列表：" + JsonUtils.toJson(response.getForbiddenList()) + ";";
        }
        //因发送消息超过上限而被流控过滤后实际未发送的userid。未被限流的接收者仍会被收到消息。限流规则包括：
        //forbidden_user_id_list
        //1.同一个微应用相同消息的内容同一个用户一天只能接收一次
        //2.同一个微应用给同一个用户发送消息，
        //如果是第三方企业应用，一天最多为50次；
        //如果是企业内部开发方式，一天最多为500次
        if (response.getForbiddenUserIdList() != null && response.getForbiddenUserIdList().size() != 0) {
            errorMsg += "发送失败的无效的用户id：" + JsonUtils.toJson(response.getInvalidUserIdList()) + ";";
        }
        return errorMsg;
    }
}

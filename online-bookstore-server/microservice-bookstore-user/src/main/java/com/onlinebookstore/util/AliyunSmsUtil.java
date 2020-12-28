package com.onlinebookstore.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 阿里云短信发送工具类
 * @author rkc
 * @version 1.0
 * @date 2020/12/3 18:34
 */
@Slf4j
public class AliyunSmsUtil {

    public static final String accessKey = "xxx";
    public static final String accessSecret = "xxx";

    public static final int SEND_SUCCESS = 1;
    public static final int SEND_FAILURE = -1;
    public static final String CODE = "code";

    /**
     * SendSms接口是短信发送接口，支持在一次请求中向多个不同的手机号码发送同样内容的短信。
     * @param mobile 手机号码
     * @param code 模板变量
     */
    public static JSONObject sendSms(String mobile, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //接收短信的手机号码。
        request.putQueryParameter("PhoneNumbers", mobile);
        //短信签名名称
        request.putQueryParameter("SignName", "在线书城");
        //短信模板ID
        request.putQueryParameter("TemplateCode", "SMS_206420625");
        JSONObject param = new JSONObject();
        param.put("code", code);
        //短信模板变量对应的实际值，JSON格式
        request.putQueryParameter("TemplateParam", param.toJSONString());
        //返回结果兼容旧接口格式
        JSONObject result = new JSONObject();
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("ali_sms发送结果:{}", response);
            JSONObject data = JSONObject.parseObject(response.getData());
            if (data.get("Code").equals("OK")) {
                result.put(CODE, SEND_SUCCESS);
            } else {
                result.put(CODE, SEND_FAILURE);
            }
            result.put("message", data.get("Message"));
        } catch (ClientException e) {
            log.error("ali_sms发送结果:{}", e.getMessage());
            result.put(CODE, SEND_FAILURE);
            result.put("message", "发送失败");
        }
        return result;
    }

    public static String getCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}

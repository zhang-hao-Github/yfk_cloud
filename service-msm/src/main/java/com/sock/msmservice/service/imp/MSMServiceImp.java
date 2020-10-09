package com.sock.msmservice.service.imp;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sock.common.utils.RandomUtil;
import com.sock.msmservice.service.MSMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-27 15:56
 **/
@Service
@Slf4j
public class MSMServiceImp implements MSMService {


    public boolean send(String phone, HashMap<String, Object> params) {

        if (StringUtils.isEmpty(phone)) return false;


        log.info("号码{}的验证码是：{}", phone, params.get("code"));
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4GAYMxbKB1F4UmycvLR5", "lforclNiEV5SoiTBqu6xyK0j0jqP1c");
        IAcsClient client = new DefaultAcsClient(profile);
        //公共请求参数

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //设置相关的参数
        ObjectMapper objectMapper = new ObjectMapper();
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "易联自动发卡网");
        request.putQueryParameter("TemplateCode", "SMS_203726727");
//        String s = objectMapper.writeValueAsString(params);
        try {
            request.putQueryParameter("TemplateParam", objectMapper.writeValueAsString(params));

            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();

            log.info("发送成功：{}，result：{}", success, response.getData());
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return false;
    }
}

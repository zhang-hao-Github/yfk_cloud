package com.sock.msmservice.controller;

import com.sock.common.utils.R;
import com.sock.common.utils.RandomUtil;
import com.sock.msmservice.service.MSMService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-27 15:54
 **/
@RestController
@CrossOrigin
@RequestMapping("yfkmsm/msm")
@Api
public class MSMController {

    @Autowired
    private MSMService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMSM(@PathVariable String phone) {
        //随机生成6位验证码
        //前端解决60秒一次问题
        if (!StringUtils.isEmpty(redisTemplate.opsForValue().get("msm:code:" + phone))) {
            return R.error().message("短信发送失败请勿频繁发送短信验证码");
        }

        String code = RandomUtil.getSixBitRandom();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        Boolean send = msmService.send(phone, params);
        if (send) {
            //发送成功 写入redis 并且设置时间 为5分钟内有效
            redisTemplate.opsForValue().set("msm:code:" + phone, code, 5, TimeUnit.MINUTES);
            return R.ok().message("短信发送成功");
        }
        return R.error().message("短信发送失败");
    }


}

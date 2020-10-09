package com.sock.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkUser;
import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.common.exceptionHandler.exception.LoginVerifyException;
import com.sock.common.utils.JwtUtils;
import com.sock.common.utils.MD5;
import com.sock.ucenterservice.entity.vo.RegistVo;
import com.sock.ucenterservice.mapper.UserMapper;
import com.sock.ucenterservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, YfkUser> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    public UserService yfkUserService;

    @Override
    public String loginVerify(YfkUser yfkUser) {
        //登入颁发token 业务
        if (StringUtils.isEmpty(yfkUser.getMobile()) || StringUtils.isEmpty(yfkUser.getPassword())) {
            throw new LoginVerifyException("异常参数");
        }
        System.out.println(yfkUser.getMobile());
        YfkUser user = userMapper.findUserinfo(yfkUser.getMobile());
        log.info("登入获取到的数据{}", user);
        if (null == user) {
            throw new LoginVerifyException("当前账号不存在");
        }
        if (!user.getPassword().equals(MD5.encrypt(yfkUser.getPassword()))) {
            throw new LoginVerifyException("密码错误");
        }

        //将获取到得信息存入redis   以   user:userid:info 形式
        redisTemplate.opsForValue().set("user:" + user.getId() + ":info", user, 1, TimeUnit.DAYS);
        //写入登入日志

        return JwtUtils.getJwtToken(user.getId(), user.getNickName());
    }

    @Override
    public void userRegister(RegistVo registVo) {
        //用户注册业务
        //非空验证
        if (StringUtils.isEmpty(registVo.getCode()) || StringUtils.isEmpty(registVo.getMobile()) || StringUtils.isEmpty(registVo.getPassword()) || StringUtils.isEmpty(registVo.getNickName()) || StringUtils.isEmpty(registVo.getEmail()) || StringUtils.isEmpty(registVo.getQq()))
            throw new DefaultException("参数异常");
        //将对象给
        YfkUser yfkUser = registVo;
        String code = registVo.getCode();
        registVo = null;
        String regCode = (String) redisTemplate.opsForValue().get("msm:code:" + yfkUser.getMobile());
        //一个验证码只验证一次
        redisTemplate.delete("msm:code:" + yfkUser.getMobile());
        if (!code.equals(regCode))
            throw new DefaultException("验证码错误或失效");
        QueryWrapper<YfkUser> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("mobile", yfkUser.getMobile());
        Integer integer = baseMapper.selectCount(objectQueryWrapper);
        if (integer > 0) throw new DefaultException("当前手机号以注册");
        //执行添加
        yfkUser.setPassword(MD5.encrypt(yfkUser.getPassword()));
        baseMapper.insert(yfkUser);

    }
}

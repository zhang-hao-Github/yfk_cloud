package com.sock.ucenterservice.controller;


import com.sock.common.utils.R;
import com.sock.common.entity.YfkUser;
import com.sock.ucenterservice.entity.vo.RegistVo;
import com.sock.ucenterservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@RequestMapping("/ucenter/user")
@Slf4j
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    public UserService yfkUserService;

    //登入
    @PostMapping("/login")
    public R loginUser(@RequestBody YfkUser yfkUser) {

        log.info("用户登入参数--{}", yfkUser);
        String token = yfkUserService.loginVerify(yfkUser);
        return R.ok().data("token", token);
    }

    //注册
    @PostMapping("/regist")
    public R registUser(@RequestBody RegistVo registVo) {
        log.info("用户注册参数--{}", registVo);
        yfkUserService.userRegister(registVo);
        return R.ok();
    }

}


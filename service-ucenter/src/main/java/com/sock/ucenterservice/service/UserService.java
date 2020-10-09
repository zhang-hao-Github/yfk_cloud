package com.sock.ucenterservice.service;

import com.sock.common.entity.YfkUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.ucenterservice.entity.vo.RegistVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
public interface UserService extends IService<YfkUser> {

    String loginVerify(YfkUser yfkUser);

    void userRegister(RegistVo registVo);

}

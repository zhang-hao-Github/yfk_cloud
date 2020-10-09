package com.sock.rbacservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.common.entity.YfkUser;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-02
 */
public interface YfkUserService extends IService<YfkUser> {

    List<YfkUser> findRoleAll();

    void allotRole(String userid, ArrayList<String> roleids);

    List findRoleByUserid(String userid);
}

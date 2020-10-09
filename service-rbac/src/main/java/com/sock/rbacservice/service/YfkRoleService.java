package com.sock.rbacservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.common.entity.YfkRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-02
 */
public interface YfkRoleService extends IService<YfkRole> {

    List<YfkRole> findRoleAll();

}

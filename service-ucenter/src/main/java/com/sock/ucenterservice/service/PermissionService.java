package com.sock.ucenterservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.common.entity.YfkPermission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */

public interface PermissionService extends IService<YfkPermission> {
    List<YfkPermission> findPermissionValue(String userid);


}

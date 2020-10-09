package com.sock.rbacservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.common.entity.YfkPermission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-01
 */
public interface YfkPermissionService extends IService<YfkPermission> {

    List<YfkPermission> queryAllmenu();

    void removeChildren(String id);

    void DoassignPermission(String roleID, List<String> permissionID);

    List<YfkPermission> queryPermissionIds(String roleid);

    List<YfkPermission> findMenuByuserid(String userId);
}

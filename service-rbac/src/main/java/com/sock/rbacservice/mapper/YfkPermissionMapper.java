package com.sock.rbacservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sock.common.entity.YfkPermission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-10-01
 */
public interface YfkPermissionMapper extends BaseMapper<YfkPermission> {

    List<YfkPermission> findMenuByuserid(String userId);

    List<YfkPermission> findMenuRoleid(String userId);

}

package com.sock.ucenterservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sock.common.entity.YfkPermission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
public interface PermissionMapper extends BaseMapper<YfkPermission> {

    List<YfkPermission> findPermissionValue(String userid);


}

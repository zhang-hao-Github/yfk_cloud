package com.sock.ucenterservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkPermission;
import com.sock.ucenterservice.mapper.PermissionMapper;
import com.sock.ucenterservice.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@Service("loginPermissionserive")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, YfkPermission> implements PermissionService {

    @Override
    public List<YfkPermission> findPermissionValue(String userid) {
        List<YfkPermission> permissionValue = baseMapper.findPermissionValue(userid);

        return permissionValue;
    }
}

package com.sock.rbacservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkRole;
import com.sock.rbacservice.mapper.YfkRoleMapper;
import com.sock.rbacservice.service.YfkRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-02
 */
@Service
public class YfkRoleServiceImpl extends ServiceImpl<YfkRoleMapper, YfkRole> implements YfkRoleService {

    @Override
    public List<YfkRole> findRoleAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        List<YfkRole> list = baseMapper.selectList(queryWrapper);

        return list;
    }
}

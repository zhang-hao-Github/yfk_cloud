package com.sock.rbacservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkUser;
import com.sock.common.entity.YfkUserRole;
import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.rbacservice.mapper.YfkUserMapper;
import com.sock.rbacservice.service.YfkUserRoleService;
import com.sock.rbacservice.service.YfkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-02
 */
@Service
public class YfkUserServiceImpl extends ServiceImpl<YfkUserMapper, YfkUser> implements YfkUserService {

    @Autowired
    private YfkUserRoleService yfkUserRoleService;

    //用户进行权限分配时所需要的权限数据
    @Override
    public List<YfkUser> findRoleAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        List<YfkUser> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    //分配角色
    @Override
    public void allotRole(String userid, ArrayList<String> roleids) {
        if (StringUtils.isEmpty(userid) || roleids.isEmpty()) {
            throw new DefaultException("参数异常");
        }
        ArrayList<YfkUserRole> userRoleArrayList = new ArrayList<>();
        for (String roleid : roleids) {
            userRoleArrayList.add(new YfkUserRole(userid, roleid));
        }
        yfkUserRoleService.saveBatch(userRoleArrayList);
    }

    //查询当前用户的权限id
    @Override
    public List findRoleByUserid(String userid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("role_id");
        queryWrapper.eq("user_id", userid);
        List list = yfkUserRoleService.list(queryWrapper);

        return list;
    }
}

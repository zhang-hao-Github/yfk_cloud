package com.sock.rbacservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkPermission;
import com.sock.common.entity.YfkRolePermission;
import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.common.utils.MenusUtils;
import com.sock.rbacservice.mapper.YfkPermissionMapper;
import com.sock.rbacservice.service.YfkPermissionService;
import com.sock.rbacservice.service.YfkRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-01
 */
@Service
@Slf4j
public class YfkPermissionServiceImpl extends ServiceImpl<YfkPermissionMapper, YfkPermission> implements YfkPermissionService {

    @Autowired
    private YfkRolePermissionService yfkRolePermissionService;
    @Autowired
    private RedisTemplate redisTemplate;


    //查询所有菜单
    public List<YfkPermission> queryAllmenu() {
        Object o = redisTemplate.opsForValue().get("menus:list");
        if (null == o) {
            QueryWrapper<YfkPermission> queryWrapper = new QueryWrapper<YfkPermission>();
            queryWrapper.orderByDesc("id");
            List<YfkPermission> yfkPermissions = baseMapper.selectList(queryWrapper);
            //reids中写入所有权限
            LinkedHashMap<String, String> pathAndRoleValue = new LinkedHashMap<>();
            for (YfkPermission yfkPermission : yfkPermissions) {
                if (!StringUtils.isEmpty(yfkPermission.getPath()) && !StringUtils.isEmpty(yfkPermission.getPermissionValue())) {
                    pathAndRoleValue.put(yfkPermission.getPath(), "JWT[" + yfkPermission.getPermissionValue() + "]");
                }
            }
            redisTemplate.opsForValue().set("permission:path:rolevalue", pathAndRoleValue);
            //log.info("总查询出数据{}---{}", yfkPermissions.size(), yfkPermissions);
            List<YfkPermission> menus = MenusUtils.BuildMenus(yfkPermissions);
            redisTemplate.opsForValue().set("menus:list", menus);
            return menus;
        }
        return (List<YfkPermission>) o;
    }

    //根据id删除节点及节点中的子节点
    public void removeChildren(String id) {

        ArrayList<String> ids = new ArrayList();
        ids.add(id);
        //递归查询出当前id下的节点id信息
        queryChildrenId(id, ids);

        baseMapper.deleteBatchIds(ids);
    }

    //分配权限
    @Override
    public void DoassignPermission(String roleID, List<String> permissionID) {
        if (StringUtils.isEmpty(roleID) || permissionID.isEmpty()) {
            throw new DefaultException("参数异常");
        }
        List<YfkRolePermission> list = new ArrayList<>();
        for (String s : permissionID) {
            list.add(new YfkRolePermission(roleID, s));
        }

        yfkRolePermissionService.saveBatch(list);
    }

    //  根据权限查询出当前角色的权限
    @Override
    public List<YfkPermission> queryPermissionIds(String roleid) {

        Object o = redisTemplate.opsForValue().get("permission:" + roleid);
        if (null == o) {

            List<YfkPermission> menuRoleid = baseMapper.findMenuRoleid(roleid);
            log.info("当前角色是{}---{}", roleid, menuRoleid);
            //存入角色对应的权限信息
            redisTemplate.opsForValue().set("permission:" + roleid, menuRoleid);
            return menuRoleid;
        }
        return (List<YfkPermission>) o;
    }

    @Override
    public List<YfkPermission> findMenuByuserid(String userId) {

        List<YfkPermission> menuByuserid = baseMapper.findMenuByuserid(userId);
        log.info("用户菜单数据{}", menuByuserid);
        YfkPermission yfkPermission = new YfkPermission();
        yfkPermission.setPid("0");
        yfkPermission.setId("1");
        menuByuserid.add(yfkPermission);
        List<YfkPermission> yfkPermissions = MenusUtils.BuildMenus(menuByuserid);
        log.info("用户菜单数据 递归后{}", yfkPermissions);
        return yfkPermissions;
    }


    //递归查询节点中所有的id
    private void queryChildrenId(String id, ArrayList<String> ids) {

        QueryWrapper<YfkPermission> queryWrapper = new QueryWrapper<YfkPermission>();
        queryWrapper.eq("pid", id);
        queryWrapper.select("id");
        List<YfkPermission> yfkPermissions = baseMapper.selectList(queryWrapper);
        yfkPermissions.stream().forEach(item -> {
            ids.add(item.getId());
            queryChildrenId(item.getId(), ids);
        });
    }

}

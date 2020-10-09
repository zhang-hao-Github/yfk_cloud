package com.sock.rbacservice.controller;


import com.sock.common.entity.YfkPermission;
import com.sock.common.entity.YfkUser;
import com.sock.common.utils.R;
import com.sock.rbacservice.service.YfkPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-01
 */
@RestController
@RequestMapping("/rbac/permission")
@CrossOrigin
@Slf4j
public class YfkPermissionController {

    @Autowired
    private YfkPermissionService yfkPermissionService;

    @GetMapping("/list")
    public R queryPermissionAll() {
        List<YfkPermission> menus = yfkPermissionService.queryAllmenu();
        return R.ok().data("permissionList", menus);
    }

    @GetMapping("/remove/{id}")
    public R removePermissionById(@PathVariable String id) {
        yfkPermissionService.removeChildren(id);

        return R.ok();
    }

    @PostMapping("/update")
    public R updatePermissionById(@RequestBody YfkPermission permission) {

        System.out.println(permission);
        boolean b = yfkPermissionService.saveOrUpdate(permission);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    //给角色分配权限
    @PostMapping("/assign")
    public R removePermissionById(@RequestBody Map map) {
        yfkPermissionService.DoassignPermission((String) map.get("roleid"), (ArrayList) map.get("permissionids"));

        return R.ok();
    }

    @GetMapping("/queryPermissionIds/{roleid}")
    public R queryPermissionIds(@PathVariable String roleid) {
        List<YfkPermission> permissionIds = yfkPermissionService.queryPermissionIds(roleid);

        return R.ok().data("permissionIds", permissionIds);
    }

    //根据用户id查询菜单  非按钮
    @GetMapping("/findMenu")
    public R findMenuByuserid() {
        YfkUser yfkUser = (YfkUser) SecurityUtils.getSubject().getPrincipal();
        List<YfkPermission> menuByuserid = yfkPermissionService.findMenuByuserid(yfkUser.getId());
        log.info("用户数据{}", yfkUser);
        return R.ok().data("usermenus", menuByuserid);
    }

}


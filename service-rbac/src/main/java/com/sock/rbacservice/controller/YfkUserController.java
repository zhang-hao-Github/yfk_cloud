package com.sock.rbacservice.controller;


import com.sock.common.entity.YfkUser;
import com.sock.common.utils.R;
import com.sock.rbacservice.service.YfkUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-02
 */
@RestController
@RequestMapping("/rbac/user")
@CrossOrigin
public class YfkUserController {

    @Autowired
    private YfkUserService yfkUserService;

    @GetMapping("/findUserList")
    public R findRoleList() {

        List<YfkUser> userList = yfkUserService.findRoleAll();
        return R.ok().data("userList", userList);
    }

    @GetMapping("/remove/{ids}")
    public R removeUserByid(@PathVariable List<String> ids) {
        System.out.println(ids);
        boolean b = yfkUserService.removeByIds(ids);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/update")
    public R removeUserByid(@RequestBody YfkUser user) {

        boolean b = yfkUserService.saveOrUpdate(user);
        if (b) {
            return R.ok();

        }
        return R.error();
    }

    //角色分配
    @PostMapping("/allot")
    public R roleAllot(@RequestBody Map map) {

        yfkUserService.allotRole((String) map.get("userid"), (ArrayList) map.get("roleids"));

        return R.ok();
    }

    //查询已有角色id
    @GetMapping("/allot/{userid}")
    public R findroleAllot(@PathVariable String userid) {

        List roleids = yfkUserService.findRoleByUserid(userid);

        return R.ok().data("roleids", roleids);
    }

    //查询当前用户登入信息
    @GetMapping("/userinfo")
    public R loginLog() {
        Object principal = SecurityUtils.getSubject().getPrincipal();

        return R.ok().data("userinfo", principal);
    }

}


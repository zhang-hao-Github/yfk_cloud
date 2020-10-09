package com.sock.rbacservice.controller;


import com.sock.common.entity.YfkRole;
import com.sock.common.utils.R;
import com.sock.rbacservice.service.YfkRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-02
 */
@RestController
@RequestMapping("/rbac/role")
@CrossOrigin
public class YfkRoleController {

    @Autowired
    private YfkRoleService yfkRoleService;

    @GetMapping("/findRoleList")
    public R findRoleList() {

        List<YfkRole> roleList = yfkRoleService.findRoleAll();

        return R.ok().data("roleList", roleList);
    }


    @GetMapping("/remove/{ids}")
    public R removeRoleByid(@PathVariable List<String> ids) {
        System.out.println(ids);
        boolean b = yfkRoleService.removeByIds(ids);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/update")
    public R removeRoleByid(@RequestBody YfkRole role) {

        boolean b = yfkRoleService.saveOrUpdate(role);
        if (b) {
            return R.ok();

        }
        return R.error();
    }


}


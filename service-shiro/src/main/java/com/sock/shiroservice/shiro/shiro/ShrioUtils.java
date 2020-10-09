package com.sock.shiroservice.shiro.shiro;

import com.sock.common.entity.YfkUser;
import org.apache.shiro.SecurityUtils;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-09 00:18
 **/
public class ShrioUtils {

    public static YfkUser getUserInfo() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return (YfkUser) principal;
    }

}

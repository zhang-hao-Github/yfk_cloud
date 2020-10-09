package com.sock.shiroservice.shiro.shiro;

import com.sock.common.entity.YfkPermission;
import com.sock.common.entity.YfkUser;
import com.sock.common.entity.YfkUserRole;
import com.sock.common.exceptionHandler.exception.LoginVerifyException;
import com.sock.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-04 23:23
 **/
@Slf4j
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private RedisTemplate redisTemplate;

    //权限认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //查询当前角色得 权限值
        YfkUser yfkUser = (YfkUser) principalCollection.getPrimaryPrincipal();
        log.info("权限验证用户信息--{}", yfkUser);
        List<YfkUserRole> userRole = yfkUser.getUserRole();
        //多id 取多次 如何做集合的合并
        List<YfkPermission> result = new ArrayList<>();
        for (YfkUserRole yfkUserRole : userRole) {
            result.addAll((List<YfkPermission>) redisTemplate.opsForValue().get("permission:" + yfkUserRole.getRoleId()));
        }
        HashSet<String> setPermissions = new HashSet<>();
        for (YfkPermission yfkPermission : result) {
            setPermissions.add(yfkPermission.getPermissionValue());
        }
        log.info("用户多角色去重权限结果{}", setPermissions);
        authorizationInfo.setStringPermissions(setPermissions);
        return authorizationInfo;
    }

    //登入认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String jwtToken = token.getUsername();
        Claims paramsByJwtToken = JwtUtils.getParamsByJwtToken(jwtToken);
        String id = (String) paramsByJwtToken.get("id");
        String password = (String) paramsByJwtToken.get("nickname");
        //jwt解析获取用户信息进行比对
        YfkUser yfkUser = (YfkUser) redisTemplate.opsForValue().get("user:" + id + ":info");
        if (yfkUser == null) {
            throw new LoginVerifyException("登入失败");
        }
        log.info("登入认证获取到的用户信息--{}", yfkUser);
        if (!yfkUser.getPassword().equals(password) && !yfkUser.getId().equals(id)) {
            throw new LoginVerifyException("签名验证失败");
        }

        return new SimpleAuthenticationInfo(yfkUser, token.getCredentials(), "JWTRealm");

    }
}

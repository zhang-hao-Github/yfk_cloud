package com.sock.shiroservice.shiro.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-04 23:17
 **/
@Configuration
@Slf4j
public class ShiroConfig {


    @Autowired
    private RedisTemplate redisTemplate;


    @Bean("jwtRealm")
    public JWTRealm jwtRealm() {
        return new JWTRealm();
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(JWTRealm jwtRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(jwtRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        //加入自定义的过滤器
        filters.put("JWT", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Object o = redisTemplate.opsForValue().get("permission:path:rolevalue");
        LinkedHashMap<String, String> filterChainMap = new LinkedHashMap<>();
        if (null != o) {
            filterChainMap = (LinkedHashMap<String, String>) o;
        }
        log.info("redis中获取的权限信息", redisTemplate.opsForValue().get("permission:path:rolevalue"));
        filterChainMap.put("/**", "JWT");
        log.info("权限信息{}", filterChainMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

}
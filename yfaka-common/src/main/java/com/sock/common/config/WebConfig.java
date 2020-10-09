package com.sock.common.config;

import com.sock.common.intercept.LoginVerify;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-02 14:52
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginVerify()).excludePathPatterns("yfkucenter/ucenter/login");
    }
}

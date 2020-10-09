package com.sock.payservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 16:56
 **/
@Configuration
public class PayConfig implements WebMvcConfigurer {


    @Bean
    public ALiPayConfig aLiPayConfig() {

        return new ALiPayConfig();
    }
}

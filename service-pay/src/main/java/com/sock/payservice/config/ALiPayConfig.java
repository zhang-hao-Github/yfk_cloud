package com.sock.payservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 16:20
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "ypay.alipy")
public class ALiPayConfig {

    //aapid
    private String APPID;
    //私钥
    private String APP_PRIVATE_KEY;
    //公钥
    private String APP_PUBLIC_KEY;

    private String URl;
}

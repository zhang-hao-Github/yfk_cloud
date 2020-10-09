package com.sock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-27 15:50
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动加载
public class MSMApplication {

    public static void main(String[] args) {
        SpringApplication.run(MSMApplication.class, args);
    }
}

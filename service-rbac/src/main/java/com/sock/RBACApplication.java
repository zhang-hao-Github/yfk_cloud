package com.sock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-01 22:41
 **/
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.sock.rbacservice.mapper")
public class RBACApplication {

    public static void main(String[] args) {
        SpringApplication.run(RBACApplication.class, args);
    }
}

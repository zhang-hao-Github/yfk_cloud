package com.sock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-04 16:26
 **/
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.sock.goodsservice.mapper")
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}

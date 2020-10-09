package com.sock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-27 21:51
 **/
@SpringBootApplication
@MapperScan("com.sock.ucenterservice.mapper")
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class, args);
    }

}

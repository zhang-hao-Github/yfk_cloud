package com.sock.common.exceptionHandler.exception;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-02 14:47
 **/
//验证登入的拦截器
public class LoginVerifyException extends RuntimeException {

    public LoginVerifyException(String msg) {
        super(msg);
    }
}

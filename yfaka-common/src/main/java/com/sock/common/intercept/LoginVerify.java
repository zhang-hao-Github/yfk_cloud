package com.sock.common.intercept;

import com.sock.common.exceptionHandler.exception.LoginVerifyException;
import com.sock.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-02 14:43
 **/
@Slf4j
public class LoginVerify implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求{}被拦截", request.getRequestURL());
        boolean b = JwtUtils.checkToken(request);
        if (!b) {
            throw new LoginVerifyException("token无效或以过期--");
        }
        return true;
    }
}

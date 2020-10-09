package com.sock.shiroservice.shiro.shiro;

import com.sock.common.exceptionHandler.exception.LoginVerifyException;
import com.sock.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-05 00:07
 **/
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        log.info("进行判断请求头是否有token{}", token);
        return JwtUtils.checkToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //有token 登入
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                //权限验证
                log.info("当前path所需要的权限{}", mappedValue);
                String[] roleValueArray = (String[]) mappedValue;
                //有进行permission填值才进入
                //权限验证码
                if (null != mappedValue) {
                    for (String s : roleValueArray) {
                        getSubject(request, response).checkPermission(s);
                    }
                }
            } catch (UnauthorizedException e) {
                throw new LoginVerifyException("没有权限");
            } catch (Exception e) {
                e.printStackTrace();
                throw new LoginVerifyException("登入失败");
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwtToken = req.getHeader("token");

        UsernamePasswordToken token = new UsernamePasswordToken(jwtToken, jwtToken);
        getSubject(request, response).login(token);
        return true;
    }

}

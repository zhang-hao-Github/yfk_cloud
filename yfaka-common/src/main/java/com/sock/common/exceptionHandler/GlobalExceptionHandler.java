package com.sock.common.exceptionHandler;

import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.common.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-28 23:57
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }


    @ExceptionHandler(DefaultException.class)
    @ResponseBody
    public R defaultError(DefaultException e) {
        return R.error().message(e.getMessage());
    }
}

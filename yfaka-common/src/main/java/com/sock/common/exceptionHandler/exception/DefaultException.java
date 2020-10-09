package com.sock.common.exceptionHandler.exception;

import lombok.Data;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-29 00:41
 **/
@Data
public class DefaultException extends RuntimeException {

    public DefaultException(String msg) {
        super(msg);
    }


}

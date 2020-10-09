package com.sock.msmservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-27 15:55
 **/
public interface MSMService {

    boolean send(String phone, HashMap<String, Object> params);

}

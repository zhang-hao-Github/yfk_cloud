package com.sock.payservice.service;

import com.sock.payservice.entity.PayParams;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 16:15
 **/
public interface PayService {

    //支付宝当面付
    String alipay(PayParams payParams);

    String alipayPoll(String out_trade_no);

    //微信支付
    boolean weixinpay();
}

package com.sock.payservice.controller;

import com.sock.common.utils.R;
import com.sock.payservice.entity.PayParams;
import com.sock.payservice.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 15:50
 **/
@RestController
public class PayController {

    @Autowired
    private PayService payService;

    //支付方法
    @PostMapping("/pay")
    public R aliPayQrCode(@RequestBody PayParams payParams) {
        System.out.println(payParams);
        String qrCode = null;

        switch (Integer.parseInt(payParams.getPay_method())) {
            case 1:
                //支付宝支付当面付
                qrCode = payService.alipay(payParams);
                break;
            case 2:
                //微信支付

                break;
        }
        return R.ok().data("qrCode", qrCode);
    }

    //轮询方法
    @GetMapping("/payPoll/{out_trade_no}")
    public R aliPayPollOrderQuery(@PathVariable String out_trade_no) {

        String tradestatus = payService.alipayPoll(out_trade_no);

        return R.ok().data("tradestatus", tradestatus);
    }


}

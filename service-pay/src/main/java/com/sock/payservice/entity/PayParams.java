package com.sock.payservice.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 22:32
 **/
@Data
@ToString
public class PayParams {

    private String pay_method; //支付方式
    private String out_trade_no; //订单号
    private String subject;  //标题
    private String total_amount;//订单金额

}

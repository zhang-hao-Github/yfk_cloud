package com.sock.payservice.service.imp;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.payservice.config.ALiPayConfig;
import com.sock.payservice.entity.PayParams;
import com.sock.payservice.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 16:15
 **/
@Service
@Slf4j
public class PayServiceimp implements PayService {

    private final static String TIMEOUT_EXPRESS = "90m";//交易时间90秒
    private final static long TIME_OUT = 60 * 1000;//交易时间60秒

    @Autowired
    private ALiPayConfig aLiPayConfig;

    public String alipay(PayParams payParams) {

        AlipayClient alipayClient = new DefaultAlipayClient
                (aLiPayConfig.getURl(), aLiPayConfig.getAPPID(),
                        aLiPayConfig.getAPP_PRIVATE_KEY(), "json", "UTF-8", aLiPayConfig.getAPP_PUBLIC_KEY(), "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
//        request.setNotifyUrl("https://opendocs.alipay.com/open/194/106039");
//        request.setReturnUrl("https://opendocs.alipay.com/open/194/106039");
        //判断订单号有没有
        if (StringUtils.isEmpty(payParams.getOut_trade_no()) || StringUtils.isEmpty(payParams.getSubject()) || StringUtils.isEmpty(payParams.getTotal_amount()))
            throw new DefaultException("参数错误,缺少参数申请支付二维码失败");


        JSONObject params = JSONUtil.createObj();
        params.set("out_trade_no", payParams.getOut_trade_no());
        params.set("total_amount", payParams.getTotal_amount());
        params.set("subject", payParams.getSubject());
//        params.set("goods_id", "product-01");//商品编号
//        params.set("goods_name", "product-name");
//        params.set("quantity", "1");
//        params.set("price", "0.01");
//        params.set("amount", "0.01");
        params.set("timeout_express", TIMEOUT_EXPRESS);//交易时间90秒
        //填参数
        request.setBizContent(params.toString());
        try {
            AlipayTradePrecreateResponse alipayTradePrecreateResponse = alipayClient.execute(request);
            String qrCode = alipayTradePrecreateResponse.getQrCode();
            String outTradeNo = alipayTradePrecreateResponse.getOutTradeNo();
            log.info("获取到的二维码json{}和返回的交易编号{}", qrCode, outTradeNo);
            log.info("获取到的所有数据是{}", alipayTradePrecreateResponse.getBody());

            return qrCode; //返回二维码地址
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    //支付宝支付轮询
    public String alipayPoll(String out_trade_no) {

        //进入方法 给进入方法赋值
        long firstTime = System.currentTimeMillis();

        AlipayClient alipayClient = new DefaultAlipayClient
                (aLiPayConfig.getURl(), aLiPayConfig.getAPPID(),
                        aLiPayConfig.getAPP_PRIVATE_KEY(), "json", "UTF-8", aLiPayConfig.getAPP_PUBLIC_KEY(), "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //填参数
        JSONObject params = JSONUtil.createObj();
        params.set("out_trade_no", out_trade_no);
        //        params.set("", "");
        //填参数

        request.setBizContent(params.toString());
        try {
            String tradeStatus = null;
            while ((System.currentTimeMillis() - firstTime) < TIME_OUT) {
                AlipayTradeQueryResponse execute = alipayClient.execute(request);
                String body = execute.getBody();
                log.info("查询订单获取到的数据有{}", body);
                if ("TRADE_SUCCESS".equals(execute.getTradeStatus())) {
                    log.info("用户支付成功{}", execute.getTradeNo());
                    tradeStatus = execute.getTradeStatus();
                    break;
                    //进行业务执行
                }
                //线程睡眠3秒
                Thread.sleep(5000);
            }

            return tradeStatus;
        } catch (AlipayApiException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean weixinpay() {
        return false;
    }
}

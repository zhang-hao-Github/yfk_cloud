package com.sock.goodsservice.entity;

import com.sock.common.entity.YfkGoods;
import com.sock.common.entity.YfkGoodsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-08 17:13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo implements Serializable {
    private Long size;
    private int current;
    private YfkGoodsType goodstype;
    private YfkGoods goods;
}

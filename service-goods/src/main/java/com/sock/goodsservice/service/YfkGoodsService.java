package com.sock.goodsservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.common.entity.YfkGoods;
import com.sock.goodsservice.entity.PageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
public interface YfkGoodsService extends IService<YfkGoods> {

    void saveGoods(YfkGoods goods);

    IPage byPage(PageVo pageVo);
}

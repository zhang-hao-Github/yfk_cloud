package com.sock.goodsservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sock.common.entity.YfkGoodsType;
import com.sock.goodsservice.entity.PageVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
public interface YfkGoodsTypeService extends IService<YfkGoodsType> {

    IPage byPage(PageVo pageVo);
}

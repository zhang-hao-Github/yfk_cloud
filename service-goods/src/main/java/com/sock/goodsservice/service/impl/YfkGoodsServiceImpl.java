package com.sock.goodsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkGoods;
import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.goodsservice.entity.PageVo;
import com.sock.goodsservice.mapper.YfkGoodsMapper;
import com.sock.goodsservice.service.YfkGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@Service
public class YfkGoodsServiceImpl extends ServiceImpl<YfkGoodsMapper, YfkGoods> implements YfkGoodsService {

    @Override
    public void saveGoods(YfkGoods goods) {
        //判断
        if (StringUtils.isEmpty(goods.getGoodsTypeId()) || StringUtils.isEmpty(goods.getGoodsName()) || StringUtils.isEmpty(goods.getPrice())) {
            throw new DefaultException("参数异常");
        }
        //做金额的转换   分 计算 出的时候/100
        boolean save = this.save(goods);

    }

    @Override
    public IPage byPage(PageVo pageVo) {
        if (null == pageVo.getSize() || pageVo.getCurrent() == 0) {
            throw new DefaultException("异常参数");
        }
        IPage iPage = new Page();
        //size 显示条数
        iPage.setSize(pageVo.getSize());
        //current 当前页
        iPage.setCurrent(pageVo.getCurrent());

        QueryWrapper<YfkGoods> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(pageVo.getGoods().getGoodsName())) {
            queryWrapper.eq("goods_name", pageVo.getGoods().getGoodsName());
        }
        if (!StringUtils.isEmpty(pageVo.getGoods().getGoodsTypeId())) {
            queryWrapper.eq("goods_type_id", pageVo.getGoods().getGoodsTypeId());
        }
        IPage page = this.page(iPage, queryWrapper);


        return page;
    }
}

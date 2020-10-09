package com.sock.goodsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sock.common.entity.YfkGoodsType;
import com.sock.common.entity.YfkUser;
import com.sock.common.exceptionHandler.exception.DefaultException;
import com.sock.goodsservice.entity.PageVo;
import com.sock.goodsservice.mapper.YfkGoodsTypeMapper;
import com.sock.goodsservice.service.YfkGoodsTypeService;
import org.apache.shiro.SecurityUtils;
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
public class YfkGoodsTypeServiceImpl extends ServiceImpl<YfkGoodsTypeMapper, YfkGoodsType> implements YfkGoodsTypeService {

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
        QueryWrapper<YfkGoodsType> queryWrapper = new QueryWrapper();
        YfkUser principal = (YfkUser) SecurityUtils.getSubject().getPrincipal();
        queryWrapper.eq("user_id", principal.getId());
        if (!StringUtils.isEmpty(pageVo.getGoodstype().getTypeName())) {
            queryWrapper.eq("type_name", pageVo.getGoodstype().getTypeName());
        }
        if (!StringUtils.isEmpty(pageVo.getGoodstype().getId())) {
            queryWrapper.eq("id", pageVo.getGoodstype().getId());
        }
        IPage iPage1 = this.page(iPage, queryWrapper);
        return iPage1;
    }
}

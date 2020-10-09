package com.sock.goodsservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sock.common.entity.YfkGoodsType;
import com.sock.common.entity.YfkUser;
import com.sock.common.utils.R;
import com.sock.goodsservice.entity.PageVo;
import com.sock.goodsservice.service.YfkGoodsTypeService;
import com.sock.shiroservice.shiro.shiro.ShrioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/type")
@Slf4j
public class YfkGoodsTypeController {

    @Autowired
    private YfkGoodsTypeService yfkGoodsTypeService;

    //商品类型的添加添加
    @PostMapping("/edit")
    public R goodsAdd(@RequestBody YfkGoodsType yfkGoodsType) {
        //获取当前用户id进行赋
        YfkUser userInfo = ShrioUtils.getUserInfo();
        log.info("当前登入的用户编号{}", userInfo.getId());
        yfkGoodsType.setUserId(userInfo.getId());
        yfkGoodsTypeService.saveOrUpdate(yfkGoodsType);
        return R.ok();
    }

    @PostMapping("/remove")
    public R goodsRemove() {

        return R.ok();
    }

    @PostMapping("/pages")
    public R findGoodsTypeListpage(@RequestBody PageVo pageVo) {
        log.info("goodtpye获取到参数{}", pageVo);
        IPage list = yfkGoodsTypeService.byPage(pageVo);
        return R.ok().data("pages", list);
    }

    @GetMapping("/list")
    public R findGoodsTypeList() {
        YfkUser userInfo = ShrioUtils.getUserInfo();
        log.info("当前登入的用户编号{}", userInfo.getId());
        QueryWrapper<YfkGoodsType> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userInfo.getId());
        queryWrapper.eq("enabled", 1);
        List<YfkGoodsType> list = yfkGoodsTypeService.list(queryWrapper);
        return R.ok().data("goodTypeList", list);
    }

}


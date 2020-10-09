package com.sock.goodsservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sock.common.entity.YfkGoods;
import com.sock.common.utils.R;
import com.sock.goodsservice.entity.PageVo;
import com.sock.goodsservice.service.YfkGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/goods/goods")
@Slf4j
public class YfkGoodsController {

    @Autowired
    private YfkGoodsService yfkGoodsService;


    @PostMapping("/add")
    public R addGoods(@RequestBody YfkGoods goods) {
        log.info("用户上传数据{}", goods);
        yfkGoodsService.saveGoods(goods);

        return R.ok();
    }

    @PostMapping("/pages")
    public R findGoodsListpage(@RequestBody PageVo pageVo) {
        log.info("goods获取到参数{}", pageVo);
        IPage list = yfkGoodsService.byPage(pageVo);

        return R.ok().data("pages", list);
    }


}


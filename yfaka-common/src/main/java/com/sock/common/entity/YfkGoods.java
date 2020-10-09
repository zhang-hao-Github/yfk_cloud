package com.sock.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sock.common.exceptionHandler.exception.DefaultException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "YfkGoods对象", description = "")
public class YfkGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品的单价")
    private Integer price;

    //perice 转换成分
    public void setPrice(double price) {
        if (StringUtils.isEmpty(price)) {
            throw new DefaultException("价格输入错误");
        }
        this.price = new Double(price * 100).intValue();
    }

    @ApiModelProperty(value = "商品描述")
    private String goodsDescription;

    @ApiModelProperty(value = "使用说明")
    private String useDescription;

    @ApiModelProperty(value = "上下架")
    private Integer enabled;

    @ApiModelProperty(value = "库储预警值")
    private Integer inventoryWarn;

    @ApiModelProperty(value = "最大购买限度")
    private Integer buymax;

    @ApiModelProperty(value = "最小购买限度")
    private Integer buymin;

    @ApiModelProperty(value = "商品展示排序")
    private Integer sort;

    //    good_type_id
    @ApiModelProperty(value = "商品所属类型id")
    private Integer goodsTypeId;

}

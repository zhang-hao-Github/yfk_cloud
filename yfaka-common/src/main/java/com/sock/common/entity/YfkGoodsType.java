package com.sock.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="YfkGoodsType对象", description="")
public class YfkGoodsType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品分类id")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品分类名称")
    private String typeName;

    @ApiModelProperty(value = "商品列表二级联动时显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "商品列表是否启用")
    private Integer enabled;

    @ApiModelProperty(value = "商品列表类型所属用户")
    private String userId;


}

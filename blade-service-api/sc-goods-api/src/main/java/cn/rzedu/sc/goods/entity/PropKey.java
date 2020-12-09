package cn.rzedu.sc.goods.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@TableName("sc_prop_key")
@ApiModel(value = "PropKey对象", description = "属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性")
public class PropKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品类目  sc_category.id
     */
    @ApiModelProperty(value = "商品类目  sc_category.id")
    private Integer categoryId;
    /**
     * 属性名称
     */
    @ApiModelProperty(value = "属性名称")
    private String name;
    /**
     * 是否SKU属性
     */
    @ApiModelProperty(value = "是否SKU属性")
    private Boolean isSku;
    /**
     * 排序次序
     */
    @ApiModelProperty(value = "排序次序")
    private Integer listOrder;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyDate;
    /**
     * 删除标识
     */
    @TableLogic
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}

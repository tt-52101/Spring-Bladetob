package cn.rzedu.sc.goods.entity;

import java.math.BigDecimal;

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
 * 团购的商品。某团购团购买的商品项目实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@TableName("sc_groupon_group_goods")
@ApiModel(value = "GrouponGroupGoods对象", description = "团购的商品。某团购团购买的商品项目")
public class GrouponGroupGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 参加的团   groupon_group.id
     */
    @ApiModelProperty(value = "参加的团   groupon_group.id")
    private Integer grouponGroupId;
    /**
     * 购买的商品   goods.id
     */
    @ApiModelProperty(value = "购买的商品   goods.id")
    private Integer goodsId;
    /**
     * 购买的商品规格SKU   goods_sku.id
     */
    @ApiModelProperty(value = "购买的商品规格SKU   goods_sku.id")
    private Integer goodsSkuId;
    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer count;
    /**
     * 购买价格
     */
    @ApiModelProperty(value = "购买价格")
    private BigDecimal price;
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

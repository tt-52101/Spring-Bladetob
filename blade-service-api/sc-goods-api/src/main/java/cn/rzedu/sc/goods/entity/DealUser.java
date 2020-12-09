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
 * 用户-商品交易记录表。以用户和商品为单位的成交流水明细
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_deal_user")
@ApiModel(value = "DealUser对象", description = " 用户-商品交易记录表。以用户和商品为单位的成交流水明细 ")
public class DealUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 订单记录ID   order.id
     */
    @ApiModelProperty(value = "订单记录ID   order.id")
    private Integer orderId;
    /**
     * 商品记录ID   goods.id
     */
    @ApiModelProperty(value = "商品记录ID   goods.id")
    private Integer goodsId;
    /**
     * 商品规格（暂无）
     */
    @ApiModelProperty(value = "商品规格（暂无）")
    private Integer goodsSkuId;
    /**
     * 支付价格
     */
    @ApiModelProperty(value = "支付价格")
    private BigDecimal dealPrice;
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

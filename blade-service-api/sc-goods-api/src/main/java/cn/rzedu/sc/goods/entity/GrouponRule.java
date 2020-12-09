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
 * 团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_groupon_rule")
@ApiModel(value = "GrouponRule对象", description = "团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的 ")
public class GrouponRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;
    /**
     * 团购名称
     */
    @ApiModelProperty(value = "团购名称")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal primePrice;
    /**
     * 团购价
     */
    @ApiModelProperty(value = "团购价")
    private BigDecimal groupPrice;
    /**
     * 成团人数
     */
    @ApiModelProperty(value = "成团人数")
    private Integer memberCount;
    /**
     * 有效期，团购从发起到结束的时间，单位：小时
     */
    @ApiModelProperty(value = "有效期，团购从发起到结束的时间，单位：小时")
    private Integer duration;
    /**
     * 团购营销属性，JSON格式
     */
    @ApiModelProperty(value = "团购营销属性，JSON格式")
    private String attributes;
    /**
     * 团购营销相关文件，JSON格式
     */
    @ApiModelProperty(value = "团购营销相关文件，JSON格式")
    private String files;
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

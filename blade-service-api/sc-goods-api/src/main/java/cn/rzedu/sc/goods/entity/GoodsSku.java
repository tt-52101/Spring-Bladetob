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
 * 商品库存表，以一个SKU为记录单位实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@TableName("sc_goods_sku")
@ApiModel(value = "GoodsSku对象", description = "商品库存表，以一个SKU为记录单位")
public class GoodsSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的商品  sc_goods.id
     */
    @ApiModelProperty(value = "对应的商品  sc_goods.id")
    private Integer goodsId;
    /**
     * key-value对的字符串内容   {"内存":"32G","颜色":"银色"}
     */
    @ApiModelProperty(value = "key-value对的字符串内容")
    private String name;
    /**
     * 一个SKU的所有属性的key-value，用字符串方式表示，格式为：{key:value{,key:value,…}} key=prop_key.id，value=prop_value.id
     */
    @ApiModelProperty(value = "一个SKU的所有属性的key-value，用字符串方式表示，格式为：{key:value{,key:value,…}} key=prop_key" +
            ".id，value=prop_value.id")
    private String keyValue;
    /**
     * 商品在原实体表的类型   1=通用课程  2=软/硬笔
     */
    @ApiModelProperty(value = "商品在原实体表的类型   1=通用课程  2=软/硬笔")
    private Integer ownerType;
    /**
     * 商品在原实体数据表的id
     */
    @ApiModelProperty(value = "商品在原实体数据表的id")
    private Integer ownerId;
    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer stockCount;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    /**
     * 状态  0=下架 1=上架
     */
    @ApiModelProperty(value = "状态  0=下架 1=上架")
    private Integer status;
    /**
     * 卖出数量
     */
    @ApiModelProperty(value = "卖出数量")
    private Integer soldCount;
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

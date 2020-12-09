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
 * 商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_goods")
@ApiModel(value = "Goods对象", description = "商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位 ")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;
    /**
     * 商品编码，客户自己编码，但系统唯一的，不与其他商品重复
     */
    @ApiModelProperty(value = "商品编码，客户自己编码，但系统唯一的，不与其他商品重复")
    private String code;
    /**
     * 商品在原实体表的类型    1=通用课程  2=软/硬笔
     */
    @ApiModelProperty(value = "商品在原实体表的类型   1=通用课程  2=软/硬笔")
    private Integer ownerType;
    /**
     * 商品在原实体数据表的id（如有）
     */
    @ApiModelProperty(value = "商品在原实体数据表的id（如有）")
    private Integer ownerId;
    /**
     * 商品分类  sc_category.id
     */
    @ApiModelProperty(value = "商品分类  sc_category.id")
    private Integer categoryId;
    /**
     * 品牌   sc_brand.id
     */
    @ApiModelProperty(value = "品牌   sc_brand.id")
    private Integer brandId;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 商品主图，uuid
     */
    @ApiModelProperty(value = "商品主图，uuid")
    private String image;
    /**
     * 商品状态  0=下架  1=上架
     */
    @ApiModelProperty(value = "商品状态  0=下架  1=上架")
    private Integer status;
    /**
     * 是否包含sku
     */
    @ApiModelProperty(value = "是否包含sku")
    private Boolean hasSku;
    /**
     * 一个商品各个SKU库存的总和，如果商品不需要SKU描述表则这个就是商品的库存
     */
    @ApiModelProperty(value = "一个商品各个SKU库存的总和，如果商品不需要SKU描述表则这个就是商品的库存")
    private Integer totalStock;
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

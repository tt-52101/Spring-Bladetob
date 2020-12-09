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
 * 商品分类。商城所有商品分类，树状结构，从一级节点开始实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_category")
@ApiModel(value = "Category对象", description = "商品分类。商城所有商品分类，树状结构，从一级节点开始")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父节点，自关联
     */
    @ApiModelProperty(value = "父节点，自关联")
    private Integer parentId;
    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String name;
    /**
     * 分类主图
     */
    @ApiModelProperty(value = "分类主图")
    private String image;
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

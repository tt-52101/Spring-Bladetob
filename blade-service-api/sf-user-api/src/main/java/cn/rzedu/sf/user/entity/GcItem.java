package cn.rzedu.sf.user.entity;

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
 * 基础代码实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_gc_item")
@ApiModel(value = "GcItem对象", description = "基础代码")
public class GcItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 基础代码分类
     */
    @ApiModelProperty(value = "基础代码分类")
    private String code;
    /**
     * 基础代码分类名称
     */
    @ApiModelProperty(value = "基础代码分类名称")
    private String codeName;
    /**
     * 代码项名称
     */
    @ApiModelProperty(value = "代码项名称")
    private String name;
    /**
     * 代码项值
     */
    @ApiModelProperty(value = "代码项值")
    private String value;
    /**
     * 排列次序
     */
    @ApiModelProperty(value = "排列次序")
    private Integer sortOrder;
    /**
     * 层级关系
     */
    @ApiModelProperty(value = "层级关系")
    private Integer level;
    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用")
    private Integer disable;
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


}

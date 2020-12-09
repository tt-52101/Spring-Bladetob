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
 * 属性值。说明属性项有多少个可以使用的值实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@TableName("sc_prop_value")
@ApiModel(value = "PropValue对象", description = "属性值。说明属性项有多少个可以使用的值")
public class PropValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 属性项   sc_prop_key.id
     */
    @ApiModelProperty(value = "属性项   sc_prop_key.id")
    private Integer propKeyId;
    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String value;
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

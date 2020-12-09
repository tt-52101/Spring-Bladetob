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
 * 商城埋点-操作人员实体类
 *
 * @author Blade
 * @since 2020-12-02
 */
@Data
@TableName("sc_buried_point_user")
@ApiModel(value = "BuriedPointUser对象", description = "商城埋点-操作人员")
public class BuriedPointUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 埋点id
     */
    @ApiModelProperty(value = "埋点id")
    private Integer buriedPointId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
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

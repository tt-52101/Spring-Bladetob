package cn.rzedu.sf.user.entity;

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
 * 活动奖品实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@TableName("sf_activity_award")
@ApiModel(value = "ActivityAward对象", description = "活动奖品")
public class ActivityAward implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 奖品类型  1=礼包A  2=礼包B
     */
    @ApiModelProperty(value = "奖品类型  1=礼包A  2=礼包B")
    private Integer type;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private Integer totalNumber;
    /**
     * 剩余数量
     */
    @ApiModelProperty(value = "剩余数量")
    private Integer leftNumber;
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

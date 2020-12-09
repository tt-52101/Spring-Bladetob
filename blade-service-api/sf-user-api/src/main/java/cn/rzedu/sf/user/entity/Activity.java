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
 * 活动实体类
 *
 * @author Blade
 * @since 2020-09-18
 */
@Data
@TableName("sf_activity")
@ApiModel(value = "Activity对象", description = "活动")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String name;
    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型")
    private Integer type;
    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;
    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;
    /**
     * 活动地点
     */
    @ApiModelProperty(value = "活动地点")
    private String place;
    /**
     * 说明，相关事项
     */
    @ApiModelProperty(value = "说明，相关事项")
    private String comment;
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

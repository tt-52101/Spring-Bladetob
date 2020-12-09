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
 * 用户助力表实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@TableName("sf_activity_user_assist")
@ApiModel(value = "ActivityUserAssist对象", description = "用户助力表")
public class ActivityUserAssist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发起人用户id
     */
    @ApiModelProperty(value = "发起人用户id")
    private Integer initiatorId;
    /**
     * 发起人用户uuid
     */
    @ApiModelProperty(value = "发起人用户uuid")
    private String initiatorUuid;
    /**
     * 助力人用户id
     */
    @ApiModelProperty(value = "助力人用户id")
    private Integer assistantId;
    /**
     * 助力人用户uuid
     */
    @ApiModelProperty(value = "助力人用户uuid")
    private String assistantUuid;
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

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
 * 团购成团的成员。说明某个参团的成员，是groupon_group表的子表
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_groupon_group_user")
@ApiModel(value = "GrouponGroupUser对象", description = "团购成团的成员。说明某个参团的成员，是groupon_group表的子表 ")
public class GrouponGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 参加的团   groupon_group.id
     */
    @ApiModelProperty(value = "参加的团   groupon_group.id")
    private Integer grouponGroupId;
    /**
     * 参团人UserID
     */
    @ApiModelProperty(value = "参团人UserID")
    private Integer userId;
    /**
     * 创建时间（参团时间）
     */
    @ApiModelProperty(value = "创建时间（参团时间）")
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

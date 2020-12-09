package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.GrouponGroupUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 团购成团的成员。说明某个参团的成员，是groupon_group表的子表
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GrouponGroupUserVO对象", description = "团购成团的成员。说明某个参团的成员，是groupon_group表的子表 ")
public class GrouponGroupUserVO extends GrouponGroupUser {
    private static final long serialVersionUID = 1L;

    /**
     * 参团人昵称
     */
    @ApiModelProperty(value = "参团人昵称")
    private String name;
    /**
     * 参团人头像
     */
    @ApiModelProperty(value = "参团人头像")
    private String icon;
}

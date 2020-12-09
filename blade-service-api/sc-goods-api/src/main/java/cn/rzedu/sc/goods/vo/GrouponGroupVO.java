package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.GrouponGroup;
import cn.rzedu.sc.goods.entity.GrouponGroupGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 团购成团记录。针对某个团购的形成的一个团的总体情况
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GrouponGroupVO对象", description = "团购成团记录。针对某个团购的形成的一个团的总体情况 ")
public class GrouponGroupVO extends GrouponGroup {
    private static final long serialVersionUID = 1L;

    /**
     * 发起人昵称
     */
    @ApiModelProperty(value = "发起人昵称")
    private String launchUserName;
    /**
     * 发起人头像
     */
    @ApiModelProperty(value = "发起人头像")
    private String launchUserIcon;

    /**
     * 参团人
     */
    @ApiModelProperty(value = "参团人")
    List<GrouponGroupUserVO> groupUserList;

    /**
     * 参团用户ids，逗号隔开
     */
    @ApiModelProperty(value = "参团用户ids，逗号隔开")
    private String groupUserIds;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品在原实体表的类型
     */
    @ApiModelProperty(value = "商品在原实体表的类型")
    private Integer ownerType;

    /**
     * 参团商品sku
     */
    @ApiModelProperty(value = "参团商品sku")
    List<GrouponGroupGoodsVO> groupGoodsList;

    /**
     * 成团需要的总人数
     */
    @ApiModelProperty(value = "成团需要的总人数")
    private Integer needMemberCount;
}

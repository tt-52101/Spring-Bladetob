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
 * 团购成团记录。针对某个团购的形成的一个团的总体情况
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_groupon_group")
@ApiModel(value = "GrouponGroup对象", description = "团购成团记录。针对某个团购的形成的一个团的总体情况 ")
public class GrouponGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 成团规程ID  groupon_rule.id
     */
    @ApiModelProperty(value = "成团规程ID  groupon_rule.id")
    private Integer grouponId;
    /**
     * 发起人UserID
     */
    @ApiModelProperty(value = "发起人UserID")
    private Integer launchUserId;
    /**
     * 发起时间
     */
    @ApiModelProperty(value = "发起时间")
    private LocalDateTime launchTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime finishTime;
    /**
     * 本团已参团人数
     */
    @ApiModelProperty(value = "本团已参团人数")
    private Integer memberCount;
    /**
     * 状态   0=拼团中  1=成团完成  2=取消
     */
    @ApiModelProperty(value = "状态   0=拼团中  1=成团完成  2=取消")
    private Integer status;
    /**
     * 是否机器团（虚拟团）
     */
    @ApiModelProperty(value = "是否机器团（虚拟团）")
    private Integer isRobot;
    /**
     * 拼团来源  1=公众号链接拼团；2=H5我的课堂拼团；3=菜单栏入口拼团
     */
    @ApiModelProperty(value = "拼团来源  1=公众号链接拼团；2=H5我的课堂拼团；3=菜单栏入口拼团")
    private Integer source;
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

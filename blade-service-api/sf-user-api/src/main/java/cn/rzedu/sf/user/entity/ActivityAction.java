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
 * 活动行为表实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@TableName("sf_activity_action")
@ApiModel(value = "ActivityAction对象", description = "活动行为表")
public class ActivityAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 行为类型  1=千字文课程消息模板  2=诗词课程消息模板  3=领取礼包消息模板  4=公众号发出活动海报  5=用户提交奖品地址  6=用户取消关注
     */
    @ApiModelProperty(value = "行为类型  1=千字文课程消息模板  2=诗词课程消息模板  3=领取礼包消息模板  4=公众号发出活动海报  5=用户提交奖品地址  6=用户取消关注")
    private Integer type;
    /**
     * 行为名
     */
    @ApiModelProperty(value = "行为名")
    private String name;
    /**
     * 发送次数
     */
    @ApiModelProperty(value = "发送次数")
    private Integer sendCount;
    /**
     * 点击次数
     */
    @ApiModelProperty(value = "点击次数")
    private Integer clickCount;
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

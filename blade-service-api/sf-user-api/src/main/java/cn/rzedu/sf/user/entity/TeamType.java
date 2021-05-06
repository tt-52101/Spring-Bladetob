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
 * 班级类型实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_team_type")
@ApiModel(value = "TeamType对象", description = "班级类型")
public class TeamType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 学校分类  1=学校  2=机构
     */
    @ApiModelProperty(value = "学校分类  1=学校  2=机构")
    private Integer groupType;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String code;
    /**
     * 排列序号
     */
    @ApiModelProperty(value = "排列序号")
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

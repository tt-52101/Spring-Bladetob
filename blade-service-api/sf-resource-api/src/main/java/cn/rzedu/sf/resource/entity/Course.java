package cn.rzedu.sf.resource.entity;

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
 * 通用课程
 * 实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@TableName("sf_course")
@ApiModel(value = "Course对象", description = "通用课程 ")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String name;
    /**
     * 主讲人
     */
    @ApiModelProperty(value = "主讲人")
    private String lecturer;
    /**
     * 课程二维码
     */
    @ApiModelProperty(value = "课程二维码")
    private String erCode;
    /**
     * 课程类型  1=点播课 2=直播课  3=慕课 4=课程专辑
     */
    @ApiModelProperty(value = "课程类型  1=点播课 2=直播课  3=慕课 4=课程专辑 ")
    private Integer courseType;
    /**
     * 相关学科  70=书法  71=软笔书法  72=硬笔书法
     */
    @ApiModelProperty(value = "相关学科  70=书法  71=软笔书法  72=硬笔书法")
    private Integer subject;
    /**
     * 教材封面图   uuid
     */
    @ApiModelProperty(value = "教材封面图   uuid")
    private String coverImage;
    /**
     * 单元总数
     */
    @ApiModelProperty(value = "单元总数")
    private Integer lessonCount;
    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private Integer visitedCount;
    /**
     * 是否启用（上架）
     */
    @ApiModelProperty(value = "是否启用（上架）")
    private Boolean enabled;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String description;
    /**
     * 历史参加人数
     */
    @ApiModelProperty(value = "历史参加人数")
    private Integer totalUsers;
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

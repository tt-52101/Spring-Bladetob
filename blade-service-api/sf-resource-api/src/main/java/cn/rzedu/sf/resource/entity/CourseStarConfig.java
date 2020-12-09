package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用课程星星数配置表实体类
 *
 * @author Blade
 * @since 2020-09-09
 */
@Data
@TableName("sf_course_star_config")
@ApiModel(value = "CourseStarConfig对象", description = "通用课程星星数配置表")
public class CourseStarConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 通用课程id
     */
    @ApiModelProperty(value = "通用课程id")
    private Integer courseId;
    /**
     * 第一颗星百分比
     */
    @ApiModelProperty(value = "第一颗星百分比")
    private Integer percentF1;
    /**
     * 第二颗星百分比
     */
    @ApiModelProperty(value = "第二颗星百分比")
    private Integer percentF2;
    /**
     * 第三颗星百分比
     */
    @ApiModelProperty(value = "第三颗星百分比")
    private Integer percentF3;
    /**
     * 第四颗星百分比
     */
    @ApiModelProperty(value = "第四颗星百分比")
    private Integer percentF4;
    /**
     * 第五颗星百分比
     */
    @ApiModelProperty(value = "第五颗星百分比")
    private Integer percentF5;
    /**
     * 第六颗星百分比
     */
    @ApiModelProperty(value = "第六颗星百分比")
    private Integer percentF6;
    /**
     * 第一颗星需要单元数
     */
    @ApiModelProperty(value = "第一颗星需要单元数")
    private Integer starsN1;
    /**
     * 第二颗星需要单元数
     */
    @ApiModelProperty(value = "第二颗星需要单元数")
    private Integer starsN2;
    /**
     * 第三颗星需要单元数
     */
    @ApiModelProperty(value = "第三颗星需要单元数")
    private Integer starsN3;
    /**
     * 第四颗星需要单元数
     */
    @ApiModelProperty(value = "第四颗星需要单元数")
    private Integer starsN4;
    /**
     * 第五颗星需要单元数
     */
    @ApiModelProperty(value = "第五颗星需要单元数")
    private Integer starsN5;
    /**
     * 第六颗星需要单元数
     */
    @ApiModelProperty(value = "第六颗星需要单元数")
    private Integer starsN6;


}

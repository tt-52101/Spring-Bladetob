package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 课程星星数配置表实体类
 *
 * @author Blade
 * @since 2020-08-11
 */
@Data
@TableName("sf_lesson_star_config")
@ApiModel(value = "LessonStarConfig对象", description = "课程星星数配置表")
public class LessonStarConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 教材id
     */
    @ApiModelProperty(value = "教材id")
    private Integer textbookId;
    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    private Integer lessonId;
    /**
     * 第几单元
     */
    @ApiModelProperty(value = "第几单元")
    private Integer section;
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
     * 第一颗星需要汉字数
     */
    @ApiModelProperty(value = "第一颗星需要汉字数")
    private Integer starsN1;
    /**
     * 第二颗星需要汉字数
     */
    @ApiModelProperty(value = "第二颗星需要汉字数")
    private Integer starsN2;
    /**
     * 第三颗星需要汉字数
     */
    @ApiModelProperty(value = "第三颗星需要汉字数")
    private Integer starsN3;


}

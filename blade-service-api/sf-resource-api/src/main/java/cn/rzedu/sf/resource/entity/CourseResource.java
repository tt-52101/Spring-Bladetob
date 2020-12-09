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
 * 课程资源
 * 资源文件一般与课程单元挂钩，一个单元可能有多个文件
 * 实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@TableName("sf_course_resource")
@ApiModel(value = "CourseResource对象", description = "课程资源，资源文件一般与课程单元挂钩，一个单元可能有多个文件 ")
public class CourseResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的课程  course.id
     */
    @ApiModelProperty(value = "对应的课程  course.id")
    private Integer courseId;
    /**
     * 对应的课程单元  course_lesson.id
     */
    @ApiModelProperty(value = "对应的课程单元  course_lesson.id")
    private Integer lessonId;
    /**
     * 资源类型  1=视频课  2=音频课  9=其他
     */
    @ApiModelProperty(value = "资源类型  1=视频课  2=音频课  9=其他 ")
    private Integer resourceType;
    /**
     * 文件类型  1=视频  2=音频  3=课件  4=文档  5=打包  99=其他
     */
    @ApiModelProperty(value = "文件类型  1=视频  2=音频  3=课件  4=文档  5=打包  99=其他 ")
    private Integer formatType;
    /**
     * 如果是视频课或音频课，说明本节时长
     */
    @ApiModelProperty(value = "如果是视频课或音频课，说明本节时长")
    private Integer duration;
    /**
     * 文件uuid
     */
    @ApiModelProperty(value = "文件uuid")
    private String uuid;
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

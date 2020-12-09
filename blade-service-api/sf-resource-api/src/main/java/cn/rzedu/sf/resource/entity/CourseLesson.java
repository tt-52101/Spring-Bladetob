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
 * 课程单元（课时），课程course的子单元（课时、节、集）
 * 实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@TableName("sf_course_lesson")
@ApiModel(value = "CourseLesson对象", description = "课程单元（课时），课程course的子单元（课时、节、集） ")
public class CourseLesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的课程  course.id
     */
    @ApiModelProperty(value = "对应的课程  course.id")
    private Integer courseId;
    /**
     * 排序次序
     */
    @ApiModelProperty(value = "排序次序")
    private Integer lessonIndex;
    /**
     * 本节单元名称
     */
    @ApiModelProperty(value = "本节单元名称")
    private String name;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
    /**
     * 资源类型  1=视频课  2=音频课  9=其他
     */
    @ApiModelProperty(value = "资源类型  1=视频课  2=音频课  9=其他 ")
    private Integer resourceType;
    /**
     * 如果是视频课或音频课，说明本节时长
     */
    @ApiModelProperty(value = "如果是视频课或音频课，说明本节时长")
    private Integer duration;
    /**
     * 历史访问总次数
     */
    @ApiModelProperty(value = "历史访问总次数")
    private Integer visitedCount;
    /**
     * 历史完成总次数（如视频看完100%）
     */
    @ApiModelProperty(value = "历史完成总次数（如视频看完100%）")
    private Integer finishedCount;
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

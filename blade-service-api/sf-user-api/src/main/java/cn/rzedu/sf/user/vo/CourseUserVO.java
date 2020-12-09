package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.CourseUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程用户，参加到某个课程course的用户
 * 视图实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CourseUserVO对象", description = "课程用户，参加到某个课程course的用户")
public class CourseUserVO extends CourseUser {
    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String courseName;
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
     * 单元总数
     */
    @ApiModelProperty(value = "单元总数")
    private Integer lessonCount;
    /**
     * 教材封面图   uuid
     */
    @ApiModelProperty(value = "教材封面图   uuid")
    private String coverImage;
    /**
     * 课程购买人数（学习人数）
     */
    @ApiModelProperty(value = "课程购买人数（学习人数）")
    private Integer boughtCount;
}

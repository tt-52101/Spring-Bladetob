package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.UserLesson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;

/**
 *  学生课程学习情况表
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserLessonVO对象", description = " 学生课程学习情况表 ")
public class UserLessonVO extends UserLesson {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程名称
	 */
	@ApiModelProperty(value = "课程名称")
	private String lessonName;
	/**
	 * 课程排序次序
	 */
	@ApiModelProperty(value = "课程排序次序")
	private Integer listOrder;
	/**
	 * 第几单元
	 */
	@ApiModelProperty(value = "第几单元")
	private Integer section;

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

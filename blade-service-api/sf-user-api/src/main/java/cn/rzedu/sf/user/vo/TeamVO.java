package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 班级视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TeamVO对象", description = "班级")
public class TeamVO extends Team {
	private static final long serialVersionUID = 1L;

	/**
	 * 学校名
	 */
	@ApiModelProperty(value = "学校名")
	private String schoolName;

	/**
	 * 组织类型  1=学校，2=机构
	 */
	@ApiModelProperty(value = "组织类型  1=学校，2=机构")
	private Integer groupType;

	/**
	 * 班级分类名
	 */
	@ApiModelProperty(value = "班级分类名")
	private String teamTypeName;

	/**
	 * 教材名
	 */
	@ApiModelProperty(value = "教材名")
	private String textbookName;

	/**
	 * 课程名
	 */
	@ApiModelProperty(value = "课程名")
	private String lessonName;

	/**
	 * 课程序号
	 */
	@ApiModelProperty(value = "课程序号")
	private Integer lessonNumber;
}

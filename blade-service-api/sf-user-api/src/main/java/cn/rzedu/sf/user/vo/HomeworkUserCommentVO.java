package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.HomeworkUserComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 作业评价视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeworkUserCommentVO对象", description = "作业评价")
public class HomeworkUserCommentVO extends HomeworkUserComment {
	private static final long serialVersionUID = 1L;

}

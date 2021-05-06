package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.Homework;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 作业视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeworkVO对象", description = "作业")
public class HomeworkVO extends Homework {
	private static final long serialVersionUID = 1L;

}

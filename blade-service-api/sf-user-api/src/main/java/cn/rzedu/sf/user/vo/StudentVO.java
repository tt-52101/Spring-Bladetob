package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StudentVO对象", description = "StudentVO对象")
public class StudentVO extends Student {
	private static final long serialVersionUID = 1L;

}

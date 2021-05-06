package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.School;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 学校组织视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SchoolVO对象", description = "学校组织")
public class SchoolVO extends School {
	private static final long serialVersionUID = 1L;

}

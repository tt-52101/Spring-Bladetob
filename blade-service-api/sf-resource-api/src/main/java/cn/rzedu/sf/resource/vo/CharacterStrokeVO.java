package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CharacterStroke;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 汉字笔画视图实体类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterStrokeVO对象", description = "汉字笔画")
public class CharacterStrokeVO extends CharacterStroke {
	private static final long serialVersionUID = 1L;

}

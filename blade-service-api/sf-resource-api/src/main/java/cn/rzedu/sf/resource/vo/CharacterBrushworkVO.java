package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CharacterBrushwork;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体视图实体类
 *
 * @author Blade
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterBrushworkVO对象", description = "汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体")
public class CharacterBrushworkVO extends CharacterBrushwork {
	private static final long serialVersionUID = 1L;

}

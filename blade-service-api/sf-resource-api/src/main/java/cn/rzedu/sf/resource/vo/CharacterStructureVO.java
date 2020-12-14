package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CharacterStructure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 汉字结构视图实体类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterStructureVO对象", description = "汉字结构")
public class CharacterStructureVO extends CharacterStructure {
	private static final long serialVersionUID = 1L;

}

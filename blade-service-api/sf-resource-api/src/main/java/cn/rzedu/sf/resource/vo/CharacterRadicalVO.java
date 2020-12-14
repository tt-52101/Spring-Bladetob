package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CharacterRadical;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 汉字偏旁视图实体类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterRadicalVO对象", description = "汉字偏旁")
public class CharacterRadicalVO extends CharacterRadical {
    private static final long serialVersionUID = 1L;

}

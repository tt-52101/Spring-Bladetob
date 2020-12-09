package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.Character;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 汉字
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterVO对象", description = "汉字")
public class CharacterVO extends Character {
    private static final long serialVersionUID = 1L;

    /**
     * 是否有相关资源
     */
    @ApiModelProperty(value = "是否有相关资源")
    private Integer hasResource;

    /**
     * 资源的学科  71=软笔书法 72=硬笔书法
     */
    @ApiModelProperty(value = "资源的学科")
    private Integer subject;

}

package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.Character;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;

/**
 * 汉字
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@ApiModel(value = "CharacterIsExistVO对象", description = "汉字")
public class CharacterIsExistVO{
    private static final long serialVersionUID = 1L;

    /**
     * 汉字是否存在 0=不存在，1=存在
     */
    @ApiModelProperty(value = "0=不存在，1=存在")
    private Integer isExist = 0;

    /**
     * 汉字ID
     */
    @ApiModelProperty(value = "汉字ID")
    private Integer characterId;

    /**
     * 汉字
     */
    @ApiModelProperty(value = "汉字")
    private String characterName;

}

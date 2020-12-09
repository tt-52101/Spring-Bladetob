package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 汉字资源
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterResourceVO对象", description = "汉字资源")
public class CharacterResourceVO extends CharacterResource {
    private static final long serialVersionUID = 1L;

    /**
     * 资源文件列表
     */
    @ApiModelProperty(value = "资源文件列表")
    private List<CharacterResourceFile> fileList;
}

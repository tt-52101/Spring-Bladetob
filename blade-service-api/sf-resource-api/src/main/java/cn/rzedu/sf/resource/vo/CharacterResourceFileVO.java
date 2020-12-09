package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 汉字资源文件视图实体类
 *
 * @author Blade
 * @since 2020-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CharacterResourceFileVO对象", description = "汉字资源文件")
public class CharacterResourceFileVO extends CharacterResourceFile {
    private static final long serialVersionUID = 1L;

}

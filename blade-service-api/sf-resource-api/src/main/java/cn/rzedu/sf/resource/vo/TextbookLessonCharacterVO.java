package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程相关字体（资源）
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TextbookLessonCharacterVO对象", description = "课程相关字体（资源）")
public class TextbookLessonCharacterVO extends TextbookLessonCharacter {
    private static final long serialVersionUID = 1L;

}

package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.TextbookLesson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 教材课程
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TextbookLessonVO对象", description = "教材课程")
public class TextbookLessonVO extends TextbookLesson {
    private static final long serialVersionUID = 1L;

    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private Integer visitedCount;

    /**
     * 本课程包含的汉字ID集合
     */
    @ApiModelProperty(value = "本课程包含的汉字ID集合")
    private String charIds;
}

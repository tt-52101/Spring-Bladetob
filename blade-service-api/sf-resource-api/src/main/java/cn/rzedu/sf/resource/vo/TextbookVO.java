package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.Textbook;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 书法教材
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TextbookVO对象", description = "书法教材表")
public class TextbookVO extends Textbook {
    private static final long serialVersionUID = 1L;

    /**
     * 课程列表
     */
    @ApiModelProperty(value = "课程列表")
    private List<TextbookLessonVO> textbookLessonVOList;

    /**
     * 单元列表
     */
    private List unitList;
}

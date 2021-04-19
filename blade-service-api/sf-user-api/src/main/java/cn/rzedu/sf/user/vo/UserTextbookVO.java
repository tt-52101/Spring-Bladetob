package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.UserTextbook;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 学生教材学习情况表
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserTextbookVO对象", description = "学生教材学习情况表 ")
public class UserTextbookVO extends UserTextbook {
    private static final long serialVersionUID = 1L;
    /**
     * 教材名称
     */
    @ApiModelProperty(value = "教材名称")
    private String textbookName;
    /**
     * 教材的学科
     */
    @ApiModelProperty(value = "教材的学科  71=软笔书法 72=硬笔书法")
    private Integer subject;

    /**
     * 教材封面图  uuid
     */
    @ApiModelProperty(value = "教材封面图uuid")
    private String coverImage;
    /**
     * 课程总数
     */
    @ApiModelProperty(value = "课程总数")
    private Integer lessonCount;
    /**
     * 汉字总数
     */
    @ApiModelProperty(value = "汉字总数")
    private Integer charCount;
    /**
     * 课程购买人数（学习人数）
     */
    @ApiModelProperty(value = "课程购买人数（学习人数）")
    private Integer boughtCount;
    /**
     * 课程完成人数
     */
    @ApiModelProperty(value = "课程完成人数")
    private Integer finishedCount;
    /**
     * 星星总数
     */
    @ApiModelProperty(value = "星星总数")
    private Integer starCount;
    /**
     * 获得的星星总数
     */
    @ApiModelProperty(value = "获得的星星总数")
    private Integer finishedStarCount;
    /**
     * 当前正在学习的课程名
     */
    @ApiModelProperty(value = "当前正在学习的课程名")
    private String activeLessonName;
    /**
     * 字体
     */
    @ApiModelProperty(value = "字体")
    private String font;
}

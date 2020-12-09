package cn.rzedu.sf.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 汉字课程教材基础数据
 * @author
 */
@Data
@ApiModel(value = "汉字课程教材基础数据", description = "汉字课程教材基础数据，用于扫码链接")
public class CharLinkVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 教材Id
     */
    @ApiModelProperty(value = "教材Id")
    private Integer textbookId;

    /**
     * 课程Id
     */
    @ApiModelProperty(value = "课程Id")
    private Integer lessonId;

    /**
     * 汉字Id
     */
    @ApiModelProperty(value = "汉字Id")
    private Integer characterId;

    /**
     * 教材名
     */
    @ApiModelProperty(value = "教材名")
    private String textbookName;

    /**
     * 课程名
     */
    @ApiModelProperty(value = "课程名")
    private String lessonName;

    /**
     * 汉字名
     */
    @ApiModelProperty(value = "汉字名")
    private String characterName;

    /**
     * 教材的学科  71=软笔书法 72=硬笔书法
     */
    @ApiModelProperty(value = "教材的学科  71=软笔书法 72=硬笔书法")
    private Integer subject;

    /**
     * 适用年级
     */
    @ApiModelProperty(value = "适用年级")
    private String gradeCode;

}

package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 教材课程
 * 1.教材textbook表包含的课程
 * 2.部分课程（硬笔）包含二维码
 * 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_textbook_lesson")
@ApiModel(value = "TextbookLesson对象", description = "教材课程 1.教材textbook表包含的课程 2.部分课程（硬笔）包含二维码")
public class TextbookLesson implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的教材  textbook.id
     */
    @ApiModelProperty(value = "对应的教材  textbook.id")
    private Integer textbookId;
    /**
     * 课程名称，唯一
     */
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 50, message = "课程名称长度不能超过50")
    @ApiModelProperty(value = "课程名称，唯一")
    private String name;
    /**
     * 排序次序
     */
    @NotNull(message = "排序次序不能为空")
    @ApiModelProperty(value = "排序次序")
    private Integer listOrder;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
    /**
     * 第几单元
     */
    @ApiModelProperty(value = "第几单元")
    private Integer section;
    /**
     * 单元名称
     */
    @ApiModelProperty(value = "单元名称")
    private String sectionName;
    /**
     * 课程二维码
     */
    @ApiModelProperty(value = "课程二维码")
    private String erCode;
    /**
     * 本课程包含的汉字（字符集）
     * 仅用于输出，关联记录在表
     * textbook_lesson_character
     */
    @ApiModelProperty(value = "本课程包含的汉字（字符集），仅用于输出，关联记录在表 textbook_lesson_character")
    private String chars;
    /**
     * 本课程包含的汉字数
     */
    @ApiModelProperty(value = "本课程包含的汉字数")
    private Integer charCount;
    /**
     * 二维码所属编号
     */
    @ApiModelProperty(value = "二维码所属编号")
    private String code;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyDate;
    /**
     * 删除标识
     */
    @TableLogic
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}

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
 * 书法教材表
 * 1.系统使用的书法教材
 * 2.属性与普教资源平台的教材类似
 * 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_textbook")
@ApiModel(value = "Textbook对象", description = "书法教材表 1.系统使用的书法教材 2.属性与普教资源平台的教材类似")
public class Textbook implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 教材名称
     */
    @NotBlank(message = "教材名称不能为空")
    @Size(max = 50, message = "教材名称长度不能超过50")
    @ApiModelProperty(value = "教材名称")
    private String name;
    /**
     * 出版社
     */
    @NotBlank(message = "出版社不能为空")
    @Size(max = 50, message = "出版社长度不能超过50")
    @ApiModelProperty(value = "出版社")
    private String publisher;
    /**
     * 适用学段  stage.code
     */
    @NotBlank(message = "适用学段不能为空")
    @ApiModelProperty(value = "适用学段  2=小学 3=初中 4=高中")
    private String stageCode;
    /**
     * 适用年级  grade.code
     */
    @NotBlank(message = "适用年级不能为空")
    @ApiModelProperty(value = "适用年级  21=一年级 22=二年级 23=三年级 24=四年级 25=五年级 26=六年级 31=初一 32=初二 33=初三 41=高一 42=高二 43=高三")
    private String gradeCode;
    /**
     * 册次  1=上册 2=下册
     */
    @NotBlank(message = "册次不能为空")
    @ApiModelProperty(value = "册次  1=上册 2=下册")
    private String volume;
    /**
     * 教材的学科  71=软笔书法 72=硬笔书法
     */
    @NotNull(message = "教材的学科不能为空")
    @ApiModelProperty(value = "教材的学科  71=软笔书法 72=硬笔书法")
    private Integer subject;
    /**
     * 教材二维码
     */
    @ApiModelProperty(value = "教材二维码")
    private String erCode;
    /**
     * 教材封面图   uuid
     */
    @ApiModelProperty(value = "教材封面图   uuid")
    private String coverImage;
    /**
     * 字体
     */
    @ApiModelProperty(value = "字体")
    private String font;
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
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private Integer visitedCount;
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

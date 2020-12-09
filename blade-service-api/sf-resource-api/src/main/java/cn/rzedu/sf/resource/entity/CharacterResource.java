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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 汉字资源
 * 汉字的（视频）资源
 * 一个汉字会有多个资源记录，软笔和硬笔资源都放在这个表，软硬笔作为不同科目来区分
 * 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_character_resource")
@ApiModel(value = "CharacterResource对象", description = "汉字资源 ，汉字的（视频）资源 ，一个汉字会有多个资源记录，软笔和硬笔资源都放在这个表，软硬笔作为不同科目来区分")
public class CharacterResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的字典汉字记录 character.id
     */
    @NotNull(message = "汉字Id不能为空")
    @ApiModelProperty(value = "对应的字典汉字记录 character.id")
    private Integer characterId;
    /**
     * 资源标题
     */
    @NotBlank(message = "资源标题不能为空")
    @Size(max = 20, message = "资源标题长度不能超过20")
    @ApiModelProperty(value = "资源标题")
    private String nameTr;
    /**
     * 关键字
     */
    @NotBlank(message = "关键字不能为空")
    @Size(max = 50, message = "关键字长度不能超过50")
    @ApiModelProperty(value = "关键字")
    private String keyword;
    /**
     * 汉字简体
     */
    @ApiModelProperty(value = "汉字简体")
    private String charS;
    /**
     * 汉字繁体
     */
    @ApiModelProperty(value = "汉字繁体")
    private String charT;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
    /**
     * 资源学科  71=软笔书法 72=硬笔书法
     */
    @NotNull(message = "资源学科不能为空")
    @ApiModelProperty(value = "资源学科  71=软笔书法 72=硬笔书法")
    private Integer subject;
    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private Integer visitedCount;
    /**
     * 资源类型  1=视频模板（软/硬笔）  2=观察模板（软笔）  3=分析模板（软笔）  4=笔法模板（软笔）  5=识字动画模板（硬笔）  6=知识扩展模板（硬笔）
     */
    @ApiModelProperty(value = "资源类型 1=视频模板（软/硬笔）  2=观察模板（软笔）  3=分析模板（软笔）  4=笔法模板（软笔）  5=识字动画模板（硬笔）  6=知识扩展模板（硬笔）")
    private Integer resourceType;
    /**
     * 视频资源文件UUID
     */
    @ApiModelProperty(value = "视频资源文件UUID")
    private String videoId;
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

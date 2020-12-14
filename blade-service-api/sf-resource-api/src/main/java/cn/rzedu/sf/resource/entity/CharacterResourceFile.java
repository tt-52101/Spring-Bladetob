package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 汉字资源文件实体类
 *
 * @author Blade
 * @since 2020-09-05
 */
@Data
@TableName("sf_character_resource_file")
@ApiModel(value = "CharacterResourceFile对象", description = "汉字资源文件")
public class CharacterResourceFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的汉字资源主表  character_resource.id
     */
    @ApiModelProperty(value = "对应的汉字资源主表  character_resource.id")
    private Integer resourceId;
    /**
     * 对应的字典汉字记录 character.id
     */
    @ApiModelProperty(value = "对应的字典汉字记录 character.id")
    private Integer characterId;
    /**
     * 资源学科  71=软笔书法 72=硬笔书法
     */
    @ApiModelProperty(value = "资源学科  71=软笔书法 72=硬笔书法")
    private Integer subject;
    /**
     * 资源类型  1=视频模板（软/硬笔）  2=观察模板（软笔）  3=分析模板（软笔）  4=笔法模板（软笔）  5=识字动画模板（硬笔）  6=知识扩展模板（硬笔）
     */
    @ApiModelProperty(value = "资源类型  1=视频模板（软/硬笔）  2=观察模板（软笔）  3=分析模板（软笔）  4=笔法模板（软笔）  5=识字动画模板（硬笔）  6=知识扩展模板（硬笔）")
    private Integer resourceType;
    /**
     * 字体
     */
    @ApiModelProperty(value = "字体")
    private String font;
    /**
     * 对应模板中的对象
     */
    @ApiModelProperty(value = "对应模板中的对象")
    private String objectId;
    /**
     * 文件类型  video，audio，image，text
     */
    @ApiModelProperty(value = "文件类型  video，audio，image，text")
    private String objectType;
    /**
     * 文本类型对象的内容
     */
    @ApiModelProperty(value = "文本类型对象的内容")
    private String content;
    /**
     * 文件对应的uuid
     */
    @ApiModelProperty(value = "文件对应的uuid")
    private String uuid;
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

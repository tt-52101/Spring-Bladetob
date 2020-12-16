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
 * 汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体实体类
 *
 * @author Blade
 * @since 2020-12-15
 */
@Data
@TableName("sf_character_brushwork")
@ApiModel(value = "CharacterBrushwork对象", description = "汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体")
public class CharacterBrushwork implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类型  1=标准笔法 2=基础笔画
     */
    @ApiModelProperty(value = "类型  1=标准笔法 2=基础笔画")
    private Integer type;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 字体
     */
    @ApiModelProperty(value = "字体")
    private String font;
    /**
     * 文本
     */
    @ApiModelProperty(value = "文本")
    private String content;
    /**
     * 语音
     */
    @ApiModelProperty(value = "语音")
    private String audioId;
    /**
     * 视频
     */
    @ApiModelProperty(value = "视频")
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

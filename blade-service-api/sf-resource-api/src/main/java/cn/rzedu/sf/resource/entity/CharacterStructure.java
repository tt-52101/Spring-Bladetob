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
 * 汉字结构实体类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Data
@TableName("sf_character_structure")
@ApiModel(value = "CharacterStructure对象", description = "汉字结构")
public class CharacterStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 结构类型
     */
    @ApiModelProperty(value = "结构类型")
    private String type;
    /**
     * 结构名称
     */
    @ApiModelProperty(value = "结构名称")
    private String name;
    /**
     * 结构图片uuid
     */
    @ApiModelProperty(value = "结构图片uuid")
    private String image;
    /**
     * 认读图片
     */
    @ApiModelProperty(value = "认读图片")
    private String recognitionImage;
    /**
     * 认读视频
     */
    @ApiModelProperty(value = "认读视频")
    private String recognitionVideo;
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

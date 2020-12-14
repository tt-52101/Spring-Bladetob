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
 * 汉字笔画实体类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Data
@TableName("sf_character_stroke")
@ApiModel(value = "CharacterStroke对象", description = "汉字笔画")
public class CharacterStroke implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 笔画类型。横、竖、撇、捺、点、提、折、钩、组合笔画9种
     */
    @ApiModelProperty(value = "笔画类型。横、竖、撇、捺、点、提、折、钩、组合笔画9种")
    private String type;
    /**
     * 笔画名称
     */
    @ApiModelProperty(value = "笔画名称")
    private String name;
    /**
     * 笔画图片uuid
     */
    @ApiModelProperty(value = "笔画图片uuid")
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
     * 粉笔文本
     */
    @ApiModelProperty(value = "粉笔文本")
    private String chalkText;
    /**
     * 粉笔视频
     */
    @ApiModelProperty(value = "粉笔视频")
    private String chalkVideo;
    /**
     * 钢笔文本
     */
    @ApiModelProperty(value = "钢笔文本")
    private String penText;
    /**
     * 钢笔视频
     */
    @ApiModelProperty(value = "钢笔视频")
    private String penVideo;
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

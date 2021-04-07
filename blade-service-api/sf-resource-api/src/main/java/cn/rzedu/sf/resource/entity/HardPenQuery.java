package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ApiModel(value = "HardPenQuery对象", description = "硬笔查询")
public class HardPenQuery implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "name(标题/名称)")
    private String name;

    @ApiModelProperty(value = "封面图url")
    private String image;

    @ApiModelProperty(value = "认读图片")
    private String recognitionImage;

    @ApiModelProperty(value = "资源对应uuid/认读视频")
    private String recognitionVideo;

    @ApiModelProperty(value = "粉笔文本")
    private String chalkText;

    @ApiModelProperty(value = "粉笔视频")
    private String chalkVideo;

    @ApiModelProperty(value = "钢笔文本")
    private String penText;

    @ApiModelProperty(value = "钢笔视频")
    private String penVideo;


}

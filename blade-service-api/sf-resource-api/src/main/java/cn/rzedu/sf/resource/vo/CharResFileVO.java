package cn.rzedu.sf.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 汉字资源对象（简化）
 * @author
 */
@Data
@ApiModel(value = "汉字资源对象", description = "汉字资源对象")
public class CharResFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    public CharResFileVO(String objectId) {
        this(objectId, "", "");
    }

    public CharResFileVO(String objectId, String objectType) {
        this(objectId, objectType, "");
    }

    public CharResFileVO(String objectId, String objectType, String objectValue) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.objectValue = objectValue;
    }

    /**
     * 对象id，前后端统一
     */
    @ApiModelProperty(value = "对象id，前后端统一")
    private String objectId;
    /**
     * 对象类型：视频，音频，图片，文本
     */
    @ApiModelProperty(value = "对象类型：视频，音频，图片，文本")
    private String objectType;
    /**
     * 对象的值，非文本类型，值为uuid
     */
    @ApiModelProperty(value = "对象的值，非文本类型，值为uuid")
    private String objectValue;

    @ApiModelProperty(value = "图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "音频URL")
    private String audioUrl;
}

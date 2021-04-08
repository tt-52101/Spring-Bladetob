package cn.rzedu.sf.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "硬笔测字结果", description = "硬笔测字结果")
public class AnalyzeWordHard {

    private static final long serialVersionUID = 1L;

    /**
     * 上传的图片
     */
    @ApiModelProperty(value = "上传的图片")
    private String uploadImage;
    /**
     * 字符
     */
    @ApiModelProperty(value = "字符")
    private String word;
    /**
     * 置信度
     */
    @ApiModelProperty(value = "置信度")
    private Float class_score;
    /**
     * 标准字图片的字体
     */
    @ApiModelProperty(value = "标准字图片的字体")
    private String font;
    /**
     * 标准字图片的路径
     */
    @ApiModelProperty(value = "标准字图片的路径")
    private String originalData;
    /**
     * 标准字图片的匹配分数
     */
    @ApiModelProperty(value = "标准字图片的匹配分数")
    private Float score_match;
    /**
     * 标准字图重影图的 image base64 编码
     */
    @ApiModelProperty(value = "标准字图重影图的 image base64 编码")
    private String image;
    /**
     * 标准字图片
     */
    @ApiModelProperty(value = "标准字图片")
    private String originalImage;
}

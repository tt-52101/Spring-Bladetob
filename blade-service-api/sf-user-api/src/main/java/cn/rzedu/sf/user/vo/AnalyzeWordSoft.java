package cn.rzedu.sf.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "软笔测字结果", description = "软笔测字结果")
public class AnalyzeWordSoft {

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
    private List<String> font;
    /**
     * 标准字图片的路径
     */
    @ApiModelProperty(value = "标准字图片的路径")
    private List<String> originalData;
    /**
     * 标准字图片的匹配分数
     */
    @ApiModelProperty(value = "标准字图片的匹配分数")
    private List<Float> score_match;
    /**
     * 标准字图片的字帖出处
     */
    @ApiModelProperty(value = "标准字图片的字帖出处")
    private List<String> provenance;
    /**
     * 标准字图重影图的 image base64 编码
     */
    @ApiModelProperty(value = "标准字图重影图的 image base64 编码")
    private List<String> image;
    /**
     * 标准字图片
     */
    @ApiModelProperty(value = "标准字图片")
    private List<String> originalImage;
}

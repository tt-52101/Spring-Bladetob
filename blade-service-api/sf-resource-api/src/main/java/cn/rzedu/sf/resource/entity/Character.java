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
import javax.validation.constraints.Size;

/**
 * 汉字表
 * 对应字典里面的汉字
 * 一个汉字对应唯一一个记录，同时包含该汉字的软笔和硬笔的资源总属性
 * 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_character")
@ApiModel(value = "Character对象", description = "汉字表，对应字典里面的汉字，一个汉字对应唯一一个记录，同时包含该汉字的软笔和硬笔的资源总属性")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 汉字简体
     */
//    @NotBlank(message = "汉字简体不能为空")
    @Size(max = 3, message = "汉字简体长度不能超过3")
    @ApiModelProperty(value = "汉字简体")
    private String charS;
    /**
     * 汉字繁体
     */
//    @NotBlank(message = "汉字繁体不能为空")
    @Size(max = 3, message = "汉字繁体长度不能超过3")
    @ApiModelProperty(value = "汉字繁体")
    private String charT;
    /**
     * 关键字
     */
    @NotBlank(message = "关键字不能为空")
    @Size(max = 50, message = "关键字长度不能超过50")
    @ApiModelProperty(value = "关键字")
    private String keyword;
    /**
     * 软笔资源总数
     */
    @ApiModelProperty(value = "1=汉字  2=部首  3=笔画")
    private Integer type;
    /**
     * 软笔字二维码
     */
    @ApiModelProperty(value = "汉字图片-浅色")
    private String lightImage;
    /**
     * 软笔字二维码
     */
    @ApiModelProperty(value = "汉字图片-深色")
    private String darkImage;
    /**
     * 软笔字二维码
     */
    @ApiModelProperty(value = "软笔字二维码")
    private String softErCode;
    /**
     * 软笔资源总数
     */
    @ApiModelProperty(value = "软笔资源总数")
    private Integer softResCount;
    /**
     * 软笔资源访问量
     */
    @ApiModelProperty(value = "软笔资源访问量")
    private Integer softResVisitedCount;
    /**
     * 软笔二维码所属编号
     */
    @ApiModelProperty(value = "软笔二维码所属编号")
    private String softCode;
    /**
     * 硬笔字二维码
     */
    @ApiModelProperty(value = "硬笔字二维码")
    private String hardErCode;
    /**
     * 硬笔资源总数
     */
    @ApiModelProperty(value = "硬笔资源总数")
    private Integer hardResCount;
    /**
     * 硬笔资源访问量
     */
    @ApiModelProperty(value = "硬笔资源访问量")
    private Integer hardResVisitedCount;
    /**
     * 硬笔二维码所属编号
     */
    @ApiModelProperty(value = "硬笔二维码所属编号")
    private String hardCode;
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

package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.UserCharacter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 学生汉字学习的情况
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserCharacterVO对象", description = "学生汉字学习的情况")
public class UserCharacterVO extends UserCharacter {
	private static final long serialVersionUID = 1L;
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
	 * 视频资源文件UUID
	 */
	@ApiModelProperty(value = "视频资源文件UUID")
	private String videoId;
	/**
	 * 汉字学习人数
	 */
	@ApiModelProperty(value = "汉字学习人数")
	private Integer learnCount;
	/**
	 * 识字动画视频文件UUID（只用于硬笔）
	 */
	@ApiModelProperty(value = "识字动画视频文件UUID（只用于硬笔）")
	private String cartoonId;
    /**
     * 知识扩展视频文件UUID（只用于硬笔）
     */
    @ApiModelProperty(value = "知识扩展视频文件UUID（只用于硬笔）")
    private String extensionId;
}

package cn.rzedu.sf.resource.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yjs
 * @since 2021-04-15
 */
@Data
@ApiModel(value = "文件数据业务封装类", description = "文件数据业务封装类")
public class FileData {

	@NotNull(message = "objectId不能为空")
	@ApiModelProperty(value = "对应模板中的对象", required = true)
	private String objectId;

	@NotNull(message = "resourceType不能为空")
	@ApiModelProperty(value = "resourceType数据规则为 subject + 模块序号，例：软笔71 认读模块recognition_1 71+1=711 ", required = true)
	private Integer resourceType;

	@NotNull(message = "objectValue不能为空")
	@ApiModelProperty(value = "文件对应的uuid 或者 content", required = true)
	private String objectValue;
}

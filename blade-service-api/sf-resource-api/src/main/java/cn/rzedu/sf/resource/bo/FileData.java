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

	@NotNull(message = "objectType不能为空")
	@ApiModelProperty(value = "文件类型  video，audio，image，text", required = true)
	private String objectType;

	@NotNull(message = "objectValue不能为空")
	@ApiModelProperty(value = "文件对应的uuid 或者 content", required = true)
	private String objectValue;
}

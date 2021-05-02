package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.HardPenQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "ProgramaManagementVO对象", description = "栏目管理")
public class ProgramaManagementVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "栏目ID")
    private Integer sortId;

    @ApiModelProperty(value = "栏目名称")
    private String sortName;

    @ApiModelProperty(value = "subject")
    private Integer subject;

    @ApiModelProperty(value = "mediaType")
    private Integer mediaType;

    @ApiModelProperty(value = "资源数量")
    private Integer resourceCount;



}

package cn.rzedu.sf.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分类列表
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@ApiModel(value = "MediaResourceSortVO对象", description = "分类List")
public class MediaResourceSortVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sortId")
    private Integer sortId;

    @ApiModelProperty(value = "sortName")
    private String sortName;

    @ApiModelProperty(value = "subject")
    private String subject;

    @ApiModelProperty(value = "sortMediaType")
    private Integer sortMediaType;

}

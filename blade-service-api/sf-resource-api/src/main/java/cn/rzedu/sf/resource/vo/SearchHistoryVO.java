package cn.rzedu.sf.resource.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "SearchHistoryVO对象", description = "字库检索记录")
public class SearchHistoryVO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    private String keyword;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "userId")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "userName")
    private String userName;

    /**
     * 资源学科
     */
    @ApiModelProperty(value = "资源学科 71 =软笔,72=硬笔")
    private Integer subject;

    /**
     * createDate
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    /**
     * modifyDate
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyDate;



}

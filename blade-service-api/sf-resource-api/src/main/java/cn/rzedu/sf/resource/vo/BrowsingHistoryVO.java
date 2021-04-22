package cn.rzedu.sf.resource.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "BrowsingHistoryVO对象", description = "浏览记录")
public class BrowsingHistoryVO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

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
     * 资源id
     */
    @ApiModelProperty(value = "资源id")
    private Integer resourceId;

    /**
     * 资源学科
     */
    @ApiModelProperty(value = "资源学科 71 =软笔,72=硬笔")
    private Integer subject;

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型 资源类型 1=授课教案 2=创作文案 3=国学视频 4=知识宝库 5=名师微课 6=碑帖临摹 7=视频资源 8=书法资源 9=隶书教学 10=篆书教学 11=行书教学 12=篆刻教学")
    private Integer mediaType;

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

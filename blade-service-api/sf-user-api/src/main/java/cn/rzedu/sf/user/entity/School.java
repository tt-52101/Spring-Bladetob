package cn.rzedu.sf.user.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 学校组织实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_school")
@ApiModel(value = "School对象", description = "学校组织")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 组织类型  1=学校，2=机构
     */
    @ApiModelProperty(value = "组织类型  1=学校，2=机构")
    private Integer groupType;
    /**
     * 区域代码
     */
    @ApiModelProperty(value = "区域代码")
    private String region;
    /**
     * 区域名
     */
    @ApiModelProperty(value = "区域名")
    private String regionName;
    /**
     * 学校名
     */
    @ApiModelProperty(value = "学校名")
    private String name;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    private String shortName;
    /**
     * logo图片
     */
    @ApiModelProperty(value = "logo图片")
    private String logo;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String introduction;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
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

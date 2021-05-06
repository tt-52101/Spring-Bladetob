package cn.rzedu.sf.user.entity;

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
 * 地区实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_region")
@ApiModel(value = "Region对象", description = "地区")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，跟code同值")
    private Integer id;
    /**
     * 上级分类，用作parent_id
     */
    @ApiModelProperty(value = "上级分类，用作parent_id")
    private Integer parent;
    /**
     * 行政区域码
     */
    @ApiModelProperty(value = "行政区域码")
    private String code;
    /**
     * 省、市、区名称
     */
    @ApiModelProperty(value = "省、市、区名称")
    private String name;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    private String shortName;
    /**
     * 排列次序
     */
    @ApiModelProperty(value = "排列次序")
    private Integer sortOrder;
    /**
     * 级别 0=国家；1=省；2=市；3=区县
     */
    @ApiModelProperty(value = "级别 0=国家；1=省；2=市；3=区县")
    private Integer level;
    /**
     * 省级名称
     */
    @ApiModelProperty(value = "省级名称")
    private String provinceName;
    /**
     * 市级名称
     */
    @ApiModelProperty(value = "市级名称")
    private String cityName;
    /**
     * 区级名称
     */
    @ApiModelProperty(value = "区级名称")
    private String districtName;
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


}

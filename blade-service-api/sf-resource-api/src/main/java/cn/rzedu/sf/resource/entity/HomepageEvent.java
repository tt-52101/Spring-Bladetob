package cn.rzedu.sf.resource.entity;

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
 * 首页轮播导航实体类
 *
 * @author Blade
 * @since 2021-04-15
 */
@Data
@TableName("sf_homepage_event")
@ApiModel(value = "HomepageEvent对象", description = "首页轮播导航")
public class HomepageEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 1=轮播，2=导航
     */
    @ApiModelProperty(value = "1=轮播，2=导航")
    private Integer type;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String link;
    /**
     * 图片uuid
     */
    @ApiModelProperty(value = "图片uuid")
    private String picture;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer listOrder;
    /**
     * 是否显示   1=显示，0=隐藏
     */
    @ApiModelProperty(value = "是否显示   1=显示，0=隐藏")
    private Integer enabled;
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

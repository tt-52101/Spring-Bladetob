package cn.rzedu.sc.goods.entity;

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
 * 商城埋点实体类
 *
 * @author Blade
 * @since 2020-12-02
 */
@Data
@TableName("sc_buried_point")
@ApiModel(value = "BuriedPoint对象", description = "商城埋点")
public class BuriedPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 埋点类型
     */
    @ApiModelProperty(value = "埋点类型")
    private Integer type;
    /**
     * 埋点名称
     */
    @ApiModelProperty(value = "埋点名称")
    private String name;
    /**
     * 相关表主键id
     */
    @ApiModelProperty(value = "相关表主键id")
    private Integer objectId;
    /**
     * 访问时间，只算年月日
     */
    @ApiModelProperty(value = "访问时间，只算年月日")
    private LocalDateTime visitTime;
    /**
     * 访问次数
     */
    @ApiModelProperty(value = "访问次数")
    private Integer visitCount;
    /**
     * 访问人数
     */
    @ApiModelProperty(value = "访问人数")
    private Integer visitPeopleCount;
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

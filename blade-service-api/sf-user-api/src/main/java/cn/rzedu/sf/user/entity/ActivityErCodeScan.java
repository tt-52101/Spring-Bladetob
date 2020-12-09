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
 * 活动二维码扫码用户实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@TableName("sf_activity_er_code_scan")
@ApiModel(value = "ActivityErCodeScan对象", description = "活动二维码扫码用户")
public class ActivityErCodeScan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 二维码id
     */
    @ApiModelProperty(value = "二维码id")
    private Integer codeId;
    /**
     * 二维码类型  1=活动海报二维码 2=推文二维码 3=课本二维码
     */
    @ApiModelProperty(value = "二维码类型  1=活动海报二维码 2=推文二维码 3=课本二维码")
    private Integer type;
    /**
     * 用户id (发起人)
     */
    @ApiModelProperty(value = "用户id (发起人)")
    private Integer userId;
    /**
     * 扫码用户id
     */
    @ApiModelProperty(value = "扫码用户id")
    private Integer scanUserId;
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

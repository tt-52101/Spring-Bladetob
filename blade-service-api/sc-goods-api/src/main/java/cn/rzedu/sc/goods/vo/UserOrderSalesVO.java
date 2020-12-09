package cn.rzedu.sc.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户订单销售总额
 * @author
 */
@Data
@ApiModel(value = "用户订单销售总额", description = "用户订单销售总额")
public class UserOrderSalesVO {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 日总额
     */
    @ApiModelProperty(value = "日总额")
    private String totalDay;
    /**
     * 周总额
     */
    @ApiModelProperty(value = "周总额")
    private String totalWeek;
    /**
     * 月总额
     */
    @ApiModelProperty(value = "月总额")
    private String totalMonth;
    /**
     * 季度总额
     */
    @ApiModelProperty(value = "季度总额")
    private String totalQuarter;

}

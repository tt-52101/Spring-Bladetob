package cn.rzedu.sf.user.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 视图实体类
 *
 * @author Blade
 * @since 2021-04-12
 */
@Data
@ApiModel(value = "RegionVO对象", description = "RegionVO对象")
public class RegionVO {
    private static final long serialVersionUID = 1L;

    /**
     * provinceName
     */
    @ApiModelProperty(value = "省区名")
    private String provinceName;

    /**
     * cityName
     */
    @ApiModelProperty(value = "市区名")
    private String cityName;
    /**
     * districtName
     */
    @ApiModelProperty(value = "区名")
    private String districtName;

}

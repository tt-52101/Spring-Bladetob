package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.HomepageEvent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 首页轮播导航视图实体类
 *
 * @author Blade
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomepageEventVO对象", description = "首页轮播导航")
public class HomepageEventVO extends HomepageEvent {
	private static final long serialVersionUID = 1L;
	/**
	 * 图片路径
	 */
	@ApiModelProperty(value = "图片路径")
	private String pictureUrl;
}

package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.Publisher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 出版社视图实体类
 *
 * @author Blade
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PublisherVO对象", description = "出版社")
public class PublisherVO extends Publisher {
	private static final long serialVersionUID = 1L;

}

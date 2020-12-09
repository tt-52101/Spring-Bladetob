package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.Activity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 活动视图实体类
 *
 * @author Blade
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityVO对象", description = "活动")
public class ActivityVO extends Activity {
    private static final long serialVersionUID = 1L;

}

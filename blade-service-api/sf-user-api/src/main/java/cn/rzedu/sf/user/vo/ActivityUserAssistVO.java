package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.ActivityUserAssist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 用户助力表视图实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityUserAssistVO对象", description = "用户助力表")
public class ActivityUserAssistVO extends ActivityUserAssist {
    private static final long serialVersionUID = 1L;

}

package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.UserLoginLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 用户每日登陆记录视图实体类
 *
 * @author Blade
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserLoginLogVO对象", description = "用户每日登陆记录")
public class UserLoginLogVO extends UserLoginLog {
    private static final long serialVersionUID = 1L;

}

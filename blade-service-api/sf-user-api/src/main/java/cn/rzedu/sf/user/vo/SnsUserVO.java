package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.SnsUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 少年说--用户报名信息视图实体类
 *
 * @author Blade
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SnsUserVO对象", description = "少年说--用户报名信息")
public class SnsUserVO extends SnsUser {
    private static final long serialVersionUID = 1L;

}

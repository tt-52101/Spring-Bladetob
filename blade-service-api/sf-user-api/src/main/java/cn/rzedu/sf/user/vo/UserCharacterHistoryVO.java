package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.UserCharacterHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 学生学习汉字的历史记录
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserCharacterHistoryVO对象", description = "学生学习汉字的历史记录")
public class UserCharacterHistoryVO extends UserCharacterHistory {
	private static final long serialVersionUID = 1L;

}

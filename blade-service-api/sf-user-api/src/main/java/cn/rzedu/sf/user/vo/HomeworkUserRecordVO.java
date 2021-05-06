package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.HomeworkUserRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 学生作业记录视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeworkUserRecordVO对象", description = "学生作业记录")
public class HomeworkUserRecordVO extends HomeworkUserRecord {
	private static final long serialVersionUID = 1L;

}

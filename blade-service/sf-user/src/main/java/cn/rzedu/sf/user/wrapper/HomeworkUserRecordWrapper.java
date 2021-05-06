package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.HomeworkUserRecord;
import cn.rzedu.sf.user.vo.HomeworkUserRecordVO;

/**
 * 学生作业记录包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class HomeworkUserRecordWrapper extends BaseEntityWrapper<HomeworkUserRecord, HomeworkUserRecordVO>  {

    public static HomeworkUserRecordWrapper build() {
        return new HomeworkUserRecordWrapper();
    }

	@Override
	public HomeworkUserRecordVO entityVO(HomeworkUserRecord homeworkUserRecord) {
		HomeworkUserRecordVO homeworkUserRecordVO = BeanUtil.copy(homeworkUserRecord, HomeworkUserRecordVO.class);

		return homeworkUserRecordVO;
	}

}

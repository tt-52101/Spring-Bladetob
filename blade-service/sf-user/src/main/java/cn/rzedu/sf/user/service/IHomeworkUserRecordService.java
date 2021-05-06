package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.HomeworkUserRecord;
import cn.rzedu.sf.user.vo.HomeworkUserRecordVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 学生作业记录 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface IHomeworkUserRecordService extends IService<HomeworkUserRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homeworkUserRecord
	 * @return
	 */
	IPage<HomeworkUserRecordVO> selectHomeworkUserRecordPage(IPage<HomeworkUserRecordVO> page,
                                                             HomeworkUserRecordVO homeworkUserRecord);

}

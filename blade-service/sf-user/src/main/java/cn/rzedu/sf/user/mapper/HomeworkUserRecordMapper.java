package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.HomeworkUserRecord;
import cn.rzedu.sf.user.vo.HomeworkUserRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 学生作业记录 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface HomeworkUserRecordMapper extends BaseMapper<HomeworkUserRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homeworkUserRecord
	 * @return
	 */
	List<HomeworkUserRecordVO> selectHomeworkUserRecordPage(IPage page, HomeworkUserRecordVO homeworkUserRecord);

}

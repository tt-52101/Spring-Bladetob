package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.HomeworkUserRecord;
import cn.rzedu.sf.user.vo.HomeworkUserRecordVO;
import cn.rzedu.sf.user.mapper.HomeworkUserRecordMapper;
import cn.rzedu.sf.user.service.IHomeworkUserRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 学生作业记录 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class HomeworkUserRecordServiceImpl extends ServiceImpl<HomeworkUserRecordMapper, HomeworkUserRecord> implements IHomeworkUserRecordService {

	@Override
	public IPage<HomeworkUserRecordVO> selectHomeworkUserRecordPage(IPage<HomeworkUserRecordVO> page, HomeworkUserRecordVO homeworkUserRecord) {
		return page.setRecords(baseMapper.selectHomeworkUserRecordPage(page, homeworkUserRecord));
	}

}

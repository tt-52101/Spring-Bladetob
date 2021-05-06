package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.Homework;
import cn.rzedu.sf.user.vo.HomeworkVO;
import cn.rzedu.sf.user.mapper.HomeworkMapper;
import cn.rzedu.sf.user.service.IHomeworkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 作业 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements IHomeworkService {

	@Override
	public IPage<HomeworkVO> selectHomeworkPage(IPage<HomeworkVO> page, HomeworkVO homework) {
		return page.setRecords(baseMapper.selectHomeworkPage(page, homework));
	}

}

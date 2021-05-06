package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.HomeworkUserComment;
import cn.rzedu.sf.user.vo.HomeworkUserCommentVO;
import cn.rzedu.sf.user.mapper.HomeworkUserCommentMapper;
import cn.rzedu.sf.user.service.IHomeworkUserCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 作业评价 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class HomeworkUserCommentServiceImpl extends ServiceImpl<HomeworkUserCommentMapper, HomeworkUserComment> implements IHomeworkUserCommentService {

	@Override
	public IPage<HomeworkUserCommentVO> selectHomeworkUserCommentPage(IPage<HomeworkUserCommentVO> page, HomeworkUserCommentVO homeworkUserComment) {
		return page.setRecords(baseMapper.selectHomeworkUserCommentPage(page, homeworkUserComment));
	}

}

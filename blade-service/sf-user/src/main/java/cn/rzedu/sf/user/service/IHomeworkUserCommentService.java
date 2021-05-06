package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.HomeworkUserComment;
import cn.rzedu.sf.user.vo.HomeworkUserCommentVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 作业评价 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface IHomeworkUserCommentService extends IService<HomeworkUserComment> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homeworkUserComment
	 * @return
	 */
	IPage<HomeworkUserCommentVO> selectHomeworkUserCommentPage(IPage<HomeworkUserCommentVO> page,
                                                               HomeworkUserCommentVO homeworkUserComment);

}

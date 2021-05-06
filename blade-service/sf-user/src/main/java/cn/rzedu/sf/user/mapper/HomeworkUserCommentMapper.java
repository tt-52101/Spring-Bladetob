package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.HomeworkUserComment;
import cn.rzedu.sf.user.vo.HomeworkUserCommentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 作业评价 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface HomeworkUserCommentMapper extends BaseMapper<HomeworkUserComment> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homeworkUserComment
	 * @return
	 */
	List<HomeworkUserCommentVO> selectHomeworkUserCommentPage(IPage page, HomeworkUserCommentVO homeworkUserComment);

}

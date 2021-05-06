package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.HomeworkUserComment;
import cn.rzedu.sf.user.vo.HomeworkUserCommentVO;

/**
 * 作业评价包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class HomeworkUserCommentWrapper extends BaseEntityWrapper<HomeworkUserComment, HomeworkUserCommentVO>  {

    public static HomeworkUserCommentWrapper build() {
        return new HomeworkUserCommentWrapper();
    }

	@Override
	public HomeworkUserCommentVO entityVO(HomeworkUserComment homeworkUserComment) {
		HomeworkUserCommentVO homeworkUserCommentVO = BeanUtil.copy(homeworkUserComment, HomeworkUserCommentVO.class);

		return homeworkUserCommentVO;
	}

}

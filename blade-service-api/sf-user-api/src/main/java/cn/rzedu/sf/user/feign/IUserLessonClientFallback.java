package cn.rzedu.sf.user.feign;

import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;
/**
 * 书法用户课程 远程调用服务失败处理
 * @author
 */
@Component
public class IUserLessonClientFallback implements IUserLessonClient {

    @Override
    public R<Boolean> createTextbook(Integer userId, Integer textbookId) {
        return R.fail("调用失败");
    }

    @Override
    public R<Boolean> createCourse(Integer userId, Integer courseId) {
        return R.fail("调用失败");
    }
}

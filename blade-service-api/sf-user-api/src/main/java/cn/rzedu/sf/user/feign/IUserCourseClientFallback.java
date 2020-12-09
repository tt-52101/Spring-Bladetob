package cn.rzedu.sf.user.feign;

import java.util.List;

import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

import cn.rzedu.sf.user.vo.UserVO;
/**
 * 书法用户课程 远程调用服务失败处理
 * @author
 */
@Component
public class IUserCourseClientFallback implements IUserCourseClient {

    @Override
    public R<Boolean> createCourse(Integer userId, Integer courseId,Integer ownedType) {
        return R.fail("调用失败");
    }

	@Override
	public R<List<UserVO>> findInitiatorUser(Integer greaterNumber, Integer lessNumber) {
		return R.fail("调用失败");
	}

	@Override
	public R<List<UserVO>> selectAllUser() {
		return R.fail("调用失败");
	}

   
}

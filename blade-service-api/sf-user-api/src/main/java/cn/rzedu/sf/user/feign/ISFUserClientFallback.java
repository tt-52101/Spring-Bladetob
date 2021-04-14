package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.user.vo.SnsUserVO;
import cn.rzedu.sf.user.vo.UserVO;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 书法用户 远程调用服务失败处理
 * @author
 */
@Component
public class ISFUserClientFallback implements ISFUserClient {

    @Override
    public R<UserVO> detail(Integer id) {
        return R.fail("调用失败");
    }

    @Override
    public R<UserVO> detailByOpenIdV1(String openId) {
        return R.fail("调用失败");
    }

    @Override
    public R<UserVO> detailByOpenIdV2(String openId, String name, String headImgUrl) {
        return R.fail("调用失败");
    }

    @Override
    public R<UserVO> detailByOpenIdV3(String openId, String name, String headImgUrl, Integer sourceType) {
        return R.fail("调用失败");
    }

    @Override
    public R<Boolean> updateById(UserVO userVO) {
        return R.fail("调用失败");
    }

    @Override
    public R<UserVO> detailByUnionId(String unionId) {
        return R.fail("调用失败");
    }

    @Override
    public R<UserVO> detailByUnionId(String unionId, String name, String headImgUrl) {
        return R.fail("调用失败");
    }

    @Override
    public R<SnsUserVO> detailUserForSns(String openId, String nickname, String headImgUrl) {
        return R.fail("调用失败");
    }
}

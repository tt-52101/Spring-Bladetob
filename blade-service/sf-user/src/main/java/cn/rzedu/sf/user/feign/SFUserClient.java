package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.user.service.ISnsUserService;
import cn.rzedu.sf.user.service.IUserService;
import cn.rzedu.sf.user.vo.SnsUserVO;
import cn.rzedu.sf.user.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 书法用户 远程调用服务 控制器
 * @author
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class SFUserClient implements ISFUserClient {

    private IUserService userService;

    private ISnsUserService snsUserService;

    @Override
    public R<UserVO> detail(Integer id) {
        return R.data(BeanUtil.copy(userService.getById(id), UserVO.class));
    }

    @Override
    public R<UserVO> detailByOpenIdV1(String openId) {
        return R.data(userService.findByOpenId(openId));
    }

    @Override
    public R<UserVO> detailByOpenIdV2(String openId, String name, String headImgUrl) {
        return R.data(userService.findByOpenId(openId, name, headImgUrl));
    }

    @Override
    public R<UserVO> detailByOpenIdV3(String openId, String name, String headImgUrl, Integer sourceType) {
        return R.data(userService.addUserVO(openId, name, headImgUrl, sourceType));
    }

    @Override
    public R<Boolean> updateById(UserVO userVO) {
        return R.data(userService.updateUserVo(userVO));
    }

    @Override
    public R<SnsUserVO> detailUserForSns(String openId, String nickname, String headImgUrl) {
        return R.data(snsUserService.findByOpenId(openId, nickname, headImgUrl));
    }

    @Override
    public R<UserVO> detailByUnionId(String unionId) {
        return R.data(userService.findByUnionId(unionId));
    }

    @Override
    public R<UserVO> detailByUnionId(String unionId, String name, String headImgUrl) {
        return R.data(userService.findByUnionId(unionId, name, headImgUrl));
    }
}

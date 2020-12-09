package cn.rzedu.sc.goods.feign;

import cn.rzedu.sc.goods.service.IBuriedPointService;
import cn.rzedu.sc.goods.service.IGrouponGroupService;
import cn.rzedu.sc.goods.service.IGrouponRuleService;
import cn.rzedu.sc.goods.service.IOrderService;
import cn.rzedu.sc.goods.vo.GrouponGroupVO;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

//@ApiIgnore()
@RestController
@AllArgsConstructor
public class GrouponClient implements IGrouponClient {

    private IGrouponRuleService grouponRuleService;

    private IGrouponGroupService grouponGroupService;

    private IOrderService orderService;

    private IBuriedPointService buriedPointService;

    private ISFUserClient userClient;

    @Override
    public R<Integer> initiateGroup(Integer userId, Integer grouponRuleId) {
        GrouponRuleVO grouponRuleVO = grouponRuleService.findDetailById(grouponRuleId);
        if (grouponRuleVO == null) {
            return R.fail("团购商品不存在");
        }
        //默认拼团来源，公众号链接消息
        GrouponGroupVO grouponGroupVO = grouponGroupService.createNormalGroup(grouponRuleId, userId, null, 1);
        Integer groupVOId = grouponGroupVO.getId();
        orderService.createOrderByGroupon(userId, grouponRuleVO, 2, 1, null, groupVOId, 1);
        return R.data(groupVOId);
    }

    @Override
    public R<Boolean> updateBuriedPointCount(Integer type, Integer objectId, Integer userId) {
        buriedPointService.updateVisitCount(type, objectId, userId);
        return R.data(true);
    }

    @Override
    public R<Boolean> updateBuriedPointCountV2(Integer type, Integer objectId, String openId) {
        R<UserVO> detail = userClient.detailByOpenIdV1(openId);
        Integer userId = detail.getData().getId();
        if (userId != null) {
            buriedPointService.updateVisitCount(type, objectId, userId);
        }
        return R.data(true);
    }
}

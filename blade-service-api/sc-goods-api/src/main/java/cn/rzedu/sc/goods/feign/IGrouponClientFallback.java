package cn.rzedu.sc.goods.feign;

import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

@Component
public class IGrouponClientFallback implements IGrouponClient {
    @Override
    public R<Integer> initiateGroup(Integer userId, Integer grouponRuleId) {
        return R.fail("调用失败");
    }

    @Override
    public R<Boolean> updateBuriedPointCount(Integer type, Integer objectId, Integer userId) {
        return R.fail("调用失败");
    }

    @Override
    public R<Boolean> updateBuriedPointCountV2(Integer type, Integer objectId, String openId) {
        return R.fail("调用失败");
    }
}

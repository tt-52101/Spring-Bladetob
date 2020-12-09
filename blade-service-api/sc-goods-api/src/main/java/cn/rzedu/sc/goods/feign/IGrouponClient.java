package cn.rzedu.sc.goods.feign;

import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        //定义Feign指向的service-id
        value = "sc-goods"
)
public interface IGrouponClient {

    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/groupon";

    /**
     * 发起新拼团，返回拼团id
     * @param userId
     * @param grouponRuleId 拼团课程id
     * @return
     */
    @GetMapping(API_PREFIX + "/initiateGroup")
    R<Integer> initiateGroup(@RequestParam("userId") Integer userId,
                             @RequestParam(value = "grouponRuleId", defaultValue = "4") Integer grouponRuleId);

    /**
     * 修改埋点人数
     * @param type
     * @param objectId
     * @param userId
     * @return
     */
    @PostMapping(API_PREFIX + "/buriedPoint")
    R<Boolean> updateBuriedPointCount(@RequestParam("type") Integer type, @RequestParam("objectId") Integer objectId,
                                      @RequestParam("userId")Integer userId);

    /**
     * 修改埋点人数
     * @param type
     * @param objectId
     * @param openId
     * @return
     */
    @PostMapping(API_PREFIX + "/buriedPointV2")
    R<Boolean> updateBuriedPointCountV2(@RequestParam("type") Integer type, @RequestParam("objectId") Integer objectId,
                                      @RequestParam("openId")String openId);
}

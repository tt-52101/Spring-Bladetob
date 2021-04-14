package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.user.vo.UserVO;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 书法用户 远程调用服务 接口类
 * @author
 */
@FeignClient(
        //定义Feign指向的service-id
        value = "sf-user"
        //定义hystrix配置类
//        fallback = ISFUserClientFallback.class
)
public interface ISFUserClient {
    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/user";

    /**
     * 获取用户详情，根据userId
     *
     * @param id 主键，userId
     * @return
     */
    @GetMapping(API_PREFIX + "/detail")
    R<UserVO> detail(@RequestParam("id") Integer id);

    /**
     * 获取用户详情，根据微信openId
     *
     * @param openId 微信openId
     * @return
     */
    @GetMapping(API_PREFIX + "/detail/openId/v1")
    R<UserVO> detailByOpenIdV1(@RequestParam("openId") String openId);

    /**
     * 获取用户详情，根据微信openId
     *
     * @param openId 微信openId
     * @param name  微信用户昵称
     * @param headImgUrl 微信用户头像地址
     * @return
     */
    @PostMapping(API_PREFIX + "/detail/openId/v2")
    R<UserVO> detailByOpenIdV2(@RequestParam("openId") String openId,
                               @RequestParam("name") String name,
                               @RequestParam("headImgUrl") String headImgUrl);

    /**
     * 获取用户详情，根据微信openId
     *
     * @param openId 微信openId
     * @param name  微信用户昵称
     * @param headImgUrl 微信用户头像地址
     * @return
     */
    @PostMapping(API_PREFIX + "/detail/openId/v3")
    R<UserVO> detailByOpenIdV3(@RequestParam("openId") String openId,
                               @RequestParam("name") String name,
                               @RequestParam("headImgUrl") String headImgUrl,
                               @RequestParam("sourceType") Integer sourceType);

    @PostMapping(API_PREFIX + "/updateById")
    R<Boolean> updateById(@RequestBody  UserVO userVO);

    @GetMapping(API_PREFIX + "/detail/unionId")
    R<UserVO> detailByUnionId(@RequestParam("unionId") String unionId);

    @GetMapping(API_PREFIX + "/detail/unionIdAndOther")
    R<UserVO> detailByUnionId(@RequestParam("unionId") String unionId,
                              @RequestParam("name") String name,
                              @RequestParam("headImgUrl") String headImgUrl);
}

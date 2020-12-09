package cn.rzedu.sf.user.feign;


import java.util.List;

import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.rzedu.sf.user.vo.UserVO;

/**
 * 书法用户课程 远程调用服务 接口类
 * @author
 */
@FeignClient(
        //定义Feign指向的service-id
        value = "sf-user"
)
public interface IUserCourseClient {

    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/user/course";

    /**
     * 短信活动赠送课程
     * @param userId
     * @param courseId
     * @return
     */
    @PostMapping(API_PREFIX + "/create")
    R<Boolean> createCourse(@RequestParam("userId") Integer userId, @RequestParam("courseId") Integer courseId,@RequestParam("ownedType") Integer ownedType);

    
    /**
     * 助力人数在一定范围内的 发起人用户
     * @param greaterNumber  大数
     * @param lessNumber     小数
     * @return
     */
    @GetMapping(API_PREFIX + "/initiator")
    R<List<UserVO>> findInitiatorUser(@RequestParam(value = "greaterNumber", defaultValue = "5") Integer greaterNumber,
                                      @RequestParam(value = "lessNumber", defaultValue = "1") Integer lessNumber);
    
    
    
    /**
     * 查找所有用户
     * @return
     */
    @GetMapping(API_PREFIX + "/selectAllUser")
    R<List<UserVO>> selectAllUser();
}

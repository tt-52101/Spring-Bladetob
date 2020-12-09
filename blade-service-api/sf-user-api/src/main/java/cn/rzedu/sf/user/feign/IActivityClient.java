package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.entity.ActivityErCode;
import cn.rzedu.sf.user.vo.UserVO;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 活动埋点 远程调用服务 接口类
 * @author
 */
@FeignClient(
    //定义Feign指向的service-id
    value = "sf-user"
)
public interface IActivityClient {

    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/activity";

    /**
     * 修改用户关注状态
     * @param userId    用户Id
     * @param focusStatus   关注状态，1=关注 0=取关
     * @return
     */
    @PostMapping(API_PREFIX + "/user/focus")
    R userFocus(@RequestParam("userId") Integer userId, @RequestParam("focusStatus") Integer focusStatus);

    /**
     * 用户礼包类型
     * @param userId    用户Id
     * @return
     */
    @PostMapping(API_PREFIX + "/user/awardType")
    R<Integer> userAwardType(@RequestParam("userId") Integer userId);


    /**
     * 助力流程
     * @param initiatorId   发起人用户id
     * @param assistantId   助力人用户id
     * @param type          助力活动类型，针对不同的奖品活动
     * @return  返回助力人数  0=已助力过/或助力人数超过8人
     */
    @PostMapping(API_PREFIX + "/assistant/process")
    R<Integer> assistantProcess(@RequestParam("initiatorId") Integer initiatorId,
                                @RequestParam("assistantId") Integer assistantId,
                                @RequestParam("type") Integer type);


    /**
     * 查找或生成用户海报二维码
     * @param userId    用户Id
     * @return
     */
    @PostMapping(API_PREFIX + "/ercode/get")
    R<ActivityErCode> findOrAddUserErCode(@RequestParam("userId") Integer userId, @RequestParam("type") Integer type);

    /**
     * 更新二维码扫码次数/人数
     * @param type          二维码类型 1=活动海报二维码 2=推文二维码 3=课本二维码
     * @param userId        目标用户id，type=2/3时，userId=0(表示系统)
     * @param scanUserId    扫码用户id
     * @return
     */
    @PostMapping(API_PREFIX + "/scan/modify")
    R modifyErCodeCount(@RequestParam("type") Integer type,
                        @RequestParam("userId") Integer userId,
                        @RequestParam("scanUserId") Integer scanUserId);

    /**
     * 更新发送次数
     * @param type  行为类型  1=千字文课程消息模板  2=诗词课程消息模板  3=领取礼包消息模板  4=公众号发出活动海报
     * @param count 发送次数
     * @return
     */
    @PostMapping(API_PREFIX + "/action/send")
    R updateSendCount(@RequestParam("type") Integer type, @RequestParam("count") Integer count);

    /**
     * 更新点击次数
     * @param type  行为类型  1=千字文课程消息模板  2=诗词课程消息模板  3=领取礼包消息模板  5=用户提交奖品地址  6=用户取消关注
     * @param count 点击次数
     * @return
     */
    @PostMapping(API_PREFIX + "/action/click")
    R updateClickCount(@RequestParam("type") Integer type, @RequestParam("count") Integer count);

    /**
     * 8人助力裂变活动
     * @return
     */
    @GetMapping(API_PREFIX + "/zl")
    R<Activity> findZuLiActivity();

    /**
     * 更新课程链接发送次数
     * @param lessonId 课程id
     * @return
     */
    @PostMapping(API_PREFIX + "/lesson/send")
    R updateLessonSendCount(@RequestParam("lessonId") Integer lessonId);

    /**
     * 助力人数在一定范围内的 发起人用户
     * @param greaterNumber  大数
     * @param lessNumber     小数
     * @return
     */
    @GetMapping(API_PREFIX + "/initiator")
    R<List<UserVO>> findInitiatorUser(@RequestParam(value = "greaterNumber", defaultValue = "5") Integer greaterNumber,
                                      @RequestParam(value = "lessNumber", defaultValue = "1") Integer lessNumber);
}

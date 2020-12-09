package cn.rzedu.sf.user.feign;


import cn.rzedu.sf.user.vo.UserVO;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 书法用户课程 远程调用服务 接口类
 * @author
 */
@FeignClient(
        //定义Feign指向的service-id
        value = "sf-user"
)
public interface IUserLessonClient {

    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/user/lesson";

    /**
     * 生成用户软硬笔课程
     * @param userId
     * @param textbookId
     * @return
     */
    @PostMapping(API_PREFIX + "/textbook/create")
    R<Boolean> createTextbook(@RequestParam("userId") Integer userId, @RequestParam("textbookId") Integer textbookId);

    /**
     * 生成用户通用课程
     * @param userId
     * @param courseId
     * @return
     */
    @PostMapping(API_PREFIX + "/course/create")
    R<Boolean> createCourse(@RequestParam("userId") Integer userId, @RequestParam("courseId") Integer courseId);
}

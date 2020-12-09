package cn.rzedu.sf.resource.feign;

import cn.rzedu.sf.resource.entity.*;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.vo.CharLinkVO;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 远程调用服务 接口类
 * @author
 */
@FeignClient(
    //定义Feign指向的service-id
    value = "sf-resource"
    //定义hystrix配置类
//    fallback = ITextbookClientFallback.class
)
public interface ITextbookClient {

    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/textbook";

    /**
     * 获取单个教材详情
     *
     * @param id 主键
     * @return
     */
    @GetMapping(API_PREFIX + "/detail")
    R<Textbook> detail(@RequestParam("id") Integer id);

    /**
     * 获取教材所有课程
     * @param textbookId
     * @return
     */
    @GetMapping(API_PREFIX + "/lessons")
    R<List<TextbookLessonVO>> allLessons(@RequestParam("textbookId") Integer textbookId);

    /**
     * 获取单个课程详情
     * @param lessonId
     * @return
     */
    @GetMapping(API_PREFIX + "/lesson/detail")
    R<TextbookLesson> lessonDetail(@RequestParam("lessonId") Integer lessonId);


    /**
     * 获取课程所有汉字
     * @param lessonId
     * @return
     */
    @GetMapping(API_PREFIX + "/lesson/chars")
    R<List<TextbookLessonCharacter>> allLessonCharacters(@RequestParam("lessonId") Integer lessonId);


    /**
     * 获取单个课程汉字
     * @param lessonId
     * @Param characterId
     * @return
     */
    @GetMapping(API_PREFIX + "/lesson/char/detail")
    R<TextbookLessonCharacter> lessonCharacterDetail(@RequestParam("lessonId") Integer lessonId,
                                                     @RequestParam("characterId") Integer characterId);

    /**
     * 课程星数配置
     * @param lessonId
     * @return
     */
    @GetMapping(API_PREFIX + "/lesson/star")
    R<LessonStarConfig> lessonStar(@RequestParam("lessonId") Integer lessonId);

    /**
     * 更新汉字资源访问量，自动+1
     * @param textbookId
     * @param lessonId
     * @param characterId
     * @return
     */
    @GetMapping(API_PREFIX + "/visitor/update")
    R updateCharVisitedCount(@RequestParam("textbookId") Integer textbookId, @RequestParam("lessonId") Integer lessonId,
                             @RequestParam("characterId") Integer characterId);
    
    
    /**
     * 根据汉字二维码和软硬笔类型获取 汉字名，课程名
     *
     * @param type 类型 1=软笔 2=硬笔
     * @param code 二维码编号  软笔为汉字编号，硬笔是课程编号
     * @return
     */
    @GetMapping(API_PREFIX + "/findByCodeAndType")
    R<List<CharLinkVO>> findByCodeAndType(@RequestParam("type") String type, @RequestParam("code") String code);


    /**
     * 生成课程星数配置，已有数据不生成
     * @param lessonId
     * @return
     */
    @PostMapping(API_PREFIX + "/createLessonStar")
    R<LessonStarConfig> createLessonStarConfig(@RequestParam("lessonId") Integer lessonId);

    /**
     * 获取课程第一课
     * @param textbookId
     * @return
     */
    @GetMapping(API_PREFIX + "/firstLesson")
    R<TextbookLesson> firstLesson(@RequestParam("textbookId") Integer textbookId);
    
    /**
     * 课程二维码编码拿字
     * @param code
     * @return
     */
    @GetMapping(API_PREFIX + "/getLessonCharacterInfo")
    R<Map<String, Object>> getLessonCharacterInfo(@RequestParam("code") String code);



    /**
     * 获取硬笔知识拓展课程（默认动画千字文）
     * @return
     */
    @GetMapping(API_PREFIX + "/course/extensionLesson")
    R<List<CourseLessonVO>> findKnowledgeExtensionLesson();

    /**
     * 获取通用课程所有单元
     * @param courseId
     * @return
     */
    @GetMapping(API_PREFIX + "/course/lessons")
    R<List<CourseLessonVO>> findCourseLessons(@RequestParam("courseId") Integer courseId);

    /**
     * 获取通用课程单元详情
     * @param lessonId
     * @return
     */
    @GetMapping(API_PREFIX + "/course/lesson/detail")
    R<CourseLesson> findCourseLessonById(@RequestParam("courseId") Integer lessonId);

    /**
     * 通用课程详情
     * @param courseId
     * @return
     */
    @GetMapping(API_PREFIX + "/course/detail")
    R<Course> findCourseById(@RequestParam("courseId") Integer courseId);

    /**
     * 课程星数配置
     * @param courseId
     * @return
     */
    @PostMapping(API_PREFIX + "/course/star")
    R<CourseStarConfig> findCourseStar(@RequestParam("courseId") Integer courseId);
}

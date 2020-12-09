package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.service.ICourseLessonService;
import cn.rzedu.sf.resource.service.ICourseService;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用课程
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/course")
@Api(value = "通用课程", tags = "通用课程")
public class CourseController {

    private ICourseService courseService;

    private ICourseLessonService courseLessonService;


    @GetMapping("/show/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "PC课程列表", notes = "PC课程列表")
    public R saveCourse(
            @ApiParam(value = "课程ids") @RequestParam(defaultValue = "1,5") String ids
    ) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (StringUtils.isBlank(ids)) {
            return R.data(list);
        }

        Map<String, Object> courseMap = null;
        List<Map<String, Object>> lessonList = null;
        Map<String, Object> LessonMap = null;
        List<Integer> idList = Func.toIntList(ids);
        for (Integer courseId : idList) {
            Course course = courseService.getById(courseId);
            if (course == null) {
                continue;
            }
            lessonList = new ArrayList<>();
            List<CourseLessonVO> lessonVOS = courseLessonService.findByCourseId(courseId);
            if (lessonVOS != null && !lessonVOS.isEmpty()) {
                for (CourseLessonVO lessonVO : lessonVOS) {
                    LessonMap = new HashMap<>(4);
//                    LessonMap.put("lessonId", lessonVO.getId());
                    LessonMap.put("lessonName", lessonVO.getName());
                    LessonMap.put("index", lessonVO.getLessonIndex());
                    LessonMap.put("videoId", lessonVO.getUuids());
                    lessonList.add(LessonMap);
                }
            }
            courseMap = new HashMap<>(4);
//            courseMap.put("courseId", courseId);
            courseMap.put("courseName", course.getName());
            courseMap.put("lessonCount", course.getLessonCount());
            courseMap.put("lessonList", lessonList);
            list.add(courseMap);
        }
        return R.data(list);
    }
}

package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.entity.CourseStarConfig;
import cn.rzedu.sf.resource.service.ICourseService;
import cn.rzedu.sf.resource.vo.CourseStarConfigVO;
import cn.rzedu.sf.resource.mapper.CourseStarConfigMapper;
import cn.rzedu.sf.resource.service.ICourseStarConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 通用课程星星数配置表 服务实现类
 *
 * @author Blade
 * @since 2020-09-09
 */
@AllArgsConstructor
@Service
public class CourseStarConfigServiceImpl extends ServiceImpl<CourseStarConfigMapper, CourseStarConfig> implements ICourseStarConfigService {

    private ICourseService courseService;
    /**
     * 配置1
     */
    private static final Integer[] CONFIG_N_1 = {3, 10, 40, 65, 70, 100};
    /**
     * 配置2
     */
    private static final Integer[] CONFIG_N_2 = {5, 6, 10, 50, 83, 90};
    /**
     * 配置3
     */
    private static final Integer[] CONFIG_N_3 = {10, 42, 58, 60, 80, 100};
    /**
     * 配置4
     */
    private static final Integer[] CONFIG_N_4 = {5, 10, 30, 40, 60, 90};


    @Override
    public IPage<CourseStarConfigVO> selectCourseStarConfigPage(IPage<CourseStarConfigVO> page,
																CourseStarConfigVO courseStarConfig) {
        return page.setRecords(baseMapper.selectCourseStarConfigPage(page, courseStarConfig));
    }

    @Override
    public CourseStarConfig findByCourseId(Integer courseId) {
        CourseStarConfig config = baseMapper.findByCourseId(courseId);
        if (config != null) {
            return config;
        }

        Course course = courseService.getById(courseId);
        if (course == null) {
            return null;
        }
        Integer lessonCount = course.getLessonCount();
        Integer[] numbers = new Integer[6];
        int i = (int) (Math.random() * 4 + 1);
        switch (i) {
            case 1 : numbers = CONFIG_N_1; break;
            case 2 : numbers = CONFIG_N_2; break;
            case 3 : numbers = CONFIG_N_3; break;
            case 4 : numbers = CONFIG_N_4; break;
        }

        Integer percentF1 = numbers[0];
        Integer percentF2 = numbers[1];
        Integer percentF3 = numbers[2];
        Integer percentF4 = numbers[3];
        Integer percentF5 = numbers[4];
        Integer percentF6 = numbers[5];
        Integer starsN1 = lessonCount * numbers[0] / 100;
        Integer starsN2 = lessonCount * numbers[1] / 100;
        Integer starsN3 = lessonCount * numbers[2] / 100;
        Integer starsN4 = lessonCount * numbers[3] / 100;
        Integer starsN5 = lessonCount * numbers[4] / 100;
        Integer starsN6 = lessonCount * numbers[5] / 100;

        config = new CourseStarConfig();
        config.setCourseId(courseId);
        config.setPercentF1(percentF1);
        config.setPercentF2(percentF2);
        config.setPercentF3(percentF3);
        config.setPercentF4(percentF4);
        config.setPercentF5(percentF5);
        config.setPercentF6(percentF6);
        config.setStarsN1(starsN1);
        config.setStarsN2(starsN2);
        config.setStarsN3(starsN3);
        config.setStarsN4(starsN4);
        config.setStarsN5(starsN5);
        config.setStarsN6(starsN6);
        baseMapper.insert(config);
        return null;
    }
}

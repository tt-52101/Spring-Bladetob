package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.service.ITextbookLessonService;
import cn.rzedu.sf.resource.vo.LessonStarConfigVO;
import cn.rzedu.sf.resource.mapper.LessonStarConfigMapper;
import cn.rzedu.sf.resource.service.ILessonStarConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程星星数配置表 服务实现类
 *
 * @author Blade
 * @since 2020-08-11
 */
@AllArgsConstructor
@Service
public class LessonStarConfigServiceImpl extends ServiceImpl<LessonStarConfigMapper, LessonStarConfig> implements ILessonStarConfigService {

	private ITextbookLessonService textbookLessonService;
	/**
	 * 初始配置，第一课默认配置
	 */
	private static final Integer[] CONFIG_N_0 = {5, 40, 80};
	/**
	 * 配置1
	 */
	private static final Integer[] CONFIG_N_1 = {40, 70, 100};
	/**
	 * 配置2
	 */
	private static final Integer[] CONFIG_N_2 = {10, 50, 90};
	/**
	 * 配置3
	 */
	private static final Integer[] CONFIG_N_3 = {60, 80, 100};

	@Override
	public IPage<LessonStarConfigVO> selectLessonStarConfigPage(IPage<LessonStarConfigVO> page, LessonStarConfigVO lessonStarConfig) {
		return page.setRecords(baseMapper.selectLessonStarConfigPage(page, lessonStarConfig));
	}

	@Override
	public LessonStarConfig findByLessonId(Integer lessonId) {
		return baseMapper.findByLessonId(lessonId);
	}

	@Override
	public LessonStarConfig saveOrUpdateConfig(Integer lessonId, boolean isChange) {
		TextbookLesson lesson = textbookLessonService.getById(lessonId);
		if (lesson == null) {
			return null;
		}
		Integer section = lesson.getSection();
		Integer charCount = lesson.getCharCount();
		Integer[] numbers = new Integer[3];
		if (section == 1) {
			numbers = CONFIG_N_0;
		} else {
			int i = (int) (Math.random() * 3 + 1);
			switch (i) {
				case 1 : numbers = CONFIG_N_1; break;
				case 2 : numbers = CONFIG_N_2; break;
				case 3 : numbers = CONFIG_N_3; break;
			}
		}
		Integer percentF1 = numbers[0];
		Integer percentF2 = numbers[1];
		Integer percentF3 = numbers[2];
		Integer starsN1 = charCount * numbers[0] / 100;
		Integer starsN2 = charCount * numbers[1] / 100;
		Integer starsN3 = charCount * numbers[2] / 100;

		LessonStarConfig config = baseMapper.findByLessonId(lessonId);
		if (config == null) {
			config = new LessonStarConfig();
			config.setTextbookId(lesson.getTextbookId());
			config.setLessonId(lesson.getId());
			config.setSection(section);
			config.setPercentF1(percentF1);
			config.setPercentF2(percentF2);
			config.setPercentF3(percentF3);
			config.setStarsN1(starsN1);
			config.setStarsN2(starsN2);
			config.setStarsN3(starsN3);
			baseMapper.insert(config);
		} else if (isChange) {
			config.setPercentF1(percentF1);
			config.setPercentF2(percentF2);
			config.setPercentF3(percentF3);
			config.setStarsN1(starsN1);
			config.setStarsN2(starsN2);
			config.setStarsN3(starsN3);
			baseMapper.updateById(config);
		}
		return config;
	}
}

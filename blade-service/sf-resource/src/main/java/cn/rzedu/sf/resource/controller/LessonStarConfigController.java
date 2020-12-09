package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.service.ITextbookLessonService;
import cn.rzedu.sf.resource.service.ITextbookService;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.vo.LessonStarConfigVO;
import cn.rzedu.sf.resource.wrapper.LessonStarConfigWrapper;
import cn.rzedu.sf.resource.service.ILessonStarConfigService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 * 课程星星数配置表 控制器
 *
 * @author Blade
 * @since 2020-08-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/lessonStarConfig")
@Api(value = "课程星数配置", tags = "课程星数配置")
public class LessonStarConfigController extends BladeController {

	private ILessonStarConfigService lessonStarConfigService;

	private ITextbookLessonService textbookLessonService;

	/**
	* 详情
	*/
	@GetMapping("/detail/{id}")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入lessonStarConfig")
	public R<LessonStarConfig> detail(@ApiParam(value = "课程id", required = true) @PathVariable(value = "id") Integer lessonId) {
		LessonStarConfig detail = lessonStarConfigService.findByLessonId(lessonId);
		return R.data(detail);
	}

	/**
	* 生成星数配置，单个课程或单个教材
	*/
	@PostMapping("/generate")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "生成星数配置", notes = "生成星数配置，单个课程或单个教材")
	public R generate(
			@ApiParam(value = "类型，1=课程,2=教材；默认1", required = true, defaultValue = "1")
				@RequestParam(defaultValue = "1") Integer type,
			@ApiParam(value = "id，课程id或教材id", required = true) @RequestParam Integer id,
			@ApiParam(value = "isChange，是否改变现有配置", defaultValue = "false") @RequestParam(defaultValue = "false") boolean isChange
	) {
		if (type == 1) {
			lessonStarConfigService.saveOrUpdateConfig(id, isChange);
		} else if (type == 2) {
			List<TextbookLessonVO> lessonList = textbookLessonService.findLessonByTextbookId(id);
			if (lessonList != null && !lessonList.isEmpty()) {
				lessonList.forEach(tlv -> lessonStarConfigService.saveOrUpdateConfig(tlv.getId(), isChange));
			}
		}
		return R.status(true);
	}
}

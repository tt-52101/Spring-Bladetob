package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.service.ICharacterRadicalService;
import cn.rzedu.sf.resource.service.ICharacterStructureService;
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
import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;
import cn.rzedu.sf.resource.wrapper.CharacterStrokeWrapper;
import cn.rzedu.sf.resource.service.ICharacterStrokeService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;
import java.util.Map;

/**
 * 汉字笔画 控制器
 *
 * @author Blade
 * @since 2020-12-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/character")
@Api(value = "汉字笔画、偏旁、结构", tags = "汉字笔画、偏旁、结构")
public class CharacterStrokeController extends BladeController {

	private ICharacterStrokeService characterStrokeService;

	private ICharacterRadicalService characterRadicalService;

	private ICharacterStructureService characterStructureService;

	/**
	 * 笔画列表
	 */
	@GetMapping("/stroke/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "笔画列表", notes = "笔画列表")
	public R strokeList() {
		List<Map<String, Object>> list = characterStrokeService.findAll();
		return R.data(list);
	}

	/**
	* 笔画详情
	*/
	@GetMapping("/stroke/detail")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "单个笔画", notes = "单个笔画")
	public R strokeDetail(@ApiParam(value = "名称", required = true) @RequestParam(value = "name") String name) {
		Map<String, Object> map = characterStrokeService.findByName(name);
		return R.data(map);
	}

	/**
	 * 偏旁列表
	 */
	@GetMapping("/radical/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "偏旁列表", notes = "偏旁列表")
	public R radicalList() {
		List<Map<String, Object>> list = characterRadicalService.findAll();
		return R.data(list);
	}

	/**
	 * 偏旁详情
	 */
	@GetMapping("/radical/detail")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "单个偏旁", notes = "单个偏旁")
	public R radicalDetail(@ApiParam(value = "名称", required = true) @RequestParam(value = "name") String name) {
		Map<String, Object> map = characterRadicalService.findByName(name);
		return R.data(map);
	}

	/**
	 * 结构列表
	 */
	@GetMapping("/structure/list")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "结构列表", notes = "结构列表")
	public R structureList() {
		List<Map<String, Object>> list = characterStructureService.findAll();
		return R.data(list);
	}

	/**
	 * 结构详情
	 */
	@GetMapping("/structure/detail")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "单个结构", notes = "单个结构")
	public R structureDetail(@ApiParam(value = "名称", required = true) @RequestParam(value = "name") String name) {
		Map<String, Object> map = characterStructureService.findByName(name);
		return R.data(map);
	}
}

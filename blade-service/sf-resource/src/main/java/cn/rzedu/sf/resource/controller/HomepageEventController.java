package cn.rzedu.sf.resource.controller;

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
import cn.rzedu.sf.resource.entity.HomepageEvent;
import cn.rzedu.sf.resource.vo.HomepageEventVO;
import cn.rzedu.sf.resource.wrapper.HomepageEventWrapper;
import cn.rzedu.sf.resource.service.IHomepageEventService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 首页轮播导航 控制器
 *
 * @author Blade
 * @since 2021-04-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/homepage/event")
@Api(value = "首页轮播导航", tags = "首页轮播导航")
public class HomepageEventController extends BladeController {

	private IHomepageEventService homepageEventService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "列表", notes = "根据类型获取")
	public R<List<HomepageEvent>> list(
			@ApiParam(value = "类型 1=轮播，2=导航", required = true) @RequestParam(defaultValue = "1") Integer type,
			@ApiParam(value = "是否展示全部") @RequestParam(required = false, defaultValue = "0") Integer isAll
			) {
		List<HomepageEvent> list = homepageEventService.findByType(type, isAll);
		return R.data(list);
	}

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "详情", notes = "根据id获取详情")
	public R<HomepageEvent> detail(@ApiParam(value = "id", required = true) @RequestParam Integer id) {
		HomepageEvent homepageEvent = homepageEventService.getById(id);
		return R.data(homepageEvent);
	}


	/**
	* 新增或修改 首页轮播导航
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "id不为空则修改，id为空则新增")
	public R submit(@Valid @RequestBody HomepageEvent homepageEvent) {
		if (homepageEvent.getId() == null && homepageEvent.getCreateDate() == null) {
			homepageEvent.setCreateDate(LocalDateTime.now());
		}
		return R.status(homepageEventService.saveOrUpdate(homepageEvent));
	}

	
	/**
	* 删除 首页轮播导航
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(homepageEventService.removeByIds(Func.toLongList(ids)));
	}

	
}

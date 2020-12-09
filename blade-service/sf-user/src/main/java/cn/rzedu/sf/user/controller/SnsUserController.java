package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.user.utils.PushMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springblade.common.props.WeChatProperties;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.user.entity.SnsUser;
import cn.rzedu.sf.user.vo.SnsUserVO;
import cn.rzedu.sf.user.wrapper.SnsUserWrapper;
import cn.rzedu.sf.user.service.ISnsUserService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 * 少年说--用户报名信息 控制器
 *
 * @author Blade
 * @since 2020-11-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sns/user")
@Api(value = "少年说用户", tags = "少年说用户")
public class SnsUserController extends BladeController {

	private ISnsUserService snsUserService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "根据openId获取用户")
	public R<SnsUserVO> detail(@ApiParam(value = "微信openId", required = true) @RequestParam String openId) {
		SnsUserVO snsUserVO = snsUserService.findByOpenId(openId);
		return R.data(snsUserVO);
	}

	/**
	* 新增/修改 报名信息
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "新增/修改", notes = "传入snsUser")
	public R<SnsUser> saveOrUpdate(@Valid @RequestBody SnsUser snsUser) {
		if (snsUser == null) {
			return R.fail("用户对象为空");
		}
		String openId = snsUser.getOpenId();
		if (StringUtils.isBlank(openId)) {
			return R.fail("openId为空");
		}
		SnsUser result = snsUserService.saveOrUpdate(openId, snsUser);
		if (result != null) {
		    PushMessageUtil.sendSnsApplySuccessMessage(openId, result.getName(), result.getMobile(),
					"Fi0URf2FG5ehjs0Bt7j8fKhLQDa1GThXlnbDn7WgRtA");
        }
		return R.data(result);
	}

}

package cn.rzedu.sc.goods.controller;

import cn.rzedu.sc.goods.service.IBuriedPointUserService;
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
import cn.rzedu.sc.goods.entity.BuriedPoint;
import cn.rzedu.sc.goods.vo.BuriedPointVO;
import cn.rzedu.sc.goods.wrapper.BuriedPointWrapper;
import cn.rzedu.sc.goods.service.IBuriedPointService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商城埋点 控制器
 *
 * @author Blade
 * @since 2020-12-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/buriedPoint")
@Api(value = "埋点", tags = "埋点接口")
public class BuriedPointController extends BladeController {

    private IBuriedPointService buriedPointService;

    private IBuriedPointUserService buriedPointUserService;

    /**
     * 详情
     */
    @PostMapping("/groupon")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "三人成团埋点", notes = "三人成团埋点")
    public R groupon(
            @ApiParam(value = "埋点类型  1=团购课程  2=三人拼团", required = true) @RequestParam Integer type,
            @ApiParam(value = "团购课程id", required = true) @RequestParam Integer grouponRuleId,
            @ApiParam(value = "浏览的用户id", required = true) @RequestParam Integer userId
    ) {
        BuriedPoint buriedPoint = buriedPointService.findByType(type, grouponRuleId, LocalDateTime.now());
        boolean valid = buriedPointUserService.insertBuriedUser(buriedPoint.getId(), userId);
        BuriedPoint bp = new BuriedPoint();
        bp.setId(buriedPoint.getId());
        bp.setVisitCount(buriedPoint.getVisitCount() + 1);
        if (valid) {
            bp.setVisitPeopleCount(buriedPoint.getVisitPeopleCount() +1);
        }
        buriedPointService.updateById(bp);
        return R.status(true);
    }

}

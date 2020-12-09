package cn.rzedu.sc.goods.controller;

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
import cn.rzedu.sc.goods.entity.Goods;
import cn.rzedu.sc.goods.vo.GoodsVO;
import cn.rzedu.sc.goods.wrapper.GoodsWrapper;
import cn.rzedu.sc.goods.service.IGoodsService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品 控制器
 *
 * @author Blade
 * @since 2020-10-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/goods")
@Api(value = "商品业务", tags = "商品业务")
public class GoodsController extends BladeController {

    private IGoodsService goodsService;

    /**
     * 通用课程商品
     */
    @GetMapping("/list/course")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "通用课程商品", notes = "通用课程商品")
    public R<List<Goods>> listCourse() {
        List<Goods> list = goodsService.findCourseGoods();
        return R.data(list);
    }


    /**
     * 新增商品
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增商品", notes = "新增商品，商品code不重复")
    public R save(@Valid @RequestBody Goods goods) {
        boolean isExist = goodsService.judgeRepeatByCode(goods.getCode(), null);
        if (isExist) {
            return R.fail("商品编号已存在");
        }
        goods.setCreateDate(LocalDateTime.now());
        return R.status(goodsService.save(goods));
    }

}

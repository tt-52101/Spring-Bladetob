package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.user.service.IUserLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.vo.UserVO;
import cn.rzedu.sf.user.wrapper.UserWrapper;
import cn.rzedu.sf.user.service.IUserService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 * 控制器
 *
 * @author Blade
 * @since 2020-08-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "书法系统的用户 ", tags = " 书法系统的用户")
public class UserController extends BladeController {

    private IUserService userService;

    private IUserLoginLogService userLoginLogService;

    /**
     * 用户详情
     */
    @GetMapping("/detail/{userId}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "用户详情", notes = "传入userId")
    public R<UserVO> detail(@ApiParam(value = "用户userId", required = true) @PathVariable(value = "userId") Integer userId) {
        User detail = userService.getById(userId);
        UserVO userVO = null;
        if (detail != null) {
            userVO = UserWrapper.build().entityVO(detail);
            int stars = userService.getUserStars(userId);
            userVO.setStarCount(stars);
        }
        return R.data(userVO);
    }


    /**
     * 修改用户信息
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "修改用户信息", notes = "传入user")
    public R update(@Valid @RequestBody User user) {
        return R.status(userService.updateById(user));
    }


    /**
     * 新增用户
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增用户", notes = "传入user")
    public R save(@Valid @RequestBody User user) {
        user.setCreateDate(LocalDateTime.now());
        user.setModifyDate(LocalDateTime.now());
        return R.status(userService.save(user));
    }

    /**
     * 修改用户就读年级
     * @param uuid
     * @return
     */
    @PostMapping("/modify/studyGrade")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "修改用户就读年级", notes = "修改用户就读年级，用于更改软笔权限")
    public R modifyStudyGrade(
            @ApiParam(value = "用户uuid", required = true) @RequestParam String uuid,
            @ApiParam(value = "就读年级, 21=一年级 22=二年级 23=三年级 24=四年级 25=五年级 26=六年级", required = true) @RequestParam String studyGrade
    ) {
        return R.status(userService.updateStudyGrade(uuid, studyGrade));
    }

    /**
     * 根据微信openid获取用户信息
     * @param openId
     * @return
     */
    @ApiIgnore
    @GetMapping("/detail/openId1")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "根据微信openid获取用户信息", notes = "根据微信openid获取用户信息")
    public R<UserVO> userDetail1(@ApiParam(value = "微信用户openId", required = true) @RequestParam String openId) {
        UserVO userVO = userService.findByOpenId(openId);
        return R.data(userVO);
    }

    /**
     * 根据微信openid获取用户信息
     * @param openId
     * @return
     */
    @PostMapping("/detail/openId2")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "根据微信openid获取用户信息", notes = "根据微信openid获取用户信息，无用户则新增，有则同步用户名和头像地址")
    public R<UserVO> userDetail2(
            @ApiParam(value = "微信用户openId", required = true) @RequestParam String openId,
            @ApiParam(value = "微信用户昵称", required = true) @RequestParam String name,
            @ApiParam(value = "微信用户头像地址", required = true) @RequestParam String headImgUrl
    ) {
        UserVO userVO = userService.findByOpenId(openId, name, headImgUrl);
        return R.data(userVO);
    }


    /**
     * 各类型用户
     */
    @GetMapping("/type/list")
    @ApiOperationSupport(order = 13)
    @ApiOperation(value = "各类型用户", notes = "各类型用户")
    public R allTypeUser(
            @ApiParam(value = "是否更新用户类型") @RequestParam(required = false, defaultValue = "false") Boolean isUpdate
    ) {
        Map<String, Object> result = userService.findAllTypeUser(isUpdate);
        return R.data(result);
    }

    /**
     * 添加用户登录日志
     */
    @PostMapping("/login/save")
    @ApiOperationSupport(order = 14)
    @ApiOperation(value = "添加用户登录日志", notes = "添加用户登录日志")
    public R allTypeUser(@ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
                         @ApiParam(value = "登录来源") @RequestParam(required = false, defaultValue = "0") Integer loginSource) {
        boolean result = userLoginLogService.addCurrentDayLogin(userId, loginSource);
        return R.data(result);
    }

    /**
     * 随机用户
     */
    @GetMapping("/list/random")
    @ApiOperationSupport(order = 15)
    @ApiOperation(value = "随机用户", notes = "随机用户")
    public R randomUsers(@ApiParam(value = "数量") @RequestParam(required = false, defaultValue = "20") Integer number) {
        List<User> list = userService.getRandomUser(number);
        List<Map<String, Object>> result = transferUser(list);
        return R.data(result);
    }

    private List<Map<String, Object>> transferUser(List<User> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        for (User user : list) {
            map = new HashMap<>(2);
            map.put("name", user.getName());
            map.put("icon", user.getIcon());
            result.add(map);
        }
        return result;
    }

}

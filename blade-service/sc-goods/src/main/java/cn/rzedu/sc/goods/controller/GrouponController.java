package cn.rzedu.sc.goods.controller;

import cn.rzedu.sc.goods.entity.*;
import cn.rzedu.sc.goods.service.*;
import cn.rzedu.sc.goods.vo.GoodsSkuVO;
import cn.rzedu.sc.goods.vo.GrouponGroupUserVO;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
import cn.rzedu.sc.goods.vo.GrouponGroupVO;
import cn.rzedu.sc.goods.wrapper.GrouponGroupWrapper;
import org.springblade.core.boot.ctrl.BladeController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 团购 控制器
 *
 * @author Blade
 * @since 2020-10-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/groupon")
@Api(value = "团购业务", tags = "团购业务")
public class GrouponController extends BladeController {

    private IGoodsService goodsService;

    private IGoodsSkuService goodsSkuService;

    private IGrouponRuleService grouponRuleService;

    private IGrouponGroupService grouponGroupService;

    private IGrouponGroupUserService grouponGroupUserService;

    private IGrouponGroupGoodsService grouponGroupGoodsService;

    private IOrderService orderService;

    /**
     * 团购课程列表
     *
     * @param query
     * @return
     */
    @GetMapping("/rule/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "团购课程列表", notes = "团购课程列表")
    public R<IPage<GrouponRuleVO>> ruleList(
            @ApiParam(value = "课程ID/名称") @RequestParam(value = "goodsName", required = false) String goodsName,
            @ApiParam(value = "价格") @RequestParam(value = "primePrice", required = false) BigDecimal primePrice,
            @ApiParam(value = "成团价") @RequestParam(value = "groupPrice", required = false) BigDecimal groupPrice,
            Query query) {
        GrouponRuleVO grouponRule = new GrouponRuleVO();
        grouponRule.setGoodsName(goodsName);
        grouponRule.setPrimePrice(primePrice);
        grouponRule.setGroupPrice(groupPrice);
        IPage<GrouponRuleVO> pages = grouponRuleService.findByGrouponRuleVO(Condition.getPage(query), grouponRule);
        setMaterialCount(pages);
        return R.data(pages);
    }


    private void setMaterialCount(IPage<GrouponRuleVO> pages) {
        List<GrouponRuleVO> list = pages.getRecords();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (GrouponRuleVO vo : list) {
            Integer materialNumber = 0;
            //统计files里素材material数量
            String files = vo.getFiles();
            if (StringUtils.isNotBlank(files)) {
                JSONObject jsonObject = JSON.parseObject(files);
                String material = jsonObject.getString("material");
                if (StringUtils.isNotBlank(material)) {
                    JSONArray jsonArray = JSON.parseArray(material);
                    materialNumber = jsonArray.size();
                }
            }
            vo.setMaterialNumber(materialNumber);
        }
        pages.setRecords(list);
    }

    /**
     * 团购课程详情
     */
    @GetMapping("/rule/detail/{id}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "团购课程详情", notes = "团购课程详情，含购买人数")
    public R<GrouponRuleVO> ruleDetail(@ApiParam(value = "团购课程id", required = true) @PathVariable(value = "id") Integer id) {
        GrouponRuleVO grouponRuleVO = grouponRuleService.findDetailById(id);
        addSkuList(grouponRuleVO);
        setRandomBoughtCount(grouponRuleVO);
        return R.data(grouponRuleVO);
    }

    private void addSkuList(GrouponRuleVO grouponRuleVO) {
        if (grouponRuleVO != null) {
            Boolean hasSku = grouponRuleVO.getHasSku();
            if (hasSku != null && hasSku) {
                List<GoodsSkuVO> skuList = goodsSkuService.findByGoodsId(grouponRuleVO.getGoodsId());
                grouponRuleVO.setSkuList(skuList);
            }
        }
    }

    //设置随机购买人数
    private void setRandomBoughtCount(GrouponRuleVO grouponRuleVO) {
        if (grouponRuleVO != null) {
            Integer boughtCount = grouponRuleVO.getBoughtCount();
            if (boughtCount == null) {
                boughtCount = 0;
            }
            boughtCount += (int) (Math.random() * 400 + 1) + 700;
            grouponRuleVO.setBoughtCount(boughtCount);
        }
    }

    /**
     * 新增团购课程
     */
    @PostMapping("/rule/save")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增团购课程", notes = "新增团购课程。" +
            "适合年龄段age、总课程数lessonCount，以json格式存在attributes里；长图longPicture、素材material，以json格式存在files里。\n" +
            "\"attributes\" : { \"age\" : \"10~16\", \"lessonCount\" : 15 },\n" +
            "\"files\" : { \"longPicture\" : \"52a7888b07e84b16bf5d5e01d0daa057\",\"material\" " +
            ":[{\"uuid\":\"52a7888b07e84b16bf5d5e01d0daa057\",\"type\":\"video\"}] }")
    public R ruleSave(@Valid @RequestBody GrouponRule grouponRule) {
        Integer goodsId = grouponRule.getGoodsId();
        if (goodsId == null) {
            return R.fail("课程不能为空");
        }
        String name = "";
        Goods goods = goodsService.getById(goodsId);
        if (goods != null) {
            name = goods.getName() + "--三人成团";
        }
        grouponRule.setName(name);
        grouponRule.setMemberCount(3);
        grouponRule.setCreateDate(LocalDateTime.now());
        return R.status(grouponRuleService.save(grouponRule));
    }

    /**
     * 修改团购课程
     */
    @PostMapping("/rule/update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "修改团购课程", notes = "修改团购课程")
    public R ruleUpdate(@Valid @RequestBody GrouponRule grouponRule) {
        Integer memberCount = grouponRule.getMemberCount();
        if (memberCount != null && memberCount == 0) {
            memberCount = 3;
            grouponRule.setMemberCount(memberCount);
        }
        return R.status(grouponRuleService.updateById(grouponRule));
    }

    /**
     * 删除团购课程
     */
    @PostMapping("/rule/remove")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除团购课程", notes = "删除团购课程")
    public R ruleRemove(@ApiParam(value = "id集合", required = true) @RequestParam String ids) {
        return R.status(grouponRuleService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 推荐好课
     * @return
     */
    @PostMapping("/rule/recommend")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "推荐好课", notes = "推荐好课")
    public R<List<GrouponRuleVO>> recommendCourse(@ApiParam(value = "需排除的课程id") @RequestParam(required = false) Integer id) {
        List<GrouponRuleVO> list = grouponRuleService.findGrouponRules(id);
        setRandomBoughtCount(list);
        return R.data(list);
    }

    private void setRandomBoughtCount (List<GrouponRuleVO> list) {
        if (list != null && !list.isEmpty()) {
            for (GrouponRuleVO grouponRuleVO : list) {
                setRandomBoughtCount(grouponRuleVO);
            }
        }
    }


    /**
     * 倒数参团列表
     *
     * 默认3个团，可配置。1个真实团+X个机器团
     * 真实团只出现倒计时6小时以内的，没有真实团则全用机器团，机器团开始到结束只有6小时
     * 机器团随机已有1~2个机器人用户
     * 每个团需包含发起人昵称、头像（机器团也一样），课程（商品）内容，软硬笔需标明年级
     * 最后第23小时（离结束还有1小时）补充机器人让用户成团
     * @return
     */
    @PostMapping("/group/list")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "倒数参团列表", notes = "倒数参团列表")
    public R<List<GrouponGroupVO>> groupList(
            @ApiParam(value = "团购课程id", required = true) @RequestParam Integer grouponRuleId,
            @ApiParam(value = "参团数量") @RequestParam(required = false, defaultValue = "3") Integer number,
            @ApiParam(value = "拼团来源（埋点用）") @RequestParam(required = false, defaultValue = "0") Integer grouponSource
    ) {
        List<GrouponGroupVO> list = new ArrayList<>();
        //真实团数量
        Integer realNumber = 1;
        GrouponGroupVO realGroup = grouponGroupService.findOneDeadlineGroup(grouponRuleId);
        if (realGroup != null) {
            list.add(realGroup);
        } else {
            realNumber = 0;
        }

        //机器团数量
        Integer robotNumber = number - realNumber;
        //需新增的机器团数量
        Integer newRobotNumber = 0;

        //查找机器团，没有或不足则生成
        List<GrouponGroupVO> robotGroupList = grouponGroupService.findRobotGroup(grouponRuleId);
        if (robotGroupList == null || robotGroupList.isEmpty()) {
            newRobotNumber = robotNumber;
        } else {
            int size = robotGroupList.size();
            if (size >= robotNumber) {
                for (int i = 0; i < robotNumber; i++) {
                    list.add(robotGroupList.get(i));
                }
            } else {
                newRobotNumber = robotNumber - size;
                list.addAll(robotGroupList);
            }
        }

        //生成机器团
        if (newRobotNumber != 0) {
            for (Integer i = 0; i < newRobotNumber; i++) {
                GrouponGroupVO vo = grouponGroupService.createRobotGroup(grouponRuleId, grouponSource);
                list.add(vo);
            }
        }
        return R.data(list);
    }


    /**
     * 拼团详情
     */
    @PostMapping("/group/detail")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "拼团详情", notes = "拼团详情")
    public R<GrouponGroupVO> groupDetail(@ApiParam(value = "拼团id", required = true) @RequestParam Integer grouponGroupId) {
        GrouponGroupVO grouponGroupVO = grouponGroupService.findGroupDetail(grouponGroupId);
        return R.data(grouponGroupVO);
    }


    /**
     * 购买/发起团购/参与团购
     * @param userId
     * @param grouponRuleId
     * @param type
     * @param grouponGroupId
     * @return
     */
    @PostMapping("/group/buy")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "购买/发起团购/参与团购", notes = "先校验数据，成功后会返回拼团id。单独购买返回0")
    public R<Map<String, Object>> bugCourse(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "团购课程id", required = true) @RequestParam Integer grouponRuleId,
            @ApiParam(value = "购买类型 1=单独购买 2=发起团购 3=参与团购", required = true) @RequestParam Integer type,
            @ApiParam(value = "团购课程skuId，多个逗号隔开，用于软硬笔课程") @RequestParam(required = false) String skuIds,
            @ApiParam(value = "拼团id，当参与团购时为必填项") @RequestParam(required = false) Integer grouponGroupId,
            @ApiParam(value = "拼团来源（埋点用）") @RequestParam(required = false, defaultValue = "0") Integer grouponSource
    ) {
        if (type == 3 && grouponGroupId == null) {
            return R.fail("参与团购需填写拼团id");
        }
        GrouponRuleVO grouponRuleVO = grouponRuleService.findDetailById(grouponRuleId);
        if (grouponRuleVO == null) {
            return R.fail("团购商品不存在");
        }
        if (2 == grouponRuleVO.getOwnerType() && StringUtils.isBlank(skuIds)) {
            return R.fail("软硬笔课程需填写sku");
        }
        //购买方式  1=单购 2=团购
        Integer purchaseType = 1;
        if (type != 1) {
            purchaseType = 2;
        }

        //团购业务
        GrouponGroupVO grouponGroupVO = null;
        if (type == 2) {
            grouponGroupVO = grouponGroupService.createNormalGroup(grouponRuleId, userId, skuIds, grouponSource);
            grouponGroupId = grouponGroupVO.getId();
        } else if (type == 3) {
            //拼团
            GrouponGroup grouponGroup = grouponGroupService.getById(grouponGroupId);
            if (grouponGroup == null) {
                return R.fail("获取不到拼团数据");
            }
            if (grouponGroup.getStatus() != 0) {
                return R.success("拼团已结束，不能参与");
            }
            //成团人数
            Integer memberCount = grouponRuleVO.getMemberCount();
            //已参团人数
            Integer joinCount = grouponGroup.getMemberCount();
            if (joinCount >= memberCount) {
                return R.success("拼团失败，人数已满");
            }
            //判断是否已参团
            List<GrouponGroupUserVO> groupUserList = grouponGroupUserService.findByGrouponGroupId(grouponGroupId);
            if (groupUserList != null && !groupUserList.isEmpty()) {
                for (GrouponGroupUserVO vo : groupUserList) {
                    if (vo.getUserId().equals(userId)) {
                        return R.success("用户已经参与拼团");
                    }
                }
            }

            //参与平团业务
            grouponGroupVO = joinGroup(userId, skuIds, grouponGroupId, grouponRuleVO, grouponGroup, memberCount, joinCount);
        }

        //生成订单，未支付
        Order order = orderService.createOrderByGroupon(userId, grouponRuleVO, purchaseType, 1, skuIds, grouponGroupId, 1);

        Map<String, Object> result = new HashMap<>();
        result.put("grouponId", grouponRuleId);
        result.put("grouponGroupId", grouponGroupId);
        result.put("goodsId", grouponRuleVO.getGoodsId());
        result.put("goodsName", grouponRuleVO.getGoodsName());
        result.put("skuIds", skuIds);
        result.put("orderId", order.getId());
        result.put("orderCode", order.getCode());
        return R.data(result);
    }

    private GrouponGroupVO joinGroup(Integer userId, String skuIds, Integer grouponGroupId,
                                     GrouponRuleVO grouponRuleVO, GrouponGroup grouponGroup,
                                     Integer memberCount, Integer joinCount) {
        LocalDateTime now = LocalDateTime.now();
        GrouponGroupUser groupUser = new GrouponGroupUser();
        groupUser.setGrouponGroupId(grouponGroupId);
        groupUser.setUserId(userId);
        groupUser.setCreateDate(now);
        grouponGroupUserService.save(groupUser);

        if (StringUtils.isBlank(skuIds)) {
            GrouponGroupGoods groupGoods = new GrouponGroupGoods();
            groupGoods.setGrouponGroupId(grouponGroupId);
            groupGoods.setGoodsId(grouponRuleVO.getGoodsId());
            groupGoods.setCount(1);
            groupGoods.setPrice(grouponRuleVO.getGroupPrice());
            groupGoods.setCreateDate(now);
            grouponGroupGoodsService.save(groupGoods);
        } else {
            List<Integer> skuIdList = Func.toIntList(skuIds);
            for (Integer skuId : skuIdList) {
                GrouponGroupGoods groupGoods = new GrouponGroupGoods();
                groupGoods.setGrouponGroupId(grouponGroupId);
                groupGoods.setGoodsId(grouponRuleVO.getGoodsId());
                groupGoods.setGoodsSkuId(skuId);
                groupGoods.setCount(1);
                groupGoods.setPrice(grouponRuleVO.getGroupPrice());
                groupGoods.setCreateDate(now);
                grouponGroupGoodsService.save(groupGoods);
            }
        }

        //修改参团人数、参团状态
        joinCount += 1;
        Integer status = 0;
        if (joinCount.equals(memberCount)) {
            status = 1;
        }
        grouponGroup.setMemberCount(joinCount);
        grouponGroup.setStatus(status);
        grouponGroupService.updateById(grouponGroup);

        return GrouponGroupWrapper.build().entityVO(grouponGroup);
    }


    /**
     * 自动成团
     * 期限在1小时内的自动成团
     * @return
     */
    @PostMapping("/group/complete")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "自动成团", notes = "自动成团")
    public R autoCompleteGroup() {
        grouponGroupService.autoCompleteGroupOneHourLeft();
        return R.status(true);
    }

    /**
     * 拼团人数不足消息提示
     * @return
     */
    @PostMapping("/send/lackMessage")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "拼团人数不足消息提示", notes = "拼团人数不足消息提示")
    public R lackMessage(
            @ApiParam(value = "离结束的时间，单位：秒", required = true) @RequestParam(defaultValue = "21600") Long greaterTime,
            @ApiParam(value = "需大于的时间，单位：秒", required = true) @RequestParam(defaultValue = "0") Long lessTime) {
        grouponGroupService.sendMessageToTimeLackGroup(greaterTime, lessTime);
        return R.status(true);
    }


}

package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.*;
import cn.rzedu.sc.goods.service.*;
import cn.rzedu.sc.goods.utils.PushMessageUtil;
import cn.rzedu.sc.goods.utils.SkuUtil;
import cn.rzedu.sc.goods.vo.*;
import cn.rzedu.sc.goods.mapper.GrouponGroupMapper;
import cn.rzedu.sc.goods.wrapper.GrouponGroupWrapper;
import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.feign.IUserLessonClient;
import cn.rzedu.sf.user.vo.UserVO;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 团购成团记录。针对某个团购的形成的一个团的总体情况 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
@AllArgsConstructor
public class GrouponGroupServiceImpl extends ServiceImpl<GrouponGroupMapper, GrouponGroup> implements IGrouponGroupService {

    private IGrouponGroupUserService grouponGroupUserService;

    private IGrouponGroupGoodsService grouponGroupGoodsService;

    private IGrouponRuleService grouponRuleService;

    private IGoodsSkuService goodsSkuService;

    private IUserLessonClient userLessonClient;

    private IGoodsService goodsService;

    private ISFUserClient userClient;

    private final static String GROUPON_JOIN_TEMPLATE = "GvqntvNC53pDYDT_lJQasiOeiTybD-ITFn3HuWLs5Hg";
    private final static String GROUPON_SUCCESS_TEMPLATE = "7tao3SerMDOTY1_7CYewIOtnGyIpZAOU5PcXGmwkojk";
    private final static String GROUPON_MEMBER_LACK_TEMPLATE = "nqGKLcQoGtJOZtK_fdFWsLPtqAryHOdrxX9gmFJBxWs";

    @Override
    public IPage<GrouponGroupVO> selectGrouponGroupPage(IPage<GrouponGroupVO> page, GrouponGroupVO grouponGroup) {
        return page.setRecords(baseMapper.selectGrouponGroupPage(page, grouponGroup));
    }

    @Override
    public GrouponGroupVO findOneDeadlineGroup(Integer grouponId) {
        List<GrouponGroupVO> list = baseMapper.findDeadlineGroup(grouponId, 6);
        if (list == null || list.isEmpty()) {
            return null;
        }
        GrouponGroupVO grouponGroup = list.get(0);
        //添加参团人数据
        addGroupUserList(grouponGroup);
        //添加商品sku数据，软硬笔课程才需要
        addGroupSku(grouponGroup);
        return grouponGroup;
    }

    private void addGroupUserList(GrouponGroupVO grouponGroup) {
        if (grouponGroup != null) {
            List<GrouponGroupUserVO> groupUserList = grouponGroupUserService.findByGrouponGroupId(grouponGroup.getId());
            grouponGroup.setGroupUserList(groupUserList);
        }
    }

    //软硬笔需提取年级放入商品名中
    private void addGroupSku(GrouponGroupVO grouponGroup) {
        if (grouponGroup != null && grouponGroup.getOwnerType() == 2) {
            List<GrouponGroupGoodsVO> goodsList = grouponGroupGoodsService.findByGrouponGroupId(grouponGroup.getId());
            String grade = "";
            if (goodsList != null && !goodsList.isEmpty()) {
                for (GrouponGroupGoodsVO vo : goodsList) {
                    grade = getGrade(vo.getGoodsSkuName());
                    String extractSkuName = SkuUtil.extractSkuName(vo.getGoodsSkuName());
                    vo.setGoodsSkuName(extractSkuName);
                }
            }
            grouponGroup.setGroupGoodsList(goodsList);
            grouponGroup.setGoodsName(grade + " " + grouponGroup.getGoodsName());
        }
    }

    private String getGrade(String skuName) {
        String grade = "";
        if (StringUtils.isBlank(skuName)) {
            return grade;
        }
        if (skuName.contains("一年级")) {
            grade = "一年级";
        } else if (skuName.contains("二年级")) {
            grade = "二年级";
        } else if (skuName.contains("三年级")) {
            grade = "三年级";
        } else if (skuName.contains("四年级")) {
            grade = "四年级";
        } else if (skuName.contains("五年级")) {
            grade = "五年级";
        } else if (skuName.contains("六年级")) {
            grade = "六年级";
        }
        return grade;
    }

    @Override
    public List<GrouponGroupVO> findRobotGroup(Integer grouponId) {
        List<GrouponGroupVO> list = baseMapper.findRobotGroup(grouponId);
        if (list != null && !list.isEmpty()) {
            for (GrouponGroupVO grouponGroup : list) {
                addGroupUserList(grouponGroup);
                addGroupSku(grouponGroup);
            }
        }
        return list;
    }

    @Override
    public GrouponGroupVO createRobotGroup(Integer grouponId, Integer source) {
        List<Map<String, Object>> robotList = baseMapper.getRobotUser();
        GrouponRuleVO grouponRule = grouponRuleService.findDetailById(grouponId);
        if (grouponRule == null) {
            return null;
        }
        //随机参团人数1~2
//        int memberCount = (int) (Math.random() * 2 + 1);
        int memberCount = 2;
        Integer launchUserId = Integer.parseInt(robotList.get(0).get("id").toString());

        LocalDateTime now = LocalDateTime.now();
        //机器团存在时间只有6小时
        LocalDateTime finishTime = now.plusHours(6);

        //新增团
        GrouponGroup grouponGroup = new GrouponGroup();
        grouponGroup.setGrouponId(grouponId);
        grouponGroup.setLaunchUserId(launchUserId);
        grouponGroup.setLaunchTime(now);
        grouponGroup.setFinishTime(finishTime);
        grouponGroup.setMemberCount(memberCount);
        grouponGroup.setStatus(0);
        grouponGroup.setIsRobot(1);
        grouponGroup.setCreateDate(now);
        grouponGroup.setSource(source);
        baseMapper.insert(grouponGroup);

        Integer grouponGroupId = grouponGroup.getId();

        //新增参团成员
        for (int i = 0; i < memberCount; i++) {
            GrouponGroupUser groupUser = new GrouponGroupUser();
            groupUser.setGrouponGroupId(grouponGroupId);
            groupUser.setUserId(Integer.parseInt(robotList.get(i).get("id").toString()));
            groupUser.setCreateDate(now);
            grouponGroupUserService.save(groupUser);
        }

        //新增参团商品，通用课程建1条，软硬笔需建多条
        Integer ownerType = grouponRule.getOwnerType();
        if (ownerType == 2) {
            //随机年级
            int nj = (int) (Math.random() * 6 + 1);
            String njKV = "\"1\":\"" + nj +"\"";
            List<GoodsSkuVO> skuList = goodsSkuService.findByGoodsId(grouponRule.getGoodsId());
            List<GoodsSkuVO> choose = new ArrayList<>();
            for (GoodsSkuVO skuVO : skuList) {
                if (skuVO.getKeyValue().contains(njKV)) {
                    choose.add(skuVO);
                }
            }
            if (!choose.isEmpty()) {
                for (GoodsSkuVO vo : choose) {
                    GrouponGroupGoods groupGoods = new GrouponGroupGoods();
                    groupGoods.setGrouponGroupId(grouponGroupId);
                    groupGoods.setGoodsId(grouponRule.getGoodsId());
                    groupGoods.setGoodsSkuId(vo.getId());
                    groupGoods.setCount(1);
                    groupGoods.setPrice(grouponRule.getGroupPrice());
                    groupGoods.setCreateDate(now);
                    grouponGroupGoodsService.save(groupGoods);
                }
            }

        } else {
            GrouponGroupGoods groupGoods = new GrouponGroupGoods();
            groupGoods.setGrouponGroupId(grouponGroupId);
            groupGoods.setGoodsId(grouponRule.getGoodsId());
            groupGoods.setCount(1);
            groupGoods.setPrice(grouponRule.getGroupPrice());
            groupGoods.setCreateDate(now);
            grouponGroupGoodsService.save(groupGoods);
        }

        //组装数据
        GrouponGroupVO grouponGroupVO = findGroupDetail(grouponGroupId);
        return grouponGroupVO;
    }

    @Override
    public GrouponGroupVO findGroupDetail(Integer grouponGroupId) {
        GrouponGroupVO grouponGroupVO = baseMapper.findByGrouponGroupId(grouponGroupId);
        addGroupUserList(grouponGroupVO);
        addGroupSku(grouponGroupVO);
        return grouponGroupVO;
    }

    @Override
    public GrouponGroupVO createNormalGroup(Integer grouponId, Integer userId, String skuIds, Integer source) {
        GrouponRuleVO grouponRule = grouponRuleService.findDetailById(grouponId);
        if (grouponRule == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finishTime = now.plusDays(1);

        //新增团
        GrouponGroup grouponGroup = new GrouponGroup();
        grouponGroup.setGrouponId(grouponId);
        grouponGroup.setLaunchUserId(userId);
        grouponGroup.setLaunchTime(now);
        grouponGroup.setFinishTime(finishTime);
        grouponGroup.setMemberCount(1);
        grouponGroup.setStatus(0);
        grouponGroup.setIsRobot(0);
        grouponGroup.setCreateDate(now);
        grouponGroup.setSource(source);
        baseMapper.insert(grouponGroup);

        Integer grouponGroupId = grouponGroup.getId();

        //新增团成员
        GrouponGroupUser groupUser = new GrouponGroupUser();
        groupUser.setGrouponGroupId(grouponGroupId);
        groupUser.setUserId(userId);
        groupUser.setCreateDate(now);
        grouponGroupUserService.save(groupUser);

        //新增团商品
        if (StringUtils.isBlank(skuIds)) {
            GrouponGroupGoods groupGoods = new GrouponGroupGoods();
            groupGoods.setGrouponGroupId(grouponGroupId);
            groupGoods.setGoodsId(grouponRule.getGoodsId());
            groupGoods.setCount(1);
            groupGoods.setPrice(grouponRule.getGroupPrice());
            groupGoods.setCreateDate(now);
            grouponGroupGoodsService.save(groupGoods);
        } else {
            List<Integer> skuIdList = Func.toIntList(skuIds);
            for (Integer skuId : skuIdList) {
                GrouponGroupGoods groupGoods = new GrouponGroupGoods();
                groupGoods.setGrouponGroupId(grouponGroupId);
                groupGoods.setGoodsId(grouponRule.getGoodsId());
                groupGoods.setGoodsSkuId(skuId);
                groupGoods.setCount(1);
                groupGoods.setPrice(grouponRule.getGroupPrice());
                groupGoods.setCreateDate(now);
                grouponGroupGoodsService.save(groupGoods);
            }
        }

        GrouponGroupVO grouponGroupVO = GrouponGroupWrapper.build().entityVO(grouponGroup);
        return grouponGroupVO;
    }

    @Override
    public void autoCompleteGroupOneHourLeft() {
        List<GrouponGroupVO> groupList = baseMapper.findOneHourLeftGroup();
        List<Map<String, Object>> robotUserList = baseMapper.getRobotUser();
        if (groupList == null || groupList.isEmpty() || robotUserList == null || robotUserList.isEmpty()) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        for (GrouponGroupVO groupVO : groupList) {
            Integer memberCount = groupVO.getMemberCount();
            Integer needMemberCount = groupVO.getNeedMemberCount();

            //添加机器人用户
            if (memberCount < needMemberCount) {
                generateRobotGroupUser(needMemberCount - memberCount, groupVO.getId(), groupVO.getGroupUserIds(), robotUserList, now);
            }
            groupVO.setMemberCount(needMemberCount);
            groupVO.setStatus(1);
            groupVO.setModifyDate(now);
            baseMapper.updateById(groupVO);

            //生成课程
            generateUserCourse(groupVO.getId(), groupVO.getGrouponId());
        }
    }

    private void generateRobotGroupUser(Integer needCount, Integer grouponGroupId, String groupUserIds,
                                   List<Map<String, Object>> robotUserList, LocalDateTime now) {
        int finishedCount = 0;
        boolean match = false;
        List<Integer> ids = Func.toIntList(groupUserIds);
        for (Map<String, Object> map : robotUserList) {
            //groupUserIds的所有userId，都不等于时才能创建
            Integer userId = Integer.parseInt(map.get("id").toString());
            match = ids.stream().noneMatch(id -> id.equals(userId));
            if (match) {
                GrouponGroupUser groupUser = new GrouponGroupUser();
                groupUser.setGrouponGroupId(grouponGroupId);
                groupUser.setUserId(userId);
                groupUser.setCreateDate(now);
                grouponGroupUserService.save(groupUser);
                finishedCount++;
            }
            if (finishedCount == needCount) {
                break;
            }
        }
    }

    private void generateUserCourse(Integer grouponGroupId, Integer grouponId) {
        List<Integer> userIds = baseMapper.findNotRobotFinishGroupUser(grouponGroupId);
        List<GrouponGroupGoodsVO> goodsList = grouponGroupGoodsService.findByGrouponGroupId(grouponGroupId);
        if (userIds == null || userIds.isEmpty() || goodsList == null || goodsList.isEmpty()) {
            return;
        }
        Goods goods = null;
        Integer ownerType = null;
        GoodsSku goodsSku = null;
        for (Integer userId : userIds) {
            for (GrouponGroupGoodsVO goodsVO : goodsList) {
                goods = goodsService.getById(goodsVO.getGoodsId());
                if (goods == null) {
                    continue;
                }
                ownerType = goods.getOwnerType();
                if (1 == ownerType) {
                    //添加通用课程
                    userLessonClient.createCourse(userId, goods.getOwnerId());
                } else if (2 == ownerType && goodsVO.getGoodsSkuId() != null) {
                    //添加软硬笔
                    goodsSku = goodsSkuService.getById(goodsVO.getGoodsSkuId());
                    if (goodsSku != null) {
                        userLessonClient.createTextbook(userId, goodsSku.getOwnerId());
                    }
                }
            }
        }

        //发送成团成功消息
        sendMessage(grouponGroupId, grouponId, userIds);
    }

    //发送成团成功消息
    private void sendMessage(Integer grouponGroupId, Integer grouponId, List<Integer> userIds) {
        GrouponRuleVO grouponRule = grouponRuleService.findDetailById(grouponId);
        String name = "";
        List<GrouponGroupUserVO> groupUserList = grouponGroupUserService.findByGrouponGroupId(grouponGroupId);
        if (groupUserList != null && !groupUserList.isEmpty()) {
            name = groupUserList.get(groupUserList.size() - 1).getName();
        }
        for (Integer id : userIds) {
            PushMessageUtil.sendFinishGrouponMessage(GROUPON_SUCCESS_TEMPLATE,
                    getUserOpenId(id), name, grouponRule.getGoodsName(), grouponRule.getGroupPrice().toString());
        }
    }
    private String getUserOpenId(Integer userId) {
        R<UserVO> result = userClient.detail(userId);
        return result.getData().getOpenId();
    }

    @Override
    public List<Integer> findNotRobotFinishGroupUser(Integer grouponGroupId) {
        return baseMapper.findNotRobotFinishGroupUser(grouponGroupId);
    }

    @Override
    public void sendMessageToTimeLackGroup(Long greaterTime, Long lessTime) {
        List<GrouponGroupVO> groupList = baseMapper.findLimitTimeGroup(greaterTime, lessTime);
        if (groupList == null || groupList.isEmpty()) {
            return;
        }
        List<Integer> userIds = null;
        UserVO userVO = null;
        for (GrouponGroupVO groupVO : groupList) {
            userIds = Func.toIntList(groupVO.getGroupUserIds());
            int leftCount = groupVO.getNeedMemberCount() - groupVO.getMemberCount();
            if (userIds == null || userIds.isEmpty()) {
                continue;
            }
            for (Integer userId : userIds) {
                userVO = userClient.detail(userId).getData();
                if (StringUtils.isBlank(userVO.getOpenId())) {
                    continue;
                }
                PushMessageUtil.sendGrouponMemberLackMessage(GROUPON_MEMBER_LACK_TEMPLATE,
                        userVO.getOpenId(), userVO.getName(), groupVO.getGoodsName(), leftCount,
                        groupVO.getGrouponId(), groupVO.getId());
            }
        }
    }
}

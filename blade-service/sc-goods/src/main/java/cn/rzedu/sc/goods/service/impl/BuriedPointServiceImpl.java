package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.BuriedPoint;
import cn.rzedu.sc.goods.service.IBuriedPointUserService;
import cn.rzedu.sc.goods.vo.BuriedPointVO;
import cn.rzedu.sc.goods.mapper.BuriedPointMapper;
import cn.rzedu.sc.goods.service.IBuriedPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 商城埋点 服务实现类
 *
 * @author Blade
 * @since 2020-12-02
 */
@Service
@AllArgsConstructor
public class BuriedPointServiceImpl extends ServiceImpl<BuriedPointMapper, BuriedPoint> implements IBuriedPointService {

    private IBuriedPointUserService buriedPointUserService;

    @Override
    public IPage<BuriedPointVO> selectBuriedPointPage(IPage<BuriedPointVO> page, BuriedPointVO buriedPoint) {
        return page.setRecords(baseMapper.selectBuriedPointPage(page, buriedPoint));
    }

    @Override
    public BuriedPoint findByType(Integer type, Integer objectId, LocalDateTime time) {
        BuriedPoint buriedPoint = baseMapper.findByType(type, objectId, time);
        if (buriedPoint == null) {
            buriedPoint = new BuriedPoint();
            buriedPoint.setType(type);
            buriedPoint.setName(getBuriedName(type, objectId));
            buriedPoint.setObjectId(objectId);
            buriedPoint.setVisitTime(time);
            buriedPoint.setVisitCount(0);
            buriedPoint.setVisitPeopleCount(0);
            buriedPoint.setCreateDate(LocalDateTime.now());
            baseMapper.insert(buriedPoint);
        }
        return buriedPoint;
    }

    private String getBuriedName(Integer type, Integer objectId) {
        String name = "";
        if (type == null) {
            return name;
        }
        if (objectId != null) {
            if (type == 1 || type == 2) {
                switch (objectId) {
                    case 1:  name += "48首小学生必读古诗经典诵读 "; break;
                    case 2:  name += "传统国学动画千字文 "; break;
                    case 3:  name += "英文字母趣味动画拼读课 "; break;
                    case 4:  name += "中华励志名人故事 "; break;
                    default: name += "";
                }
            } else if (type == 3) {
                switch (objectId) {
                    case 11:  name += "儿童驼背矫正仪 "; break;
                    case 12:  name += "小学必读国学故事 "; break;
                    case 13:  name += "透气网棉双肩小学书包 "; break;
                    case 14:  name += "儿童智能矫正矫正带 "; break;
                    default: name += "";
                }
            }
        }
        switch (type) {
            case 1:  name += "团购课程"; break;
            case 2:  name += "三人拼团"; break;
            default: name += "";
        }
        return name;
    }

    @Override
    public void updateVisitCount(Integer type, Integer objectId, Integer userId) {
        try {
            BuriedPoint buriedPoint = findByType(type, objectId, LocalDateTime.now());
            boolean valid = buriedPointUserService.insertBuriedUser(buriedPoint.getId(), userId);
            BuriedPoint bp = new BuriedPoint();
            bp.setId(buriedPoint.getId());
            bp.setVisitCount(buriedPoint.getVisitCount() + 1);
            if (valid) {
                bp.setVisitPeopleCount(buriedPoint.getVisitPeopleCount() +1);
            }
            baseMapper.updateById(bp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

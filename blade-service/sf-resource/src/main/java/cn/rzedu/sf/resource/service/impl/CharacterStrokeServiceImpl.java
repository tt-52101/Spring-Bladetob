package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;
import cn.rzedu.sf.resource.mapper.CharacterStrokeMapper;
import cn.rzedu.sf.resource.service.ICharacterStrokeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉字笔画 服务实现类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Service
public class CharacterStrokeServiceImpl extends ServiceImpl<CharacterStrokeMapper, CharacterStroke> implements ICharacterStrokeService {

	@Override
	public IPage<CharacterStrokeVO> selectCharacterStrokePage(IPage<CharacterStrokeVO> page, CharacterStrokeVO characterStroke) {
		return page.setRecords(baseMapper.selectCharacterStrokePage(page, characterStroke));
	}

}

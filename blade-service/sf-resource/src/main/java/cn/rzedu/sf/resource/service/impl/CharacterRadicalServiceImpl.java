package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterRadical;
import cn.rzedu.sf.resource.vo.CharacterRadicalVO;
import cn.rzedu.sf.resource.mapper.CharacterRadicalMapper;
import cn.rzedu.sf.resource.service.ICharacterRadicalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉字偏旁 服务实现类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Service
public class CharacterRadicalServiceImpl extends ServiceImpl<CharacterRadicalMapper, CharacterRadical> implements ICharacterRadicalService {

	@Override
	public IPage<CharacterRadicalVO> selectCharacterRadicalPage(IPage<CharacterRadicalVO> page, CharacterRadicalVO characterRadical) {
		return page.setRecords(baseMapper.selectCharacterRadicalPage(page, characterRadical));
	}

}

package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterStructure;
import cn.rzedu.sf.resource.vo.CharacterStructureVO;
import cn.rzedu.sf.resource.mapper.CharacterStructureMapper;
import cn.rzedu.sf.resource.service.ICharacterStructureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉字结构 服务实现类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Service
public class CharacterStructureServiceImpl extends ServiceImpl<CharacterStructureMapper, CharacterStructure> implements ICharacterStructureService {

	@Override
	public IPage<CharacterStructureVO> selectCharacterStructurePage(IPage<CharacterStructureVO> page, CharacterStructureVO characterStructure) {
		return page.setRecords(baseMapper.selectCharacterStructurePage(page, characterStructure));
	}

}

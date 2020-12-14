package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CharacterStructure;
import cn.rzedu.sf.resource.vo.CharacterStructureVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉字结构 服务类
 *
 * @author Blade
 * @since 2020-12-10
 */
public interface ICharacterStructureService extends IService<CharacterStructure> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterStructure
	 * @return
	 */
	IPage<CharacterStructureVO> selectCharacterStructurePage(IPage<CharacterStructureVO> page,
                                                             CharacterStructureVO characterStructure);

}

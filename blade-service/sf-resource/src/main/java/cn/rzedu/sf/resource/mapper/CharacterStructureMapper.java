package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.CharacterStructure;
import cn.rzedu.sf.resource.vo.CharacterStructureVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 汉字结构 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-10
 */
public interface CharacterStructureMapper extends BaseMapper<CharacterStructure> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterStructure
	 * @return
	 */
	List<CharacterStructureVO> selectCharacterStructurePage(IPage page, CharacterStructureVO characterStructure);

	/**
	 * 所有结构
	 * @return
	 */
	List<CharacterStructure> findAllStructure();

	/**
	 * 单个结构
	 * @param name
	 * @return
	 */
	CharacterStructure findByName(String name);
}

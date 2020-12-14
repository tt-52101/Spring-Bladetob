package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.CharacterRadical;
import cn.rzedu.sf.resource.vo.CharacterRadicalVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 汉字偏旁 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-10
 */
public interface CharacterRadicalMapper extends BaseMapper<CharacterRadical> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterRadical
	 * @return
	 */
	List<CharacterRadicalVO> selectCharacterRadicalPage(IPage page, CharacterRadicalVO characterRadical);

}

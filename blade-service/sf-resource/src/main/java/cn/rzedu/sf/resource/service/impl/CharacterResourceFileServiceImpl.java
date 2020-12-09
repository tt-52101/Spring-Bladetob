package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.vo.CharacterResourceFileVO;
import cn.rzedu.sf.resource.mapper.CharacterResourceFileMapper;
import cn.rzedu.sf.resource.service.ICharacterResourceFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 汉字资源文件 服务实现类
 *
 * @author Blade
 * @since 2020-09-05
 */
@Service
public class CharacterResourceFileServiceImpl extends ServiceImpl<CharacterResourceFileMapper, CharacterResourceFile> implements ICharacterResourceFileService {

    @Override
    public IPage<CharacterResourceFileVO> selectCharacterResourceFilePage(
            IPage<CharacterResourceFileVO> page, CharacterResourceFileVO characterResourceFile) {
        return page.setRecords(baseMapper.selectCharacterResourceFilePage(page, characterResourceFile));
    }

    @Override
    public CharacterResourceFile findUnionByResourceIdAndObjectId(Integer resourceId, String objectId) {
        return baseMapper.findUnionByResourceIdAndObjectId(resourceId, objectId);
    }

    @Override
    public List<CharacterResourceFile> findByResourceId(Integer resourceId) {
        return baseMapper.findByResourceId(resourceId);
    }

    @Override
    public List<CharacterResourceFile> findByCharacterId(Integer characterId, Integer subject, Integer resourceType) {
        return baseMapper.findByCharacterId(characterId, subject, resourceType);
    }
}

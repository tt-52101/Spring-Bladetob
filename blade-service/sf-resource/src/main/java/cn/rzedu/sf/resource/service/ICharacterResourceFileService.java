package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.vo.CharacterResourceFileVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 汉字资源文件 服务类
 *
 * @author Blade
 * @since 2020-09-05
 */
public interface ICharacterResourceFileService extends IService<CharacterResourceFile> {

    /**
     * 自定义分页
     *
     * @param page
     * @param characterResourceFile
     * @return
     */
    IPage<CharacterResourceFileVO> selectCharacterResourceFilePage(IPage<CharacterResourceFileVO> page,
                                                                   CharacterResourceFileVO characterResourceFile);

    /**
     * 根据资源类型和objectId获取模板下的唯一对象
     * @param resourceId
     * @param objectId
     * @return
     */
    CharacterResourceFile findUnionByResourceIdAndObjectId(Integer resourceId, String objectId, String font);

    /**
     * 获取某汉字资源模板下 的所有object对象
     * @param resourceId
     * @return
     */
    List<CharacterResourceFile> findByResourceId(Integer resourceId);

    /**
     * 获取某汉字资源模板下 的所有object对象
     * @param resourceId
     * @param font
     * @return
     */
    List<CharacterResourceFile> findByResourceAndFont(Integer resourceId, String font);

    /**
     * 获取某汉字资源模板(唯一启用)下的所有object对象
     * characterId，subject，resourceType确定唯一启用资源
     * @param characterId
     * @param subject
     * @param resourceType
     * @return
     */
    List<CharacterResourceFile> findByCharacterId(Integer characterId, Integer subject, Integer resourceType);

}

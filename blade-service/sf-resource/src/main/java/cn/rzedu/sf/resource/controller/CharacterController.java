/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.service.ICharacterResourceService;
import cn.rzedu.sf.resource.vo.CharacterIsExistVO;
import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.vo.CharacterVO;
import cn.rzedu.sf.resource.wrapper.CharacterWrapper;
import cn.rzedu.sf.resource.service.ICharacterService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 汉字 控制器
 *
 * @author Blade
 * @since 2020-08-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/character")
@Api(value = "汉字 ", tags = "汉字")
public class CharacterController extends BladeController {

    private ICharacterService characterService;

    private ICharacterResourceService characterResourceService;

    /**
     * 字典列表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "字典列表", notes = "跟据\"字\"查询")
    public R<IPage<CharacterVO>> list(
            @ApiParam(value = "字") @RequestParam(value = "name", required = false) String name,
            Query query) {
        if (StringUtils.isNotEmpty(name)) {
            name = name.trim();
        }
        IPage<CharacterVO> pages = characterService.findCharacterByChar(Condition.getPage(query), name);
        return R.data(pages);
    }

    /**
     * 根据id获取详情
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "详情", notes = "根据id获取汉字详情")
    public R<CharacterVO> detailById(@ApiParam(value = "主键", required = true) @PathVariable(value = "id") Integer id) {
        Character character = characterService.getById(id);
        return R.data(CharacterWrapper.build().entityVO(character));
    }



    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增", notes = "传入character")
    public R save(@Valid @RequestBody Character character) {
        boolean isExist = characterService.judgeRepeatByCharS(character.getCharS(), null);
        if (isExist) {
            return R.success(ResultCode.PARAM_VALID_ERROR, "简体字已有，不能重复");
        }
        return R.status(characterService.save(character));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "修改", notes = "传入character")
    public R update(@Valid @RequestBody Character character) {
        boolean isExist = characterService.judgeRepeatByCharS(character.getCharS(), character.getId());
        if (isExist) {
            return R.success(ResultCode.PARAM_VALID_ERROR, "简体字已有，不能重复");
        }
        boolean status = characterService.updateById(character);
        if (status) {
            characterResourceService.updateCharST(character.getId(), character.getCharS(), character.getCharT());
        }
        return R.status(status);
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "id集合", required = true) @RequestParam String ids) {
        List<Character> list = characterService.listByIds(Func.toLongList(ids));
        boolean hasResource =
                list.stream().anyMatch(character -> character.getSoftResCount() > 0 || character.getHardResCount() > 0);
        if (hasResource) {
            return R.success(ResultCode.PARAM_VALID_ERROR, "有关联资源，无法删除");
        }
        return R.status(characterService.removeByIds(Func.toLongList(ids)));
    }


    /**
     * 根据教材课程二维码，更新汉字二维码
     * @param textbookId
     * @return
     */
    @PostMapping("/batch/update/ercode")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "根据教材课程二维码，更新汉字二维码", notes = "传入textbookId")
    public R batchUpdateErCode(@ApiParam(value = "教材id，非必填", required = false)
                                   @RequestParam(value = "textbookId", required = false) Integer textbookId) {
        List<Character> list = characterService.findErCodeFromLesson(textbookId);
        characterService.updateBatchById(list);
        return R.status(true);
    }

    /**
     * 硬笔查询 同步教学资源
     * 根据 汉字 获取详情
     * @param characterName
     * @return
     */
    @GetMapping("/hard/{character}")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "硬笔查询汉字-同步教学资源", notes = "根据单个汉字获取汉字ID")
    public R<CharacterIsExistVO> getDetailPageByCharacter(@ApiParam(value = "主键", required = true) @PathVariable(value = "character") String characterName) {
        CharacterIsExistVO result = new CharacterIsExistVO();

        Character character = new Character();
        character.setCharS(characterName);
        character.setIsDeleted(0);
        Character c = characterService.getOne(Condition.getQueryWrapper(character));
        if(c != null){
            boolean isExist = characterService.isExistHardResourceByCharacterId(c.getId());
            if(isExist){
                result.setIsExist(1);
                result.setCharacterId(c.getId());
                result.setCharacterName(c.getCharS());
                return R.data(result);
            }
        }
        return R.data(result);
    }
}

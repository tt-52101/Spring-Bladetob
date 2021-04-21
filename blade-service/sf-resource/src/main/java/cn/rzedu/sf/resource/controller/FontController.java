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

import cn.rzedu.sf.resource.vo.CharacterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.resource.entity.Font;
import cn.rzedu.sf.resource.vo.FontVO;
import cn.rzedu.sf.resource.wrapper.FontWrapper;
import cn.rzedu.sf.resource.service.IFontService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 书法字体表 控制器
 *
 * @author Blade
 * @since 2021-04-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/font")
@Api(value = "书法字体表", tags = "书法字体表接口")
public class FontController extends BladeController {

	private IFontService fontService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入font")
	public R<FontVO> detail(Font font) {
		Font detail = fontService.getOne(Condition.getQueryWrapper(font));
		return R.data(FontWrapper.build().entityVO(detail));
	}

	/**
	 * 软/硬笔资源
	 * @param query
	 * @return
	 */
	@GetMapping("/list/{subject}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "软/硬笔 字体列表", notes = "软/硬笔 字体列表")
	public R<IPage<FontVO>> list(
			@ApiParam(value = "资源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
			Query query) {
		FontVO fontVO = new FontVO();
		fontVO.setSubject(subject);
		IPage<FontVO> pages = fontService.selectFontPage(Condition.getPage(query), fontVO);
		return R.data(pages);
	}

	/**
	* 新增 书法字体表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入font(name、subject、type) ")
	public R save(@Valid @RequestBody Font font) {
		LocalDateTime now = LocalDateTime.now();
		font.setCreateDate(now);
		font.setModifyDate(now);
		font.setIsDeleted(0);
		return R.status(fontService.save(font));
	}

	/**
	* 修改 书法字体表
	*/
//	@PostMapping("/update")
//    @ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入font")
//	public R update(@Valid @RequestBody Font font) {
//		return R.status(fontService.updateById(font));
//	}

	/**
	* 新增或修改 书法字体表
	*/
//	@PostMapping("/submit")
//    @ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入font")
//	public R submit(@Valid @RequestBody Font font) {
//		return R.status(fontService.saveOrUpdate(font));
//	}

	
	/**
	* 删除 书法字体表
	*/
//	@PostMapping("/remove")
//    @ApiOperationSupport(order = 7)
//	@ApiOperation(value = "删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(fontService.removeByIds(Func.toLongList(ids)));
//	}

	
}

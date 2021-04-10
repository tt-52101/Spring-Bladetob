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

import cn.rzedu.sf.resource.service.IHardPenQueryService;
import cn.rzedu.sf.resource.service.IMediaResourceService;
import cn.rzedu.sf.resource.vo.HardPenQueryVO;
import cn.rzedu.sf.resource.vo.MediaResourceSortVO;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2021-03-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mediaresource")
@Api(value = "教案", tags = "Media接口")
public class MediaResourceController extends BladeController {

	private IMediaResourceService mediaResourceService;
	private IHardPenQueryService hardPenQueryService;

	/**
	 * 首页
	 */
	@GetMapping("/Page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "首页", notes = "1=授课教案,2=创作文案,3=国学视频,4=知识宝库,5=名师微课")
	public R<IPage<MediaResourceVO>> MediaResourcePage(Query query,
		@ApiParam(value = "mediaType",required = true)@RequestParam(value = "mediaType") Integer mediaType,
		@ApiParam(value = "subject",required = true)@RequestParam(value = "subject") Integer subject) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourcePage(Condition.getPage(query),mediaType,subject);
		return R.data(pages);
	}

	/**
	 * 分类列表
	 *
	 * @param mediaType
	 * @return
	 */
	@GetMapping("/sort/{mediaType}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分类列表", notes = "1=授课教案,2=创作文案,3=国学视频,4=知识宝库,5=名师微课")
	public R<List<MediaResourceSortVO>> MediaResourceSortList(
		@ApiParam(value = "mediaType",required = true)@PathVariable(value = "mediaType") Integer mediaType,
		@ApiParam(value = "subject",required = true)@RequestParam(value = "subject") Integer subject) {
		List<MediaResourceSortVO> list = mediaResourceService.selectMediaResourceSortList(mediaType,subject);
		return R.data(list);
	}


	/**
	 * 资源列表
	 */
	@GetMapping("/sortList/{mediaType}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "资源列表", notes = "根据传入sortId返回资源列表")
	public R<IPage<MediaResourceVO>> MediaResourceList(Query query,
	@ApiParam(value = "media_type",required = true) @PathVariable(value = "mediaType") Integer mediaType,
	@ApiParam(value = "一级分类")@RequestParam(value = "sortId",required = false) Integer sortId,
	@ApiParam(value = "subject",required = true)@RequestParam(value = "subject") Integer subject) {

			IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceList(Condition.getPage(query),mediaType,sortId,subject);
			return R.data(pages);

	}


	/**
	 * 搜索资源列表
	 */
	@GetMapping("/searchList/{mediaType}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "资源搜索", notes = "根据搜索title返回资源列表")
	public R<IPage<MediaResourceVO>> MediaResourceSearchList(Query query,
		@ApiParam(value = "mediaType",required = true) @PathVariable(value = "mediaType") Integer mediaType,
		@ApiParam(value = "一级分类")@RequestParam(value = "sortId",required = false) Integer sortId,
		@ApiParam(value = "subject 71=软笔 72=硬笔",required = true)@RequestParam(value = "subject") Integer subject,
		@ApiParam(value = "title")@RequestParam(value = "title",required = false) String title) {

			IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceSortSearch(Condition.getPage(query),mediaType,sortId,subject,title);
			return R.data(pages);
		}

	/**
	 * 查询
	 * @param query
	 * @param subject
	 * @param title
	 * @return
	 */
	@GetMapping("/QueryList/{mediaType}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "资源查询", notes = "根据查询title返回资源列表")
	public R<IPage<MediaResourceVO>> MediaResourceQueryList(Query query,
		@ApiParam(value = "subject 71=软笔 72=硬笔",required = true)@RequestParam(value = "subject") Integer subject,
		@ApiParam(value = "title")@RequestParam(value = "title",required = false) String title) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceQuery(Condition.getPage(query),subject,title);
		return R.data(pages);
	}


	/**
	 * 硬笔查询
	 * @param query
	 * @param name
	 * @return
	 */
//	@GetMapping("/HardPenQueryList")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "硬笔查询", notes = "根据搜索name返回资源列表")
//	public R<IPage<HardPenQueryVO>> hardPenQueryList(Query query,
//		@ApiParam(value = "name")@RequestParam(value = "name",required = false) String name) {
//		IPage<HardPenQueryVO> pages = hardPenQueryService.selectHardPenQuery(Condition.getPage(query),name);
//		return R.data(pages);
//	}
	
}

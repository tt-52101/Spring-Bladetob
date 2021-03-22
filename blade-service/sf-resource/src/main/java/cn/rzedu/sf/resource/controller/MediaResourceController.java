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

import cn.rzedu.sf.resource.service.IMediaResourceService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


	/**
	 * 首页
	 */
	@GetMapping("/teachingPlanPage")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "授课教案-首页", notes = "全部教案")
	public R<IPage<MediaResourceVO>> teachingPlanPage(MediaResourceVO mediaResource, Query query) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceSKJAPage(Condition.getPage(query), mediaResource);
		return R.data(pages);
	}

	@GetMapping("/creationPapeworkPage")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "创作文案-首页", notes = "ALl")
	public R<IPage<MediaResourceVO>> creationPapeworkPage(MediaResourceVO mediaResource, Query query) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceCZWAPage(Condition.getPage(query), mediaResource);
		return R.data(pages);
	}

	@GetMapping("/sinologVideoPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "国学视频-首页", notes = "ALl")
	public R<IPage<MediaResourceVO>> sinologVideoPage(MediaResourceVO mediaResource, Query query) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceGXSPPage(Condition.getPage(query), mediaResource);
		return R.data(pages);
	}

	@GetMapping("/knowledgeBasePage")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "知识宝库-首页", notes = "ALl")
	public R<IPage<MediaResourceVO>> knowledgeBasePage(MediaResourceVO mediaResource, Query query) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceZSBKPage(Condition.getPage(query), mediaResource);
		return R.data(pages);
	}

	@GetMapping("/microLecturePage")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "名师微课-首页", notes = "ALl")
	public R<IPage<MediaResourceVO>> microLecturePage(MediaResourceVO mediaResource, Query query) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceMSWKPage(Condition.getPage(query), mediaResource);
		return R.data(pages);
	}

	/**
	 * 分类
	 */
	@GetMapping("/TeachingPlanList/{sort}")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "授课教案-分类", notes = "根据传入的sort")
	public R<IPage<MediaResourceVO>> TeachingPlanList(MediaResourceVO mediaResource, Query query,@ApiParam(value = "一级分类",required = true)@PathVariable(value = "sort") String sort) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceSKJAList(Condition.getPage(query),sort);
		return R.data(pages);
	}

	@GetMapping("/creationPapeworkList/{sort}")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "创作文案-分类", notes = "根据传入的sort")
	public R<IPage<MediaResourceVO>> creationPapeworkList(MediaResourceVO mediaResource, Query query,@ApiParam(value = "一级分类",required = true)@PathVariable(value = "sort") String sort) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceCZWAList(Condition.getPage(query),sort);
		return R.data(pages);
	}

	@GetMapping("/sinologVideoList/{sort}")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "国学视频-分类", notes = "根据传入的sort")
	public R<IPage<MediaResourceVO>> sinologVideoList(MediaResourceVO mediaResource, Query query,@ApiParam(value = "一级分类",required = true)@PathVariable(value = "sort") String sort) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceGXSPList(Condition.getPage(query),sort);
		return R.data(pages);
	}

	@GetMapping("/knowledgeBaseList/{sort}")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "知识宝库-分类", notes = "根据传入的sort")
	public R<IPage<MediaResourceVO>> knowledgeBaseList(MediaResourceVO mediaResource, Query query,@ApiParam(value = "一级分类",required = true)@PathVariable(value = "sort") String sort) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceZSBKList(Condition.getPage(query),sort);
		return R.data(pages);
	}

	@GetMapping("/microLectureList/{sort}")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "名师微课-分类", notes = "根据传入的sort")
	public R<IPage<MediaResourceVO>> microLectureList(MediaResourceVO mediaResource, Query query,@ApiParam(value = "一级分类",required = true)@PathVariable(value = "sort") String sort) {
		IPage<MediaResourceVO> pages = mediaResourceService.selectMediaResourceMSWKList(Condition.getPage(query),sort);
		return R.data(pages);
	}

	/**
	* 详情
	*/
	@GetMapping("/TeachingPlanDetail/{id}")
    @ApiOperationSupport(order = 11)
	@ApiOperation(value = "授课教案-详情", notes = "传入ID")
	public R<String > TeachingPlanDetail(@ApiParam(value = "id",required = true)@PathVariable(value = "id") Integer id) {
		String uuid = mediaResourceService.selectMediaResourceSKJADetail(id);
		return R.data(uuid);
	}
	@GetMapping("/creationPapeworkDetail/{id}")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "创作文案-详情", notes = "传入ID")
	public R<String > creationPapeworkDetail(@ApiParam(value = "id",required = true)@PathVariable(value = "id") Integer id) {
		String uuid = mediaResourceService.selectMediaResourceCZWADetail(id);
		return R.data(uuid);
	}
	@GetMapping("/sinologVideoDetail/{id}")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "国学视频-详情", notes = "传入ID")
	public R<String > sinologVideoDetail(@ApiParam(value = "id",required = true)@PathVariable(value = "id") Integer id) {
		String uuid = mediaResourceService.selectMediaResourceGXSPDetail(id);
		return R.data(uuid);
	}
	@GetMapping("/knowledgeBaseDetail/{id}")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "知识宝库-详情", notes = "传入ID")
	public R<String > knowledgeBaseDetail(@ApiParam(value = "id",required = true)@PathVariable(value = "id") Integer id) {
		String uuid = mediaResourceService.selectMediaResourceZSBKDetail(id);
		return R.data(uuid);
	}
	@GetMapping("/microLectureDetail/{id}")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "名师微课-详情", notes = "传入ID")
	public R<String > microLectureDetail(@ApiParam(value = "id",required = true)@PathVariable(value = "id") Integer id) {
		String uuid = mediaResourceService.selectMediaResourceMSWKDetail(id);
		return R.data(uuid);
	}



//	/**
//	* 新增
//	*/
//	@PostMapping("/save")
//    @ApiOperationSupport(order = 16)
//	@ApiOperation(value = "新增", notes = "传入mediaResource")
//	public R save(@Valid @RequestBody MediaResource mediaResource) {
//		return R.status(mediaResourceService.save(mediaResource));
//	}
//
//	/**
//	* 修改
//	*/
//	@PostMapping("/update")
//    @ApiOperationSupport(order = 17)
//	@ApiOperation(value = "修改", notes = "传入mediaResource")
//	public R update(@Valid @RequestBody MediaResource mediaResource) {
//		return R.status(mediaResourceService.updateById(mediaResource));
//	}
//
//	/**
//	* 新增或修改
//	*/
//	@PostMapping("/submit")
//    @ApiOperationSupport(order = 18)
//	@ApiOperation(value = "新增或修改", notes = "传入mediaResource")
//	public R submit(@Valid @RequestBody MediaResource mediaResource) {
//		return R.status(mediaResourceService.saveOrUpdate(mediaResource));
//	}
//
//
//	/**
//	* 删除
//	*/
//	@PostMapping("/remove")
//    @ApiOperationSupport(order = 19)
//	@ApiOperation(value = "删除", notes = "传入id")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(mediaResourceService.removeByIds(Func.toLongList(ids)));
//	}

	
}

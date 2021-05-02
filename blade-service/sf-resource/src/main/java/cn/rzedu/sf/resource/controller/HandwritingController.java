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

import cn.rzedu.sf.resource.vo.HandwritingWordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;


import org.springblade.core.tool.api.R;
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.vo.FileResult;
import org.springframework.web.bind.annotation.*;
import cn.rzedu.sf.resource.vo.HandwritingVO;
import cn.rzedu.sf.resource.service.IHandwritingService;
import org.springblade.core.boot.ctrl.BladeController;

import java.io.IOException;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2021-04-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/handwriting")
@Api(value = "集字创作", tags = "集字创作")
public class HandwritingController extends BladeController {

	private IHandwritingService handwritingService;
	private EntityFileClient entityFileClient;

	/**
	* 生成集字
	*/
	@PostMapping("/generateHandwriting")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "生成集字")
	public R<List<HandwritingWordVO>> generateHandwriting(@Valid @RequestBody HandwritingVO handwritingVO,
														  @RequestHeader("userId") int userId,
														  @RequestHeader("userName") String userName
														  ) throws IOException {
		List<String> sentences = handwritingVO.getSentences();
		String standards = handwritingVO.getStandards();
		String banner = handwritingVO.getBanner();
		String font = handwritingVO.getFont();
		List<String> signs = handwritingVO.getSigns();

		List<HandwritingWordVO> handwritingWordVOS = handwritingService.generateHandwriting(sentences,standards,banner,font,signs,userId,userName);
		for (HandwritingWordVO handwritingWordVO : handwritingWordVOS){
			if (handwritingWordVO!=null){
				String uuid = handwritingWordVO.getUuid();
				FileResult fileResult = entityFileClient.findImageByUuid(uuid);
				String link = fileResult.getLink();
				handwritingWordVO.setUuid(link);
			}else {
				handwritingWordVO.setUuid(" ");
			}

		}
		return R.data(handwritingWordVOS);
	}

	/**
	 * 所属书法作者列表
	 */
	@GetMapping("/selectSourceAuthor")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "所属书法作者列表")
	public R<List<String>> selectSourceAuthor(@ApiParam(value = "word") @RequestParam String word) {
		return R.data(handwritingService.selectSourceAuthor(word));
	}

	/**
	 * 所属碑帖分类
	 */
	@GetMapping("/selectSourceInscriptions")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "所属碑帖分类")
	public R<List<HandwritingWordVO>> selectSourceInscriptions(
			@ApiParam(value = "单字") @RequestParam String word,
			@ApiParam(value = "字体") @RequestParam String font,
			@ApiParam(value = "所属书法作者") @RequestParam(required = false) String sourceAuthor
	) throws IOException {
		List<HandwritingWordVO> handwritingWordVOS = handwritingService.selectSourceInscriptions(word,font,sourceAuthor);
		for (HandwritingWordVO handwritingWordVO : handwritingWordVOS){
			if (handwritingWordVO!=null){
				String uuid = handwritingWordVO.getUuid();
				FileResult fileResult = entityFileClient.findImageByUuid(uuid);
				String link = fileResult.getLink();
				handwritingWordVO.setUuid(link);
			}else {
				handwritingWordVO.setUuid(" ");
			}
		}
		return R.data(handwritingWordVOS);
	}


	/**
	 * 单字获取
	 */
	@GetMapping("/selectHandwritingWord")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "单字获取")
	public R<HandwritingWordVO> selectHandwritingWord(
			@ApiParam(value = "单字") @RequestParam String word,
			@ApiParam(value = "字体") @RequestParam String font,
			@ApiParam(value = "所属书法作者") @RequestParam String sourceAuthor,
			@ApiParam(value = "所属碑帖分类") @RequestParam String sourceInscriptions
	) throws IOException {
		HandwritingWordVO handwritingWordVO = handwritingService.selectHandwritingWord(word,font,sourceAuthor,sourceInscriptions);
		if (handwritingWordVO!=null){
			String uuid = handwritingWordVO.getUuid();
			FileResult fileResult = entityFileClient.findImageByUuid(uuid);
			String link = fileResult.getLink();
			handwritingWordVO.setUuid(link);
			return R.data(handwritingWordVO);
		}else
			return R.success("没有资源");

	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "save")
	public R saveHandwritingWord(@Valid @RequestBody HandwritingWordVO handwritingWordVO)   {
		String word = handwritingWordVO.getWord();
		String uuid = handwritingWordVO.getUuid();
		String font = handwritingWordVO.getFont();
		String sourceAuthor = handwritingWordVO.getSourceAuthor();
		String sourceInscriptions = handwritingWordVO.getSourceInscriptions();
		return R.status(handwritingService.saveHandwritingWord(word,uuid,font,sourceAuthor,sourceInscriptions));
	}

}

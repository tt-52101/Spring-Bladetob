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

import cn.rzedu.sf.resource.service.IHandwritingService;
import cn.rzedu.sf.resource.service.IMediaResourceService;
import cn.rzedu.sf.resource.vo.HandwritingVO;
import cn.rzedu.sf.resource.vo.HandwritingWordVO;
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
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.vo.FileResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@Api(value = "书法字典", tags = "书法字典")
public class HandwritingDictController extends BladeController {

	private IHandwritingService handwritingService;
	private EntityFileClient entityFileClient;
	private IMediaResourceService mediaResourceService;

	/**
	 * 书法查询
	 */
	@GetMapping("/handwritingWordQuery")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "书法查询")
	public R<List<HandwritingWordVO>> handwritingWordQuery(
															@ApiParam(value = "subject 72 = 硬笔 71 = 软笔") @RequestParam Integer subject,
															@ApiParam(value = "单字") @RequestParam String word,
															@ApiParam(value = "字体") @RequestParam String font,
															@ApiParam(value = "所属书法作者") @RequestParam(required = false) String sourceAuthor,
															@ApiParam(value = "所属碑帖分类") @RequestParam(required = false) String sourceInscriptions
	) throws IOException {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		Integer userId =Integer.parseInt(request.getHeader("userId")) ;
		String username = request.getHeader("username");
		if (!word.isEmpty() && !word.equals(" ")){
			mediaResourceService.saveSearchHistory(word,userId,username,subject);
		}
		List<HandwritingWordVO> handwritingWordVOS = handwritingService.handwritingWordQuery(word,font,sourceAuthor,sourceInscriptions);
		for (HandwritingWordVO handwritingWordVO : handwritingWordVOS){
			if (handwritingWordVO!=null){
				String uuid = handwritingWordVO.getUuid();
				FileResult fileResult = entityFileClient.findImageByUuid(uuid);
				String link = fileResult.getLink();
				handwritingWordVO.setUuid(link);
			}
		}
		return R.data(handwritingWordVOS);
	}

	/**
	 * 字库检索记录列表
	 */
	@GetMapping("/searchKeywordHistory")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "字库检索记录")
	public R<List<String>> selectResourceId(@ApiParam(value = "subject 71=软笔 72=硬笔",required = true)@RequestParam(value = "subject") Integer subject
	) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String userName = request.getHeader("username");
		return R.data(mediaResourceService.selectKeyword(userName,subject));
	}

}

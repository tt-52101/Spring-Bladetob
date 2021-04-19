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
package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.user.vo.PublisherVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import jxl.write.*;
import lombok.AllArgsConstructor;


import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import cn.rzedu.sf.user.vo.FrontUserVO;
import cn.rzedu.sf.user.service.IFrontUserService;
import org.springblade.core.boot.ctrl.BladeController;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2021-04-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/frontuser")
@Api(value = "", tags = "用户管理系统接口")
public class FrontUserController extends BladeController {

	private IFrontUserService frontUserService;

	/**
	* 用户登录
	*/
	@PostMapping("/userLogin")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "用户登录", notes = "传入userName,passWord")
	public R<FrontUserVO> FrontUserLogin(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		String passWord = frontUserVO.getPassword();
		FrontUserVO userVO = frontUserService.FrontUserLogin(userName,passWord);
		if (userVO!=null){
			return R.data(userVO);
		}else
			return R.fail(400,"用户名或密码不正确");
	}

	/**
	 * 修改密码
	 */
	@PostMapping("/userUpdatePassWord")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "修改密码", notes = "传入userName,oldPassWord,newPassWord")
	public R FrontUserUpdatePassWord(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		String oldPassWord = frontUserVO.getPassword();
		String newPassWord = frontUserVO.getNewPassword();
		return R.status(frontUserService.userUpdatePassword(userName,oldPassWord,newPassWord));
	}

	/**
	 * 查询用户
	 */
	@GetMapping("/selectFrontUserList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "USER条件查询", notes = "传入查询条件")
	public R<IPage<FrontUserVO>> selectFrontUserList(Query query,
													 @ApiParam(value = "username") @RequestParam(value = "username",required = false)String username,
													 @ApiParam(value = "typeId") @RequestParam(value = "typeId",required = false)String typeId,
													 @ApiParam(value = "provinceCode") @RequestParam(value = "provinceCode",required = false)String provinceCode,
													 @ApiParam(value = "cityCode") @RequestParam(value = "cityCode",required = false)String cityCode,
													 @ApiParam(value = "districtCode") @RequestParam(value = "districtCode",required = false)String districtCode,
													 @ApiParam(value = "单位") @RequestParam(value = "department",required = false)String department,
													 @ApiParam(value = "备注") @RequestParam(value = "remark",required = false)String remark
	) {
		IPage<FrontUserVO> pages = frontUserService.selectFrontUserList(Condition.getPage(query),username,typeId,provinceCode,cityCode,districtCode,department,remark);
		return R.data(pages);
	}



	/**
	 * 单用户注册
	 */
	@PostMapping("/frontUserRegister")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "单用户注册", notes = "传入参数")
	public R frontUserRegister(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		String passWord = frontUserVO.getPassword();
		Integer typeId = frontUserVO.getTypeId();
		String provinceCode = frontUserVO.getProvinceCode();
		String cityCode = frontUserVO.getCityCode();
		String districtCode = frontUserVO.getDistrictCode();
		String department = frontUserVO.getDepartment();
		String remark = frontUserVO.getRemark();
		return R.status(frontUserService.frontUserRegister(userName,passWord,typeId,provinceCode,cityCode,districtCode,department,remark));


	}

	@PostMapping("/frontUserBatchRegister")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "批量用户注册", notes = "传入参数")
	public R<List<String>> frontUseBatchRegister(@Valid @RequestBody FrontUserVO frontUserVO) throws InterruptedException, IOException, WriteException {
		Integer batchSize = frontUserVO.getBatchSize();
		String passWord = frontUserVO.getPassword();
		Integer typeId = frontUserVO.getTypeId();
		String provinceCode = frontUserVO.getProvinceCode();
		String cityCode = frontUserVO.getCityCode();
		String districtCode = frontUserVO.getDistrictCode();
		String department = frontUserVO.getDepartment();
		String remark = frontUserVO.getRemark();
		List<String> userNameList = frontUserService.frontUserBatchRegister(batchSize,passWord,typeId,provinceCode,cityCode,districtCode,department,remark);
		return R.data(userNameList);
	}


	/**
	 * 查看
	 */
	@GetMapping("/selectFrontUserDetail")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "查看", notes = "传入userName")
	public  R<FrontUserVO> selectFrontUserDetail(@ApiParam(value = "userName") @RequestParam(value = "userName")String userName) {
		return R.data(frontUserService.selectFrontUserDetail(userName));
	}

	/**
	 * 编辑
	 */
	@PostMapping("/updateFrontUser")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "编辑", notes = "传入参数")
	public  R updateFrontUser(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		String newUserName = frontUserVO.getNewUserName();
		String passWord = frontUserVO.getPassword();
		String provinceCode = frontUserVO.getProvinceCode();
		String cityCode = frontUserVO.getCityCode();
		String districtCode = frontUserVO.getDistrictCode();
		String department = frontUserVO.getDepartment();
		String remark = frontUserVO.getRemark();
		return R.status(frontUserService.updateFrontUser(userName,newUserName,passWord,provinceCode,cityCode,districtCode,department,remark));
	}

	@PostMapping("/deleteFrontUser")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入userName")
	public  R deleteFrontUser(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		return R.status(frontUserService.deleteFrontUser(userName));
	}

	@PostMapping("/updateFunctionAuth")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "功能权限", notes = "传入参数")
	public  R<FrontUserVO> updateFunctionAuth(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		List<String> functionIds = frontUserVO.getFunctionIds();
		List<String> functionNames = frontUserVO.getFunctionNames();
		List<String> publisherIds = frontUserVO.getPublisherIds();
		List<String> publisherNames = frontUserVO.getPublisherNames();
		List<String> gradeIds = frontUserVO.getGradeIds();
		List<String> gradeNames = frontUserVO.getGradeNames();
		return R.status(frontUserService.updateFunctionAuth(userName,functionIds,functionNames,publisherIds,publisherNames,gradeIds,gradeNames));
	}

	@PostMapping("/updatebatchFunctionAuth")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "批量更改功能权限", notes = "传入参数")
	public  R<FrontUserVO> updatebatchFunctionAuth(@Valid @RequestBody FrontUserVO frontUserVO) {
		List<String> userNameList = frontUserVO.getUserNameList();
		List<String> functionIds = frontUserVO.getFunctionIds();
		List<String> functionNames = frontUserVO.getFunctionNames();
		List<String> publisherIds = frontUserVO.getPublisherIds();
		List<String> publisherNames = frontUserVO.getPublisherNames();
		List<String> gradeIds = frontUserVO.getGradeIds();
		List<String> gradeNames = frontUserVO.getGradeNames();
		return R.status(frontUserService.updateBatchFunctionAuth(userNameList,functionIds,functionNames,publisherIds,publisherNames,gradeIds,gradeNames));
	}

	@PostMapping("/deletedBatchFrontUser")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量删除", notes = "传入userName")
	public  R<FrontUserVO> deletedBatchFrontUser(@Valid @RequestBody FrontUserVO frontUserVO) {
		List<String> userNameList = frontUserVO.getUserNameList();
		return R.status(frontUserService.deletedBatchFrontUser(userNameList));
	}

	/**
	 * 查询出版社
	 */
	@GetMapping("/selectPublisher")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "查询出版社", notes = "无参数值")
	public  R<List<PublisherVO>> selectPublisher() {
		return R.data(frontUserService.selectPublisher());
	}

	/**
	 * 批量导出帐号
	 */
	@GetMapping("/exportBatchFrontUserList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "批量导出帐号", notes = "传入userNames")
	public void exportBatchFrontUserList(@ApiParam(required = true) @RequestParam List<String> userNameList) throws IOException, WriteException {
		List<FrontUserVO> userVOList = frontUserService.selecttBatchUserList(userNameList);
		frontUserService.exportExcelForm(userVOList);
	}
}

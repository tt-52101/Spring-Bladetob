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
import com.alibaba.fastjson.JSONArray;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import cn.rzedu.sf.user.vo.FrontUserVO;
import cn.rzedu.sf.user.service.IFrontUserService;
import org.springblade.core.boot.ctrl.BladeController;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private IFrontUserService frontUserService;

	private String serverPath;

	@Value("${serverPath}")
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	private Map<String, String> hardPen = new HashMap<>();

	private Map<String, String> softPen = new HashMap<>();

	public FrontUserController() {
		hardPen.put("/teaching/72", "同步教学");
		hardPen.put(" /teachingPlan/72?mediaType=1", "授课教案");
		hardPen.put("/teachingPlan/72?mediaType=2", "知识宝库");
		hardPen.put("/teachingPlan/72?mediaType=3", "国学视频");
		hardPen.put("/teachingPlan/72?mediaType=4", "创作文案");
		hardPen.put("/teachingPlan/72?mediaType=5", "名师微课");
		hardPen.put("/hardPenQuery/72", "硬笔查询");
		hardPen.put("/strokes", "笔画教学");
		hardPen.put("/radical", "偏旁教学");
		hardPen.put("/structure", "结构教学");
		hardPen.put("/calligraphyTest/72", "书法考试");

		softPen.put("/teaching/71?stageCode=2", "基础教学");
		softPen.put("/teaching/71?stageCode=3", "初中教学");
		softPen.put("/teachingPlan/71?mediaType=3", "国学视频");
		softPen.put("/teachingPlan/71?mediaType=4", "知识宝库");
		softPen.put("/teachingPlan/71?mediaType=5", "名师微课");
		softPen.put("/teachingPlan/71?mediaType=6", "碑帖临摹");
		softPen.put("/teachingPlan/71?mediaType=7", "视频资源");
		softPen.put("/teachingPlan/71?mediaType=8", "书法资源");
		softPen.put("/teachingPlan/71?mediaType=9", "隶书教学");
		softPen.put("/teachingPlan/71?mediaType=10", "篆书教学");
		softPen.put("/teachingPlan/71?mediaType=11", "行书教学");
		softPen.put("/teachingPlan/71?mediaType=12", "篆刻教学");
		softPen.put("/fontsCreation/71", "集字创作");
		softPen.put("/calligraphyTest/71", "书法考试");
	}

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

	/**
	 * 添加注册码
	 */
	@PostMapping("/frontUserRegisterCode")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "添加注册码 ", notes = "username = 注册码")
	public R frontUserRegisterCode(@Valid @RequestBody FrontUserVO frontUserVO) {
		String registerCode = frontUserVO.getUsername();
		Integer studentTerminal = frontUserVO.getStudentTerminal();
		Integer typeId = frontUserVO.getTypeId();
		String provinceCode = frontUserVO.getProvinceCode();
		String cityCode = frontUserVO.getCityCode();
		String districtCode = frontUserVO.getDistrictCode();
		String department = frontUserVO.getDepartment();
		String remark = frontUserVO.getRemark();
		return R.status(frontUserService.frontUserRegisterCode(registerCode,studentTerminal,typeId,provinceCode,cityCode,districtCode,department,remark));
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
	@ApiOperation(value = "查看/查看功能权限", notes = "传入userName")
	public  R<FrontUserVO> selectFrontUserDetail(@ApiParam(value = "userName") @RequestParam(value = "userName")String userName) {
		return R.data(frontUserService.selectFrontUserDetail(userName));
	}

	@GetMapping("/getMenus")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取注册码权限菜单", notes = "传入registerCode")
	public R<Map<String, List<Map<String, String>>>> getMenus(@ApiParam(value = "registerCode") @RequestParam(value = "registerCode")String registerCode) {
		FrontUserVO frontUserVO = frontUserService.selectFrontUserDetail(registerCode);
		Map<String, List<Map<String, String>>> resutMap = new HashMap<>();
		List<Map<String, String>> hardPenList = new ArrayList<>();
		List<Map<String, String>> softPenList = new ArrayList<>();

		if (frontUserVO != null) {
			String functionIdsJSON = frontUserVO.getFunctionId();

			functionIdsJSON = functionIdsJSON.replace("[", "").replace("]", "").replace(" ", "");

			String[] functionIds = functionIdsJSON.split(",");

			for (int i = 0; i < functionIds.length; i++) {
				String functionId = functionIds[i].trim();
				if(functionId != null) {
					String menuName = hardPen.get(functionId);
					if (menuName != null && !"".equals(menuName)) {
						functionId = functionId.contains("?") ? (functionId + "&registerCode=" + registerCode) : (functionId + "?registerCode=" + registerCode);

						functionId = serverPath + functionId;
						Map<String, String> hardPenTemp = new HashMap<>();
						hardPenTemp.put("menuUrl", functionId);
						hardPenTemp.put("menuName", menuName);
						hardPenList.add(hardPenTemp);
					} else {
						String softMenuName = softPen.get(functionId);
						functionId = functionId.contains("?") ? (functionId + "&registerCode=" + registerCode) : (functionId + "?registerCode=" + registerCode);
						functionId = serverPath + functionId;
						if (softMenuName != null && !"".equals(softMenuName)) {
							Map<String, String> softPenTemp = new HashMap<>();
							softPenTemp.put("menuUrl", functionId);
							softPenTemp.put("menuName", softMenuName);
							softPenList.add(softPenTemp);
						}
					}
				}
			}

		}

		resutMap.put("hardPen", hardPenList);
		resutMap.put("softPen", softPenList);
		return R.data(resutMap);

	}


	/**
	 * 编辑
	 */
	@PostMapping("/updateFrontUser")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "编辑", notes = "传入参数")
	public  R updateFrontUser(@Valid @RequestBody FrontUserVO frontUserVO) {
		String userName = frontUserVO.getUsername();
		String passWord = frontUserVO.getPassword();
		Integer studentTerminal = frontUserVO.getStudentTerminal();
		String provinceCode = frontUserVO.getProvinceCode();
		String cityCode = frontUserVO.getCityCode();
		String districtCode = frontUserVO.getDistrictCode();
		String department = frontUserVO.getDepartment();
		String remark = frontUserVO.getRemark();
		return R.status(frontUserService.updateFrontUser(userName,passWord,studentTerminal,provinceCode,cityCode,districtCode,department,remark));
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

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import lombok.AllArgsConstructor;


import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import cn.rzedu.sf.user.vo.FrontUserVO;
import cn.rzedu.sf.user.service.IFrontUserService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	@GetMapping("/userLogin")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "用户登录", notes = "传入userName,passWord")
	public R<FrontUserVO> FrontUserLogin(
		@ApiParam(value = "userName") @RequestParam(value = "userName")String userName,
		@ApiParam(value = "passWord") @RequestParam(value = "passWord")String passWord
	) {
		FrontUserVO userVO = frontUserService.FrontUserLogin(userName,passWord);
		return R.data(userVO);
	}

	/**
	 * 修改密码
	 */
	@GetMapping("/userUpdatePassWord")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "修改密码", notes = "传入userName,oldPassWord,newPassWord")
	public R FrontUserUpdatePassWord(
		@ApiParam(value = "userName") @RequestParam(value = "userName")String userName,
		@ApiParam(value = "OldPassWord") @RequestParam(value = "OldPassWord")String OldPassWord,
		@ApiParam(value = "newPassWord") @RequestParam(value = "newPassWord")String newPassWord
	) {
		return R.status(frontUserService.userUpdatePassword(userName,OldPassWord,newPassWord));
	}

	/**
	 * 查询用户
	 */
	@GetMapping("/selectFrontUserList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "USER条件查询", notes = "传入查询条件")
	public R<IPage<FrontUserVO>> selectFrontUserList(Query query,
													 @ApiParam(value = "userName") @RequestParam(value = "userName",required = false)String userName,
													 @ApiParam(value = "provinceName") @RequestParam(value = "provinceName",required = false)String provinceName,
													 @ApiParam(value = "cityName") @RequestParam(value = "cityName",required = false)String cityName,
													 @ApiParam(value = "districtName") @RequestParam(value = "districtName",required = false)String districtName,
													 @ApiParam(value = "department") @RequestParam(value = "department",required = false)String department,
													 @ApiParam(value = "remark") @RequestParam(value = "remark",required = false)String remark
													 ) {
		IPage<FrontUserVO> pages = frontUserService.selectFrontUserList(Condition.getPage(query),userName,provinceName,cityName,districtName,department,remark);
		return R.data(pages);
	}


	/**
	 * 单用户注册
	 */
	@GetMapping("/frontUserRegister")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "单用户注册", notes = "传入参数")
	public R frontUserRegister(
			@ApiParam(value = "用户名") @RequestParam(value = "userName")String userName,
			@ApiParam(value = "密码") @RequestParam(value = "passWord")String passWord,
			@ApiParam(value = "1=普及版，2=基础版，3=互动版") @RequestParam(value = "typeId",defaultValue = "1",required = false)Integer typeId,
			@ApiParam(value = "用户类型") @RequestParam(value = "typeName",required = false)String typeName,
			@ApiParam(value = "provinceCode") @RequestParam(value = "provinceCode",required = false)String provinceCode,
			@ApiParam(value = "provinceName") @RequestParam(value = "provinceName",required = false)String provinceName,
			@ApiParam(value = "cityCode") @RequestParam(value = "cityCode",required = false)String cityCode,
			@ApiParam(value = "cityName") @RequestParam(value = "cityName",required = false)String cityName,
			@ApiParam(value = "districtCode") @RequestParam(value = "districtCode",required = false)String districtCode,
			@ApiParam(value = "districtName") @RequestParam(value = "districtName",required = false)String districtName,
			@ApiParam(value = "备注") @RequestParam(value = "remark",required = false)String remark,
			@ApiParam(value = "单位") @RequestParam(value = "department",required = false)String department
	) {
		return R.status(frontUserService.frontUserRegister(userName,passWord,typeId,typeName,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,department,remark));
	}

	@GetMapping("/frontUserBatchRegister")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "批量用户注册", notes = "传入参数")
	public void frontUseBatchRegister(
									  @ApiParam(value = "批量生成数量") @RequestParam(value = "batchSize")Integer batchSize,
									  @ApiParam(value = "密码") @RequestParam(value = "passWord",required = false)String passWord,
									  @ApiParam(value = "1=普及版，2=基础版，3=互动版") @RequestParam(value = "typeId",defaultValue = "1",required = false)Integer typeId,
									  @ApiParam(value = "typeName") @RequestParam(value = "typeName",required = false)String typeName,
									  @ApiParam(value = "provinceCode") @RequestParam(value = "provinceCode",required = false)String provinceCode,
									  @ApiParam(value = "provinceName") @RequestParam(value = "provinceName",required = false)String provinceName,
									  @ApiParam(value = "cityCode") @RequestParam(value = "cityCode",required = false)String cityCode,
									  @ApiParam(value = "cityName") @RequestParam(value = "cityName",required = false)String cityName,
									  @ApiParam(value = "districtCode") @RequestParam(value = "districtCode",required = false)String districtCode,
									  @ApiParam(value = "districtName") @RequestParam(value = "districtName",required = false)String districtName,
									  @ApiParam(value = "remark") @RequestParam(value = "remark",required = false)String remark,
									  @ApiParam(value = "department") @RequestParam(value = "department",required = false)String department
	) throws InterruptedException, IOException, WriteException {

		List<FrontUserVO> userVOList = frontUserService.frontUserBatchRegister(batchSize,passWord,typeId,typeName,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,department,remark);
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = requestAttributes.getResponse();
		HttpServletRequest request = requestAttributes.getRequest();
		LocalDateTime localDateTime=LocalDateTime.now();
		String year = localDateTime.toString().substring(0,4);
		String month = localDateTime.toString().substring(5,7);
		String day = localDateTime.toString().substring(8,10);
		String fileName = "UserDetail("+year+month+day+")"+".xls";
		String filePath = request.getSession().getServletContext().getRealPath("") + "/" +fileName;
		File name = new File(filePath);
		// 创建写工作簿对象
		WritableWorkbook workbook = Workbook.createWorkbook(name);
		// 工作表
		WritableSheet ws = workbook.createSheet("userList", 0);
		ws.addCell(new Label(0,0,"username"));
		ws.addCell(new Label(1,0,"password"));
		ws.addCell(new Label(2,0,"type_name"));
		ws.addCell(new Label(3,0,"province_name"));
		ws.addCell(new Label(4,0,"city_name"));
		ws.addCell(new Label(5,0,"district_name"));
		ws.addCell(new Label(6,0,"department"));
		ws.addCell(new Label(7,0,"remark"));
		ws.addCell(new Label(8,0,"create_date"));
		ws.addCell(new Label(9,0,"modify_date"));
		int index = 0;
		for(FrontUserVO frontUser:userVOList){
			ws.addCell(new Label(0,index+1,frontUser.getUsername()));
			ws.addCell(new Label(1,index+1,frontUser.getPassword()));
			ws.addCell(new Label(2,index+1,frontUser.getTypeName()));
			ws.addCell(new Label(3,index+1,frontUser.getProvinceName()));
			ws.addCell(new Label(4,index+1,frontUser.getCityName()));
			ws.addCell(new Label(5,index+1,frontUser.getDistrictName()));
			ws.addCell(new Label(6,index+1,frontUser.getDepartment()));
			ws.addCell(new Label(7,index+1,frontUser.getRemark()));
			ws.addCell(new Label(8,index+1,String.valueOf(frontUser.getCreateDate())));
			ws.addCell(new Label(9,index+1,String.valueOf(frontUser.getModifyDate())));
			index++;
		}
		//开始执行写入操作
		workbook.write();
		workbook.close();
		//下载
		OutputStream out = null;
		response.addHeader("content-disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName, "utf-8"));
		out = response.getOutputStream();
		// inputStream：读文件
		InputStream is = new FileInputStream(filePath);
		byte[] b = new byte[4096];
		int size = is.read(b);
		while (size > 0) {
			out.write(b, 0, size);
			size = is.read(b);
		}
		out.close();
		is.close();

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
	@GetMapping("/updateFrontUser")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "编辑", notes = "传入参数")
	public  R updateFrontUser(
			@ApiParam(value = "userName") @RequestParam(value = "userName")String userName,
			@ApiParam(value = "新用户名") @RequestParam(value = "newUserName",required = false)String newUserName,
			@ApiParam(value = "password") @RequestParam(value = "passWord",required = false)String passWord,
			@ApiParam(value = "provinceCode") @RequestParam(value = "provinceCode",required = false)String provinceCode,
			@ApiParam(value = "provinceName") @RequestParam(value = "provinceName",required = false)String provinceName,
			@ApiParam(value = "cityCode") @RequestParam(value = "cityCode",required = false)String cityCode,
			@ApiParam(value = "cityName") @RequestParam(value = "cityName",required = false)String cityName,
			@ApiParam(value = "districtCode") @RequestParam(value = "districtCode",required = false)String districtCode,
			@ApiParam(value = "districtName") @RequestParam(value = "districtName",required = false)String districtName,
			@ApiParam(value = "remark") @RequestParam(value = "remark",required = false)String remark,
			@ApiParam(value = "department") @RequestParam(value = "department",required = false)String department
			) {
		return R.status(frontUserService.updateFrontUser(userName,newUserName,passWord,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,department,remark));
	}

	@GetMapping("/deleteFrontUser")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入userName")
	public  R deleteFrontUser(@ApiParam(value = "userName") @RequestParam(value = "userName")String userName) {
		return R.status(frontUserService.deleteFrontUser(userName));
	}

	@GetMapping("/updateFunctionAuth")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "功能权限", notes = "传入参数")
	public  R<FrontUserVO> updateFunctionAuth(
			@ApiParam(value = "userName") @RequestParam(value = "userName")String userName,
			@ApiParam(value = "functionId") @RequestParam(value = "functionId")List<String> functionId,
			@ApiParam(value = "functionName") @RequestParam(value = "functionName")List<String> functionName,
			@ApiParam(value = "publisherId") @RequestParam(value = "publisherId")List<String> publisherId,
			@ApiParam(value = "publisherName") @RequestParam(value = "publisherName")List<String> publisherName,
			@ApiParam(value = "gradeId") @RequestParam(value = "gradeId")List<String> gradeId,
			@ApiParam(value = "gradeName") @RequestParam(value = "gradeName") List<String> gradeName
			) {
		return R.status(frontUserService.updateFunctionAuth(userName,functionId,functionName,publisherId,publisherName,gradeId,gradeName));
	}

	@GetMapping("/deletedBatchFrontUser")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量删除", notes = "传入userName")
	public  R<FrontUserVO> deletedBatchFrontUser(@ApiParam(value = "userNameList") @RequestParam(value = "userNameList")List<String> userNameList) {
		return R.status(frontUserService.deletedBatchFrontUser(userNameList));
	}
}

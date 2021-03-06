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
package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.FrontUser;
import cn.rzedu.sf.user.vo.FrontUserVO;
import cn.rzedu.sf.user.mapper.FrontUserMapper;
import cn.rzedu.sf.user.service.IFrontUserService;
import cn.rzedu.sf.user.vo.PublisherVO;
import cn.rzedu.sf.user.vo.RegionVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-04-12
 */
@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements IFrontUserService {

	public String userRandom() throws InterruptedException {
		StringBuffer userName=new StringBuffer();
		Integer a = (int)(Math.random()*90+10);
		userName.append(a.toString());
		Calendar c=Calendar.getInstance();
		String time=new SimpleDateFormat("yyyy-MM-ddHHmmssSSS").format(c.getTime()).toString();
		userName.append(time.substring(14,19));
		Long sys=System.currentTimeMillis();
		userName.append(sys.toString().substring(9, 13));
		Thread.sleep(100);
		return userName.toString();
	}

	@Override
	public List<PublisherVO> selectPublisher() {
		return baseMapper.selectPublisher();
	}


	@Override
	public FrontUserVO FrontUserLogin(String userName,String passWord) {
		FrontUserVO user = baseMapper.selectFrontUserDetail(userName);
		if(user != null && user.getPassword().equals(passWord) ){
			LocalDateTime lastUseTime = LocalDateTime.now();
			int status = 1;
			baseMapper.userUpdateStatus(userName,status,lastUseTime);
			return baseMapper.selectFrontUserDetail(userName);
		}else
			return null;
	}

	@Override
	public boolean userUpdatePassword(String userName, String oldPassWord, String newPassWord) {
		FrontUserVO user = baseMapper.selectFrontUserDetail(userName);
		if(user != null && user.getPassword().equals(oldPassWord)){
			return SqlHelper.retBool(baseMapper.userUpdatePassword(userName,newPassWord));
		}else
			return false;
	}

	@Override
	public boolean frontUserRegister(String username, String password,int typeId, String provinceCode, String cityCode, String districtCode,
									 String functionId,String functionName,String publisherId,String publisherName,String gradeId,String gradeName,String department, String remark) {
		LocalDateTime localDateTime=LocalDateTime.now();
		String userName = baseMapper.selectUserName(username);
		String provinceName = baseMapper.selectRegionProvinceName(provinceCode);
		String cityName = baseMapper.selectRegionCityName(cityCode); ;
		String districtName = baseMapper.selectRegionDistrictName(districtCode);
		String typeName = null;
		if(typeId == 1){
			typeName = "普及版";
		}else if (typeId == 2){
			typeName = "基础版";
		}else if (typeId == 3){
			typeName = "互动版";
		}
		if (userName == null || userName.equals(" ")){
			return SqlHelper.retBool(baseMapper.frontUserRegister(username,password,typeId,typeName,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,functionId,functionName,publisherId,publisherName,gradeId,gradeName,department,remark,localDateTime,localDateTime));
		}else
			return false;
	}

	@Override
	public boolean frontUserRegisterCode(String registerCode, int studentTerminal,int typeId, String provinceCode, String cityCode, String districtCode,
										 String functionId,String functionName,String publisherId,String publisherName,String gradeId,String gradeName,String department, String remark) {
		LocalDateTime localDateTime=LocalDateTime.now();
		String registercode = baseMapper.selectUserName(registerCode);
		String provinceName = baseMapper.selectRegionProvinceName(provinceCode);
		String cityName = baseMapper.selectRegionCityName(cityCode); ;
		String districtName = baseMapper.selectRegionDistrictName(districtCode);
		String typeName = null;
		if(typeId == 1){
			typeName = "普及版";
		}else if (typeId == 2){
			typeName = "基础版";
		}else if (typeId == 3){
			typeName = "互动版";
		}
		if (registercode == null || registercode.equals(" ")){
			return SqlHelper.retBool(baseMapper.frontUserRegisterCode(registerCode,studentTerminal,typeId,typeName,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,functionId,functionName,publisherId,publisherName,gradeId,gradeName,department,remark,localDateTime,localDateTime));
		}else
			return false;
	}

	@Override
	public List<String> frontUserBatchRegister(int batchSize, String passWord, int typeId, String provinceCode, String cityCode, String districtCode,
											   String functionId,String functionName,String publisherId,String publisherName,String gradeId,String gradeName,String department, String remark) throws InterruptedException {
		List<String> userNameList = new ArrayList<>();
		String dfpassWord = "123456";
		LocalDateTime localDateTime=LocalDateTime.now();
		String provinceName = baseMapper.selectRegionProvinceName(provinceCode);
		String cityName = baseMapper.selectRegionCityName(cityCode); ;
		String districtName = baseMapper.selectRegionDistrictName(districtCode);
		String typeName = null;
		if(typeId == 1){
			typeName = "普及版";
		}else if (typeId == 2){
			typeName = "基础版";
		}else if (typeId == 3){
			typeName = "互动版";
		}
		for (int i = 0;i < batchSize;i++){
			String username = this.userRandom();
			if(baseMapper.selectUserName(username) == null || baseMapper.selectUserName(username).equals(" ")){
				if(passWord == null || passWord.equals("")){
					baseMapper.frontUserRegister(username,dfpassWord,typeId,typeName,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,functionId,functionName,publisherId,publisherName,gradeId,gradeName,department,remark,localDateTime,localDateTime);
					userNameList.add(username);
				}else {
					baseMapper.frontUserRegister(username,passWord,typeId,typeName,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,functionId,functionName,publisherId,publisherName,gradeId,gradeName,department,remark,localDateTime,localDateTime);
					userNameList.add(username);
				}
			}else{
				i -= 1;
			}
		}
		return userNameList;
	}

	@Override
	public IPage<FrontUserVO> selectFrontUserList(IPage<FrontUserVO> page, String userName,String typeId, String provinceCode, String cityCode, String districtCode, String department, String remark) {
		String provinceName = baseMapper.selectRegionProvinceName(provinceCode);
		String cityName = baseMapper.selectRegionCityName(cityCode); ;
		String districtName = baseMapper.selectRegionDistrictName(districtCode);

		return page.setRecords(baseMapper.selectFrontUserList(page,userName,typeId,provinceName,cityName,districtName,department,remark)) ;
	}

	@Override
	public FrontUserVO selectFrontUserDetail(String userName) {
		return baseMapper.selectFrontUserDetail(userName);
	}

	@Override
	public boolean updateFrontUser(String userName, String passWord,Integer studentTerminal, String provinceCode, String cityCode, String districtCode,String department, String remark)
	{
		String provinceName = baseMapper.selectRegionProvinceName(provinceCode);
		String cityName = baseMapper.selectRegionCityName(cityCode);
		String districtName = baseMapper.selectRegionDistrictName(districtCode);

		return SqlHelper.retBool(baseMapper.updateFrontUser(userName,passWord,studentTerminal,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,department,remark));

	}

	@Override
	public boolean deleteFrontUser(String userName) {
		return SqlHelper.retBool(baseMapper.deleteFrontUser(userName));
	}

	@Override
	public boolean updateFunctionAuth(String userName, List<String> functionIds, List<String> functionNames, List<String> publisherIds, List<String> publisherNames, List<String> gradeIds, List<String> gradeNames) {
		String functionId = functionIds.toString();
		String functionName = functionNames.toString();
		String publisherId = publisherIds.toString();
		String publisherName = publisherNames.toString();
		String gradeId = gradeIds.toString();
		String gradeName = gradeNames.toString();
		return SqlHelper.retBool(baseMapper.updateFunctionAuth(userName,functionId,functionName,publisherId,publisherName,gradeId,gradeName));
	}

	@Override
	public boolean deletedBatchFrontUser(List<String> userNameList) {
		int count = 0;
		for (String userName : userNameList){
			int r = baseMapper.deleteFrontUser(userName);
			count += r;
		}
		if (count > 0){
			return true;
		}else
		return false;
	}

	@Override
	public boolean updateBatchFunctionAuth(List<String> userNameList, List<String> functionIds, List<String> functionNames, List<String> publisherIds, List<String> publisherNames, List<String> gradeIds, List<String> gradeNames) {
		String functionId = functionIds.toString();
		String functionName = functionNames.toString();
		String publisherId = publisherIds.toString();
		String publisherName = publisherNames.toString();
		String gradeId = gradeIds.toString();
		String gradeName = gradeNames.toString();
		int count = 0;
		for (String userName : userNameList){
			int r =baseMapper.updateFunctionAuth(userName,functionId,functionName,publisherId,publisherName,gradeId,gradeName);
			count+=r;
		}
		if (count>0){
			return true;
		}else
			return false;
	}

	@Override
	public List<FrontUserVO> selecttBatchUserList(List<String> userNameList) {
		List<FrontUserVO> userVOList = new ArrayList<>();
		for (String userName : userNameList){
			if (baseMapper.selectFrontUserDetail(userName)!=null){
				userVOList.add(baseMapper.selectFrontUserDetail(userName));
			}
		}
		return userVOList;
	}


	@Override
	public void exportExcelForm(List<FrontUserVO> userVOList) throws IOException, WriteException {
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
		String status;
		// 创建写工作簿对象
		WritableWorkbook workbook = Workbook.createWorkbook(name);
		// 工作表
		WritableSheet ws = workbook.createSheet("userList", 0);
		ws.addCell(new Label(0,0,"id"));
		ws.addCell(new Label(1,0,"username"));
		ws.addCell(new Label(2,0,"password"));
		ws.addCell(new Label(3,0,"type_name"));
		ws.addCell(new Label(4,0,"province_name"));
		ws.addCell(new Label(5,0,"city_name"));
		ws.addCell(new Label(6,0,"district_name"));
		ws.addCell(new Label(7,0,"department"));
		ws.addCell(new Label(8,0,"remark"));
		ws.addCell(new Label(9,0,"function_name"));
		ws.addCell(new Label(10,0,"publisher_name"));
		ws.addCell(new Label(11,0,"grade_name"));
		ws.addCell(new Label(12,0,"create_date"));
		ws.addCell(new Label(13,0,"modify_date"));
		ws.addCell(new Label(14,0,"status"));
		ws.addCell(new Label(15,0,"last_use_time"));
		int index = 0;
		for(FrontUserVO frontUser:userVOList){
			if (frontUser.getStatus() == 0){
				status = "未登录";
			}else
				status = "已登录";
			ws.addCell(new Label(0,index+1,String.valueOf(frontUser.getId())));
			ws.addCell(new Label(1,index+1,frontUser.getUsername()));
			ws.addCell(new Label(2,index+1,frontUser.getPassword()));
			ws.addCell(new Label(3,index+1,frontUser.getTypeName()));
			ws.addCell(new Label(4,index+1,frontUser.getProvinceName()));
			ws.addCell(new Label(5,index+1,frontUser.getCityName()));
			ws.addCell(new Label(6,index+1,frontUser.getDistrictName()));
			ws.addCell(new Label(7,index+1,frontUser.getDepartment()));
			ws.addCell(new Label(8,index+1,frontUser.getRemark()));
			ws.addCell(new Label(9,index+1,frontUser.getFunctionName()));
			ws.addCell(new Label(10,index+1,frontUser.getPublisherName()));
			ws.addCell(new Label(11,index+1,frontUser.getGradeName()));
			ws.addCell(new Label(12,index+1,String.valueOf(frontUser.getCreateDate())));
			ws.addCell(new Label(13,index+1,String.valueOf(frontUser.getModifyDate())));
			ws.addCell(new Label(14,index+1,status));
			ws.addCell(new Label(15,index+1,String.valueOf(frontUser.getLastUseTime())));
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



}

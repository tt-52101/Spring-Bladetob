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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.File;
import java.io.IOException;
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
	public boolean frontUserRegister(String username, String password,int typeId,String typeName, String province_code, String province_name, String city_code, String city_name, String district_code, String district_name, String department, String remark) {
		LocalDateTime localDateTime=LocalDateTime.now();
		String userName = baseMapper.selectUserName(username);
		if (userName == null || userName.equals(" ")){
			return SqlHelper.retBool(baseMapper.frontUserRegister(username,password,typeId,typeName,province_code,province_name,city_code,city_name,district_code,district_name,department,remark,localDateTime,localDateTime));
		}else
			return false;
	}

	@Override
	public void frontUserBatchRegister(int batchSize, String passWord, int typeId, String typeName, String province_code, String province_name, String city_code, String city_name, String district_code, String district_name, String department, String remark) throws InterruptedException, IOException, WriteException {
		List<FrontUserVO> userVOList = new ArrayList<>();
		FrontUserVO frontUserVO;
		String dfpassWord = "123456";
		LocalDateTime localDateTime=LocalDateTime.now();
		String year = localDateTime.toString().substring(0,4);
		String month = localDateTime.toString().substring(5,7);
		String day = localDateTime.toString().substring(8,10);
		String filePath = "UserDetail("+year+month+day+")"+".xlsx";
		File userFile = new File(filePath);
		WritableWorkbook wwb = Workbook.createWorkbook(userFile);
		if (!userFile.exists() || userFile.isDirectory()) {
			userFile.createNewFile();
		}else {
			userFile.delete();
		}
		for (int i = 0;i < batchSize;i++){
			String username = this.userRandom();
			if(baseMapper.selectUserName(username) == null || baseMapper.selectUserName(username).equals(" ")){
				if(passWord == null || passWord.equals("")){
					baseMapper.frontUserRegister(username,dfpassWord,typeId,typeName,province_code,province_name,city_code,city_name,district_code,district_name,department,remark,localDateTime,localDateTime);
					frontUserVO = new FrontUserVO();
					frontUserVO.setUsername(username);
					frontUserVO.setPassword(dfpassWord);
					frontUserVO.setTypeId(typeId);
					frontUserVO.setTypeName(typeName);
					frontUserVO.setProvinceCode(province_code);
					frontUserVO.setProvinceName(province_name);
					frontUserVO.setCityCode(city_code);
					frontUserVO.setCityName(city_name);
					frontUserVO.setDistrictCode(district_code);
					frontUserVO.setDistrictName(district_name);
					frontUserVO.setDepartment(department);
					frontUserVO.setRemark(remark);
					frontUserVO.setCreateDate(localDateTime);
					frontUserVO.setModifyDate(localDateTime);
					userVOList.add(frontUserVO);
				}else {
					baseMapper.frontUserRegister(username,passWord,typeId,typeName,province_code,province_name,city_code,city_name,district_code,district_name,department,remark,localDateTime,localDateTime);
					frontUserVO = new FrontUserVO();
					frontUserVO.setUsername(username);
					frontUserVO.setPassword(passWord);
					frontUserVO.setTypeId(typeId);
					frontUserVO.setTypeName(typeName);
					frontUserVO.setProvinceCode(province_code);
					frontUserVO.setProvinceName(province_name);
					frontUserVO.setCityCode(city_code);
					frontUserVO.setCityName(city_name);
					frontUserVO.setDistrictCode(district_code);
					frontUserVO.setDistrictName(district_name);
					frontUserVO.setDepartment(department);
					frontUserVO.setRemark(remark);
					frontUserVO.setCreateDate(localDateTime);
					frontUserVO.setModifyDate(localDateTime);
					userVOList.add(frontUserVO);
				}
			}else{
				i -= 1;
			}
		}
		WritableSheet ws = wwb.createSheet("列表 1", 0);
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

	}

	@Override
	public IPage<FrontUserVO> selectFrontUserList(IPage<FrontUserVO> page, String userName, String provinceName, String cityName, String districtName, String department, String remark) {

		return page.setRecords(baseMapper.selectFrontUserList(page,userName,provinceName,cityName,districtName,department,remark)) ;
	}

	@Override
	public FrontUserVO selectFrontUserDetail(String userName) {
		return baseMapper.selectFrontUserDetail(userName);
	}

	@Override
	public boolean updateFrontUser(String userName, String newUserName, String passWord, String provinceCode, String provinceName, String cityCode, String cityName, String districtCode, String districtName,String department, String remark)
	{
		String username = baseMapper.selectUserName(newUserName);
		if (username == null || username.equals(" ")){
			return SqlHelper.retBool(baseMapper.updateFrontUser(userName,newUserName,passWord,provinceCode,provinceName,cityCode,cityName,districtCode,districtName,remark,department));
		}else
			return false;
	}

	@Override
	public boolean deleteFrontUser(String userName) {
		return SqlHelper.retBool(baseMapper.deleteFrontUser(userName));
	}

	@Override
	public boolean updateFunctionAuth(String userName, List<String> functionId, List<String> functionName, List<String> publisherId, List<String> publisherName, List<String> gradeId, List<String> gradeName) {

		return SqlHelper.retBool(baseMapper.updateFunctionAuth(userName,functionId.toString(),functionName.toString(),publisherId.toString(),publisherName.toString(),gradeId.toString(),gradeName.toString()));
	}

	@Override
	public boolean deletedBatchFrontUser(List<String> userNameList) {
		int size = userNameList.size();
		int index = 0;
		for (String userName : userNameList){
			baseMapper.deleteFrontUser(userName);
			index ++;
		}
		if (size == index){
			return true;
		}else
		return false;
	}


}

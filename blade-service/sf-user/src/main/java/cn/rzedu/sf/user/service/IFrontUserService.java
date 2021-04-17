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
package cn.rzedu.sf.user.service;


import cn.rzedu.sf.user.entity.FrontUser;
import cn.rzedu.sf.user.vo.FrontUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-04-12
 */
public interface IFrontUserService extends IService<FrontUser> {

	boolean CreateExcelForm(List<FrontUserVO> frontUserVOList) throws IOException, WriteException;

	/**
	 *用户登录
	 * @param userName
	 * @return
	 */
	FrontUserVO FrontUserLogin(String userName,String passWord);

	/**
	 * 修改密码
	 * @param userName
	 * @param oldPassWord
	 * @param newPassWord
	 * @return
	 */
	boolean userUpdatePassword(String userName,String oldPassWord,String newPassWord);

	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @param typeId
	 * @param typeName
	 * @param province_code
	 * @param province_name
	 * @param city_code
	 * @param city_name
	 * @param district_code
	 * @param district_name
	 * @param remark
	 * @param department
	 * @return
	 */
	boolean frontUserRegister(String username, String password,int typeId,String typeName, String province_code, String province_name, String city_code, String city_name, String district_code, String district_name,String department, String remark);

	/**
	 * 批量生成用户
	 * @param batchSize
	 * @param password
	 * @param typeId
	 * @param typeName
	 * @param province_code
	 * @param province_name
	 * @param city_code
	 * @param city_name
	 * @param district_code
	 * @param district_name
	 * @param department
	 * @param remark
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws WriteException
	 */
	List<FrontUserVO> frontUserBatchRegister(int batchSize, String password,int typeId,String typeName, String province_code, String province_name, String city_code, String city_name, String district_code, String district_name, String department, String remark) throws InterruptedException, IOException, WriteException;

	/**
	 *
	 * 用户管理查询
	 * @param page
	 * @param userName
	 * @param provinceName
	 * @param cityName
	 * @param districtName
	 * @param department
	 * @param remark
	 * @return
	 */
	IPage<FrontUserVO> selectFrontUserList(IPage<FrontUserVO> page,String userName,String provinceName,String cityName,String districtName,String department,String remark);

	/**
	 * 查看
	 * @param userName
	 * @return
	 */
	FrontUserVO selectFrontUserDetail(String  userName);

	/**
	 * 编辑
	 * @param userName
	 * @param newUserName
	 * @param passWord
	 * @param provinceCode
	 * @param provinceName
	 * @param cityCode
	 * @param cityName
	 * @param districtCode
	 * @param districtName
	 * @param department
	 * @param remark
	 * @return
	 */
	boolean updateFrontUser(String userName,String newUserName,String passWord,String provinceCode, String provinceName, String cityCode, String cityName, String districtCode, String districtName,String department, String remark);

	/**
	 * 删除
	 * @param userName
	 * @return
	 */
	boolean deleteFrontUser(String userName);

	/**
	 * 功能权限
	 * @param userName
	 * @param functionId
	 * @param functionName
	 * @param publisherId
	 * @param publisherName
	 * @param gradeId
	 * @param gradeName
	 * @return
	 */
	boolean updateFunctionAuth(String userName,List<String> functionIds,List<String> functionNames,List<String> publisherIds,List<String> publisherNames,List<String> gradeIds,List<String> gradeNames);

	/**
	 * 批量删除
	 * @param userNameList
	 * @return
	 */
	boolean deletedBatchFrontUser(List<String> userNameList);
}


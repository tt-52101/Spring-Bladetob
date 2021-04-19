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
import cn.rzedu.sf.user.vo.PublisherVO;
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


	/**
	 * 查询出版社
	 * @return
	 */
	List<PublisherVO> selectPublisher();

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
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @param remark
	 * @param department
	 * @param remark
	 * @param department
	 * @return
	 */
	boolean frontUserRegister(String username, String password,int typeId, String provinceCode,  String cityCode, String districtCode, String department, String remark);

	/**
	 * 批量生成用户
	 * @param batchSize
	 * @param password
	 * @param typeId
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @param remark
	 * @param department
	 * @param department
	 * @param remark
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws WriteException
	 */
	List<String> frontUserBatchRegister(int batchSize, String password,int typeId, String provinceCode, String cityCode, String districtCode, String department, String remark) throws InterruptedException;

	/**
	 *
	 * 用户管理查询
	 * @param page
	 * @param userName
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @param department
	 * @param remark
	 * @return
	 */
	IPage<FrontUserVO> selectFrontUserList(IPage<FrontUserVO> page,String userName,String typeId,String provinceCode,String cityCode,String districtCode,String department,String remark);

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
	 * @param cityCode
	 * @param districtCode
	 * @param department
	 * @param remark
	 * @return
	 */
	boolean updateFrontUser(String userName,String newUserName,String passWord,String provinceCode, String cityCode, String districtCode,String department, String remark);

	/**
	 * 删除
	 * @param userName
	 * @return
	 */
	boolean deleteFrontUser(String userName);

	/**
	 * 功能权限
	 * @param userName
	 * @param functionIds
	 * @param functionNames
	 * @param publisherIds
	 * @param publisherNames
	 * @param gradeIds
	 * @param gradeNames
	 * @return
	 */
	boolean updateFunctionAuth(String userName,List<String> functionIds,List<String> functionNames,List<String> publisherIds,List<String> publisherNames,List<String> gradeIds,List<String> gradeNames);

	/**
	 * 批量删除
	 * @param userNameList
	 * @return
	 */
	boolean deletedBatchFrontUser(List<String> userNameList);

	/**
	 * 批量更改权限
	 * @param userNameList
	 * @param functionIds
	 * @param functionNames
	 * @param publisherIds
	 * @param publisherNames
	 * @param gradeIds
	 * @param gradeNames
	 * @return
	 */
	boolean updateBatchFunctionAuth(List<String> userNameList,List<String> functionIds,List<String> functionNames,List<String> publisherIds,List<String> publisherNames,List<String> gradeIds,List<String> gradeNames);

	/**
	 * 查询批量帐号
	 * @param userNameList
	 * @return
	 */
	List<FrontUserVO> selecttBatchUserList(List<String> userNameList);

	/**
	 * 批量导出帐号
	 * @param frontUserVOList
	 * @throws IOException
	 * @throws WriteException
	 */
	void exportExcelForm(List<FrontUserVO> frontUserVOList) throws IOException, WriteException;
}


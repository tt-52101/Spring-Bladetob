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
package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.FrontUser;
import cn.rzedu.sf.user.vo.FrontUserVO;
import cn.rzedu.sf.user.vo.PublisherVO;
import cn.rzedu.sf.user.vo.RegionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2021-04-12
 */
public interface FrontUserMapper extends BaseMapper<FrontUser> {


	/**
	 * 查询出版社
	 * @return
	 */
	List<PublisherVO> selectPublisher();

	/**
	 * 查询区域
	 * @return
	 */
	String selectRegionProvinceName(String provinceCode);
	String selectRegionCityName(String cityCode);
	String selectRegionDistrictName(String districtCode);



	/**
	 *
	 *user详细信息
	 * @param userName
	 * @return
	 */
	FrontUserVO selectFrontUserDetail(String userName);

	/**
	 * 修改密码
	 * @param userName
	 * @param passWord
	 * @return
	 */
	int userUpdatePassword(String userName,String passWord);

	/**
	 * 更新用户状态
	 * @param userName
	 * @param status
	 * @param lastUseTime
	 * @return
	 */
	int userUpdateStatus(String userName, Integer status, LocalDateTime lastUseTime);
	/**
	 *查询user
	 * @param page
	 * @param userName
	 * @param provinceName
	 * @param cityName
	 * @param districtName
	 * @param department
	 * @param remark
	 * @return
	 */
	List<FrontUserVO> selectFrontUserList(IPage page,String userName,String typeId, String provinceName,String cityName,String districtName,String department,String remark);

	/**
	 *用户注册
	 * @param username
	 * @param password
	 * @param provinceCode
	 * @param provinceName
	 * @param cityCode
	 * @param cityName
	 * @param districtCode
	 * @param districtName
	 * @param remark
	 * @param department
	 * @param createDate
	 * @param modifyDate
	 * @return
	 */
	int frontUserRegister(String username, String password,int typeId,String typeName, String provinceCode, String provinceName, String cityCode, String cityName, String districtCode, String districtName,String department, String remark,
						  LocalDateTime createDate,LocalDateTime modifyDate);

	int frontUserRegisterCode(String registerCode, int studentTerminal,int typeId,String typeName, String provinceCode, String provinceName, String cityCode, String cityName, String districtCode, String districtName,String department, String remark,
						  LocalDateTime createDate,LocalDateTime modifyDate);

	/**
	 * 查询用户是否存在
	 * @param userName
	 * @return
	 */
	String selectUserName(String userName);

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
	int updateFrontUser(String userName,String newUserName,String passWord,String provinceCode, String provinceName, String cityCode, String cityName, String districtCode, String districtName,String department, String remark);

	/**
	 * 删除
	 * @param userName
	 * @return
	 */
	int deleteFrontUser(String userName);

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
	int updateFunctionAuth(String userName,String functionId,String functionName,String publisherId,String publisherName,String gradeId,String gradeName);

}

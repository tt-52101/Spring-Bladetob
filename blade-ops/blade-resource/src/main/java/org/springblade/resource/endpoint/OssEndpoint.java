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
package org.springblade.resource.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.oss.AliossTemplate;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.oss.model.OssFile;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.FileUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.service.IEntityFileService;
import org.springblade.resource.utils.FileMd5Util;
import org.springblade.resource.vo.FileResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
public class OssEndpoint {

//	private QiniuTemplate qiniuTemplate;

	private AliossTemplate aliossTemplate;
	private IEntityFileService entityFileService;
	private OssProperties ossProperties;


	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return Bucket
	 */
	@SneakyThrows
	@PostMapping("/make-bucket")
	public R makeBucket(@RequestParam String bucketName) {
		aliossTemplate.makeBucket(bucketName);
		return R.success("创建成功");
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-bucket")
	public R removeBucket(@RequestParam String bucketName) {
		aliossTemplate.removeBucket(bucketName);
		return R.success("删除成功");
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileName       存储桶对象名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/copy-file")
	public R copyFile(@RequestParam String fileName, @RequestParam String destBucketName, String destFileName) {
		aliossTemplate.copyFile(fileName, destBucketName, destFileName);
		return R.success("操作成功");
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public R<OssFile> statFile(@RequestParam String fileName) {
		return R.data(aliossTemplate.statFile(fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public R<String> filePath(@RequestParam String fileName) {
		return R.data(aliossTemplate.filePath(fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	public R<String> fileLink(@RequestParam String fileName) {
		return R.data(aliossTemplate.fileLink(fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	@ApiOperation(value = "上传文件", notes = "oss上传文件接口" , position = 1)
	public R<FileResult> putFile(@RequestParam MultipartFile file) {
		FileResult fileResult = new FileResult();

		String md5 = FileMd5Util.getFileMd5(file.getInputStream(), 5);
		EntityFile entityFile = entityFileService.findFileByMD5(md5);
		if(entityFile == null){
			BladeFile bladeFile = aliossTemplate.putFile(file.getOriginalFilename(), file.getInputStream());

			entityFile = new EntityFile();
			entityFile.setFileName(bladeFile.getUuid());
			entityFile.setRealFileName(file.getOriginalFilename());
//			entityFile.setFtpCode("");
			entityFile.setMd5Code(md5);
			entityFile.setSuffix(FileUtil.getFileExtension(file.getOriginalFilename()));
//			entityFile.setThumbnailUrl("");
			entityFile.setSize((double)file.getSize());
			entityFile.setUrl(bladeFile.getName());
			entityFile.setUuid(bladeFile.getUuid());
			entityFile.setModifyDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			entityFile.setCreateDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			entityFileService.save(entityFile);
		}

		fileResult.setUuid(entityFile.getUuid());
		fileResult.setOriginalName(entityFile.getRealFileName());
		fileResult.setLink(ossProperties.getHttpPrefix().concat(StringPool.SLASH).concat(entityFile.getUrl()));
		fileResult.setThumbnailUrl(entityFile.getThumbnailUrl());
		fileResult.setName(entityFile.getUrl());

		return R.data(fileResult);
	}

	@SneakyThrows
	@PostMapping("/find-file-by-uuid")
	@ApiOperation(value = "查找文件", notes = "查找文件通过uuid" , position = 2)
	public R<FileResult> findFileByUuid(@RequestParam String uuid,
										@ApiParam(value = "userId") @RequestParam(value = "userId",required = false) Integer userId,
										@ApiParam(value = "username") @RequestParam(value = "username",required = false) String username,
										@ApiParam(value = "资源ID") @RequestParam(value = "resourceId",required = false) Integer resourceId,
										@ApiParam(value = "subject,71 = 软笔,72=硬笔") @RequestParam(value = "subject",required = false) Integer subject,
										@ApiParam(value = "mediaType") @RequestParam(value = "mediaType",required = false) Integer mediaType) {

		if (resourceId != null && subject!=null && mediaType != null){
			entityFileService.saveBrowsingHistory(userId,username,resourceId,subject,mediaType);
		}


		FileResult fileResult = new FileResult();

		EntityFile entityFile = entityFileService.findFileByUuid(uuid);

		if(entityFile != null){
			fileResult.setUuid(entityFile.getUuid());
			fileResult.setOriginalName(entityFile.getRealFileName());
			fileResult.setLink(ossProperties.getHttpPrefix().concat(StringPool.SLASH).concat(entityFile.getUrl()));
			fileResult.setThumbnailUrl(entityFile.getThumbnailUrl());
			fileResult.setName(entityFile.getUrl());
		}

		return R.data(fileResult);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-by-name")
	public R<BladeFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
		BladeFile bladeFile = aliossTemplate.putFile(fileName, file.getInputStream());
		return R.data(bladeFile);
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	public R removeFile(@RequestParam String fileName) {
		aliossTemplate.removeFile(fileName);
		return R.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *s
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	public R removeFiles(@RequestParam String fileNames) {
		aliossTemplate.removeFiles(Func.toStrList(fileNames));
		return R.success("操作成功");
	}

//	public static void main(String[] args) {
//		LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//
//		LocalDateTime localDateTime1 = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
//
//		System.out.println(localDateTime);
//		System.out.println(localDateTime1);
//	}
}

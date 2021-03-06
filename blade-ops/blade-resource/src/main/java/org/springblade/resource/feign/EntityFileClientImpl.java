package org.springblade.resource.feign;

import com.alibaba.fastjson.JSON;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import lombok.AllArgsConstructor;
import org.springblade.core.oss.AliossTemplate;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.FileUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.service.IEntityFileService;
import org.springblade.resource.utils.FileMd5Util;
import org.springblade.resource.utils.VodUploadUtil;
import org.springblade.resource.vo.FileResult;
import org.springblade.resource.vo.VodResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.springblade.resource.utils.VodUploadUtil.initVodClient;

@RestController
@AllArgsConstructor
public class EntityFileClientImpl implements EntityFileClient {

	private IEntityFileService entityFileService;
	private OssProperties ossProperties;
    private AliossTemplate aliossTemplate;

	/**
	 *  hystrix
	 *  熔断、隔离、Fallback、cache、监控等功能，能够在一个、或多个依赖同时出现问题时保证系统依然可用
	 * @param entityFile 主键
	 * @return
	 */
	@Override
	@PostMapping(API_PREFIX + "/add")
	public Boolean add(EntityFile entityFile) {
		boolean save = entityFileService.save(entityFile);
		return save;
	}

	@Override
	public EntityFile findFileByMD5(String md5) {
		if(md5 == null || "".equals(md5)){
			return null;
		}
		int i = 100 / 0;
		return entityFileService.findFileByMD5(md5);
	}

	@Override
	public EntityFile upload(File file) throws IOException {

		String md5 = FileMd5Util.getFileMd5(new FileInputStream(file), 5);
		EntityFile entityFile = entityFileService.findFileByMD5(md5);

		if (entityFile == null) {
			entityFile = new EntityFile();
			UploadStreamResponse response = VodUploadUtil.uploadStream(ossProperties.getAccessKey(), ossProperties.getSecretKey(), file.getName(), file.getName(), new FileInputStream(file));
			if (response.isSuccess()) {
				entityFile.setFileName(file.getName());
				entityFile.setRealFileName(file.getName());
				entityFile.setMd5Code(md5);
				entityFile.setSuffix(FileUtil.getFileExtension(file.getName()));
				entityFile.setSize((double) file.length());
				entityFile.setUuid(response.getVideoId());
				entityFile.setModifyDate(LocalDateTime.now());
				entityFile.setCreateDate(LocalDateTime.now());
				entityFileService.save(entityFile);
			}
			return entityFile;
		}
		return entityFile;
	}

    @Override
    public EntityFile uploadImage(File file) throws IOException {
        String md5 = FileMd5Util.getFileMd5(new FileInputStream(file), 5);
        EntityFile entityFile = entityFileService.findFileByMD5(md5);

        if (entityFile == null) {

            BladeFile bladeFile = aliossTemplate.putFile(file.getName(), new FileInputStream(file));

            entityFile = new EntityFile();
            entityFile.setFileName(bladeFile.getUuid());
            entityFile.setRealFileName(file.getName());
            entityFile.setMd5Code(md5);
            entityFile.setSuffix(FileUtil.getFileExtension(file.getName()));
            entityFile.setSize((double)file.length());
            entityFile.setUrl(bladeFile.getName());
            entityFile.setUuid(bladeFile.getUuid());
            entityFile.setModifyDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            entityFile.setCreateDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            entityFileService.save(entityFile);
            return entityFile;
        }
        return entityFile;
    }

	@Override
	public VodResult uploadVodMultipartFile(MultipartFile file) throws IOException {
		VodResult vodResult = new VodResult();

		String md5 = FileMd5Util.getFileMd5(file.getInputStream(), 5);
		EntityFile entityFile = entityFileService.findFileByMD5(md5);

		if (entityFile == null) {
			entityFile = new EntityFile();
			UploadStreamResponse response = VodUploadUtil.uploadStream(ossProperties.getAccessKey(), ossProperties.getSecretKey(), file.getOriginalFilename(), file.getOriginalFilename(), file.getInputStream());
			GetPlayInfoResponse getPlayInfoResponse = new GetPlayInfoResponse();
			vodResult.setRequestId(response.getRequestId());
			if (response.isSuccess()) {
				entityFile.setFileName(file.getOriginalFilename());
				entityFile.setRealFileName(file.getOriginalFilename());
				entityFile.setMd5Code(md5);
				entityFile.setSuffix(FileUtil.getFileExtension(file.getOriginalFilename()));
				entityFile.setSize((double) file.getSize());
				entityFile.setUuid(response.getVideoId());
				entityFile.setModifyDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				entityFile.setCreateDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				entityFileService.save(entityFile);

				vodResult.setUuid(response.getVideoId());
			} else {
				vodResult.setErrorCode(response.getCode());
				vodResult.setErrorMessage(response.getMessage());
			}
		} else { //已存在，根据文件id查询url
			vodResult.setUuid(entityFile.getUuid());
		}

		return vodResult;
	}

	@Override
	public FileResult uploadOssMultipartFile(MultipartFile file) throws IOException {
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

		return fileResult;
	}

	@Override
	public FileResult findImageByUuid(String uuid) throws IOException {
		FileResult fileResult = new FileResult();

		EntityFile entityFile = entityFileService.findFileByUuid(uuid);

		if(entityFile != null){
			fileResult.setUuid(entityFile.getUuid());
			fileResult.setOriginalName(entityFile.getRealFileName());
			fileResult.setLink(ossProperties.getHttpPrefix().concat(StringPool.SLASH).concat(entityFile.getUrl()));
			fileResult.setThumbnailUrl(entityFile.getThumbnailUrl());
			fileResult.setName(entityFile.getUrl());
		}

		return fileResult;
	}

	@Override
	public GetVideoInfoResponse findVideoByUuid(String uuid) throws IOException {
		DefaultAcsClient client = initVodClient(ossProperties.getAccessKey(), ossProperties.getSecretKey());
		GetVideoInfoResponse response = new GetVideoInfoResponse();
		try {
			response = VodUploadUtil.getVideoInfo(client, uuid);
			System.out.print(JSON.toJSONString(response));
			System.out.print("Title = " + response.getVideo().getTitle() + "\n");
			System.out.print("Description = " + response.getVideo().getDescription() + "\n");
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		}

		return response;
	}

	@Override
	public String findAudioByUuid(String uuid) throws IOException {
		String playUrl = "";
		try {
			GetPlayInfoResponse response = VodUploadUtil.getPlayInfo(ossProperties.getAccessKey(), ossProperties.getSecretKey(), uuid);
			if (response != null) {
				List<GetPlayInfoResponse.PlayInfo> playInfos = response.getPlayInfoList();
				if (playInfos != null && !playInfos.isEmpty()) {
					playUrl = playInfos.get(0).getPlayURL();
				}
			}
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		}
		return playUrl;
	}
}

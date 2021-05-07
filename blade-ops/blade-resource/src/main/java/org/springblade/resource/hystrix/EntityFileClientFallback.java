package org.springblade.resource.hystrix;

import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.vo.FileResult;
import org.springblade.resource.vo.VodResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class EntityFileClientFallback implements EntityFileClient {


	@Override
	public Boolean add(EntityFile entityFile) {
		return false;
	}

	@Override
	public EntityFile findFileByMD5(String md5) {
		EntityFile entityFile = new EntityFile();
		entityFile.setUuid("boom!!!!");
		return entityFile;
	}

	@Override
	public EntityFile upload(File file) {
		return null;
	}

	@Override
	public EntityFile uploadImage(File file) throws IOException {
		return null;
	}

	@Override
	public VodResult uploadVodMultipartFile(MultipartFile file) throws IOException {
		return null;
	}

	@Override
	public FileResult uploadOssMultipartFile(MultipartFile file) throws IOException {
		return null;
	}

	@Override
	public FileResult findImageByUuid(String uuid) throws IOException {
		return null;
	}

	@Override
	public GetVideoInfoResponse findVideoByUuid(String uuid) throws IOException {
		return null;
	}

	@Override
	public String findAudioByUuid(String uuid) throws IOException {
		return null;
	}
}

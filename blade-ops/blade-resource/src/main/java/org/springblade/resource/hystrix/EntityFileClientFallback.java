package org.springblade.resource.hystrix;

import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.feign.EntityFileClient;
import org.springframework.stereotype.Component;

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
}

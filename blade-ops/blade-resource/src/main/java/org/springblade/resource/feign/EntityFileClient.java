/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.resource.feign;

import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.hystrix.EntityFileClientFallback;
import org.springblade.resource.vo.FileResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;

/**
 * Notice Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-resource"
		,
	//定义hystrix配置类
	fallback = EntityFileClientFallback.class
)
public interface EntityFileClient {

	/**
	 * 接口前缀
	 */
	String API_PREFIX = "/api/resource";

	/**
	 * 新增
	 * @param entityFile
	 * @return
	 */
	@PostMapping(API_PREFIX + "/add")
	Boolean add(@RequestBody EntityFile entityFile);

	/**
	 * 根据 med5 查询数据
	 * @param md5
	 * @return
	 */
	@PostMapping(API_PREFIX + "/findFileByMD5")
	EntityFile findFileByMD5(@RequestParam("md5") String md5);

	/**
	 *  上传视频资源
	 * @param file
	 * @return
	 */
	@PostMapping(API_PREFIX + "/upload")
	EntityFile upload(@RequestBody File file) throws IOException;

    /**
     *  上传图片资源
     * @param file
     * @return
     */
    @PostMapping(API_PREFIX + "/uploadImage")
	EntityFile uploadImage(@RequestBody File file) throws IOException;

	/**
	 *  查找图片资源
	 * @param uuid
	 * @return
	 */
	@PostMapping(API_PREFIX + "/findImageByUuid")
	FileResult findImageByUuid(@RequestParam("uuid") String uuid) throws IOException;

	/**
	 *  查找音频资源
	 * @param uuid
	 * @return
	 */
	@PostMapping(API_PREFIX + "/findAudioByUuid")
	String findAudioByUuid(@RequestParam("uuid") String uuid) throws IOException;
}

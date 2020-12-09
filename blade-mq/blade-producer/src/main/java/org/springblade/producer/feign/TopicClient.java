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
package org.springblade.producer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Notice Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-producer",
	//定义hystrix配置类
	fallback = TopicClientFallback.class
)
public interface TopicClient {

	/**
	 * 接口前缀
	 */
	String API_PREFIX = "/api/producer";

	/**
	 * 发送消息
	 * @return
	 */
	@PostMapping(API_PREFIX + "/sendMessage")
	void sendMessage(@RequestParam("message")String message, @RequestParam("delayTime")Long delayTime);
}

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
package cn.rzedu.sf.activity.feign;

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
        value = "sf-activity",
        //定义hystrix配置类
        fallback = ActivityClientFallback.class
)
public interface ActivityClient {

    /**
     * 接口前缀
     */
    String API_PREFIX = "/api/activity";

    /**
     * 获取token
     *
     * @return
     */
    @PostMapping(API_PREFIX + "/getAccessToken")
    String getAccessToken();

    /**
     * 发送活动信息 两条文案 + 海报
     *
     * @return
     */
    @PostMapping(API_PREFIX + "/sendMessage")
    void sendActivityMessage(@RequestParam("openId") String openId);

    /**
     * 发送活动海报
     *
     * @return
     */
    @PostMapping(API_PREFIX + "/sendActivityPoster")
    String sendActivityPoster(@RequestParam("openId") String openId, @RequestParam("type") Integer type);

    /**
     * 发送新助力活动文案
     * @return
     */
    @PostMapping(API_PREFIX + "/sendNewActivity")
    void sendNewActivity(@RequestParam("openId") String openId);

    /**
     * 发送新助力活动海报
     * @return
     */
    @PostMapping(API_PREFIX + "/sendNewActivityPoster")
    String sendNewActivityPoster(@RequestParam("openId") String openId, @RequestParam("type") Integer type);

    /**
     * 推送 图片 客服消息
     */
    @PostMapping(API_PREFIX + "/sendImageKFMessage")
    void sendImageKFMessage(@RequestParam("accessToken") String accessToken, @RequestParam("openId") String openId, @RequestParam("mediaId") String mediaId);
    
    @PostMapping(API_PREFIX + "/keyword")
     void keyword(@RequestParam("openId")String openId,@RequestParam("stringBuffer") StringBuffer stringBuffer) ;
    
    @PostMapping(API_PREFIX + "/groupSend")
    String groupSend(@RequestParam("openId")String openId,@RequestParam("name") String name,@RequestParam("icon") String icon) ;

    /**
     * 发送拼团消息
     * @param openId
     */
    @PostMapping(API_PREFIX + "/sendInitiateGrouponMessage")
    void sendInitiateGrouponMessage(@RequestParam("openId")String openId);
}

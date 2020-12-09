package cn.rzedu.sf.activity.feign;//package org.springblade.resource.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class ActivityClientFallback implements ActivityClient {

    @Override
    public String getAccessToken() {
        return "调用失败";
    }

    @Override
    public void sendActivityMessage(String openId) {

    }

    @Override
    public String sendActivityPoster(String openId, Integer type) {
        return "fail";
    }

    @Override
    public void sendNewActivity(String openId) {
    }

    @Override
    public String sendNewActivityPoster(String openId, Integer type) {
        return "fail";
    }

    @Override
    public void sendImageKFMessage(String accessToken, String openId, String mediaId) {

    }
    @Override
    public void keyword(String openId,StringBuffer stringBuffer){
    }

	@Override
	public String groupSend(String openId, String name,String icon) {
		 return "fail";
	}

    @Override
    public void sendInitiateGrouponMessage(String openId) {

    }
}

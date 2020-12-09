package org.springblade.producer.feign;//package org.springblade.resource.feign;

import org.springframework.stereotype.Component;


@Component
public class TopicClientFallback implements TopicClient {

    @Override
    public void sendMessage(String message, Long delayTime) {

    }
}

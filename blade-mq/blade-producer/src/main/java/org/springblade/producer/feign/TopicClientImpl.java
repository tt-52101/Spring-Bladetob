package org.springblade.producer.feign;

import lombok.AllArgsConstructor;
import org.springblade.producer.service.Topic;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
public class TopicClientImpl implements TopicClient {

	private Topic topic;

	@Override
	public void sendMessage(String message, Long delayTime) {
		System.out.println("message ------------------------------------ " + message);
		System.out.println("delayTime ------------------------------------ " + delayTime);
		System.out.println("开始时间 ------------------------------------ " + new Date());
		topic.producer().send(MessageBuilder.withPayload(message).setHeader("x-delay", delayTime).build());
	}
}

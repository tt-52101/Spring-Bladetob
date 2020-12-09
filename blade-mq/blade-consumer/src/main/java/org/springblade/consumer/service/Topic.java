package org.springblade.consumer.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义的消息通道
 */
public interface Topic {
    /**
     * 消息生产者的配置
     */
//    String PRODUCER = "producer-output";
//
//    @Output(PRODUCER)
//    MessageChannel producer();

    /**
     * 消息消费者的配置
     */
    String CONSUMER = "consumer-input";

    @Input(CONSUMER)
    SubscribableChannel consumer();
}


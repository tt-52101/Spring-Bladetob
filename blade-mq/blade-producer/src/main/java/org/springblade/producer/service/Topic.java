package org.springblade.producer.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Topic {
    /**
     * 消息生产者的配置
     */
    String PRODUCER = "producer-output";

    @Output(PRODUCER)
    MessageChannel producer();

//    /**
//     * 消息消费者的配置
//     */
//    String CONSUMER = "consumer-input";
//
//    @Input(CONSUMER)
//    SubscribableChannel consumer();
}

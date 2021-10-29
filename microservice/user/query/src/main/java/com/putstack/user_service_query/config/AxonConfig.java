package com.putstack.user_service_query.config;

import com.rabbitmq.client.Channel;

import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
@Log4j2
public class AxonConfig {

    private final static int SNAPSHOT_THRESHOLD = 5; 
    
    @Bean
    public SpringAMQPMessageSource amqpMessageSource(AMQPMessageConverter messageConverter) {
        return new SpringAMQPMessageSource(messageConverter) {

            @RabbitListener(queues = "myQueue")
            @Override
            public void onMessage(Message message, Channel channel) {
                System.out.println("amqp event " + message.toString() + " received");
                super.onMessage(message, channel);
            }
        };
    }

}

package com.pollsystem.simpleproject.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Bean
    public Queue pollWinnerQueue() {
        return new Queue("poll.winner.mail", true);
    }
}

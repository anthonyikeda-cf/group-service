package com.example.composertestapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexMessageConfig {

    @Bean("aAddQueue")
    public Queue groupIndexQueue() {
        return new Queue("indexing.group.add");
    }

    @Bean("aDeleteQueue")
    public Queue groupDeIndexQueue() {
        return new Queue("indexing.group.delete");
    }
}

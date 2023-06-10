package io.desofme.msfileprocessing.config;

import io.desofme.msfileprocessing.config.properties.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(properties.getExchange());
    }

    @Bean
    Queue queue() {
        return new Queue(properties.getQueue(), false);
    }

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(properties.getRoutingKey()).noargs();
    }

}
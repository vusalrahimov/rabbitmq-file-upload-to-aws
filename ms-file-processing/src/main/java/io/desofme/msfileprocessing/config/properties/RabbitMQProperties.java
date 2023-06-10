package io.desofme.msfileprocessing.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "amqp")
public class RabbitMQProperties {

    private String queue;
    private String exchange;
    private String routingKey;

}
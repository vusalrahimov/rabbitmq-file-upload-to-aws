package io.desofme.msfileprocessing;

import io.desofme.msfileprocessing.config.properties.AWSProperties;
import io.desofme.msfileprocessing.config.properties.RabbitMQProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RabbitMQProperties.class, AWSProperties.class})
public class MsFileProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFileProcessingApplication.class, args);
    }

}

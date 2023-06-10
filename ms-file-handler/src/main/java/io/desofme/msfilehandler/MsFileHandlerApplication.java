package io.desofme.msfilehandler;

import io.desofme.msfilehandler.config.properties.AWSProperties;
import io.desofme.msfilehandler.config.properties.RabbitMQProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RabbitMQProperties.class, AWSProperties.class})
public class MsFileHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFileHandlerApplication.class, args);
    }

}

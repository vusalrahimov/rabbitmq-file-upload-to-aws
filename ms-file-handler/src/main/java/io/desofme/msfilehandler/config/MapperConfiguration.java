package io.desofme.msfilehandler.config;

import io.desofme.msfilehandler.mapper.FileMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MapperConfiguration {

    @Bean
    FileMapper fileMapper() {
        return FileMapper.INSTANCE;
    }

}

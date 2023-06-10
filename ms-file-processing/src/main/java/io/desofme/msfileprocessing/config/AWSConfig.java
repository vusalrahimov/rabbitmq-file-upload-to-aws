package io.desofme.msfileprocessing.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.desofme.msfileprocessing.config.properties.AWSProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AWSConfig {

    private final AWSProperties properties;

    @Bean
    AmazonS3 amazonS3() {
        var awsCredentials = new BasicAWSCredentials(properties.getKeyId(), properties.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(properties.getRegion())
                .build();
    }

}

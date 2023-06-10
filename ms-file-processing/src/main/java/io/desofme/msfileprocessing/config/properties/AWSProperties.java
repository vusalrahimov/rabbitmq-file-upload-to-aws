package io.desofme.msfileprocessing.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws")
public class AWSProperties {

    private String keyId;
    private String secretKey;
    private String bucketName;
    private String region;
    private String folder;

}

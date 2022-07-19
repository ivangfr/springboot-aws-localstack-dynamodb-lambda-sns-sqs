package com.ivanfranchin.newsconsumer.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    @NotBlank
    private String region;

    @NotBlank
    private String accessKey;

    @NotBlank
    private String secretAccessKey;

    @NotBlank
    private String endpoint;

    @NotNull
    private SQS sqs;

    @Data
    @Valid
    public static class SQS {

        @NotBlank
        private String queueUrl;
    }
}

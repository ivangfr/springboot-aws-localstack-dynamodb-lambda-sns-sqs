package com.ivanfranchin.dynamodblambdafunction.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    @NotNull
    private SNS sns;

    @Data
    @Valid
    public static class SNS {

        @NotBlank
        private String destination;
    }
}

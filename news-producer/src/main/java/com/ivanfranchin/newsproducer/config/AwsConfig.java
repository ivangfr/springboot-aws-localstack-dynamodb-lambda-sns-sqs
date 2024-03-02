package com.ivanfranchin.newsproducer.config;

import com.ivanfranchin.newsproducer.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@RequiredArgsConstructor
@Configuration
public class AwsConfig {

    private final AwsProperties awsProperties;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(awsProperties.getRegion()))
                .endpointOverride(URI.create(awsProperties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsProperties.getAccessKey(), awsProperties.getSecretAccessKey())))
                .build();
    }
}

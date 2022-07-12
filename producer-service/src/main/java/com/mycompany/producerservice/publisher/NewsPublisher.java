package com.mycompany.producerservice.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.producerservice.event.News;
import com.mycompany.producerservice.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@RequiredArgsConstructor
@Component
public class NewsPublisher {

    private final SnsClient snsClient;
    private final AwsProperties awsProperties;
    private final ObjectMapper objectMapper;

    public void publish(News news) {
        snsClient.publish(
                PublishRequest.builder()
                        .topicArn(awsProperties.getSns().getTopicArn())
                        .message(toJson(news))
                        .build());
    }

    private String toJson(News news) {
        try {
            return objectMapper.writeValueAsString(news);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

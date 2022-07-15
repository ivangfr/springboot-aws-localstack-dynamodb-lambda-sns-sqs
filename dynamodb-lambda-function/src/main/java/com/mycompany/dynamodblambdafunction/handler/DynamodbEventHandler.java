package com.mycompany.dynamodblambdafunction.handler;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.dynamodblambdafunction.model.News;
import com.mycompany.dynamodblambdafunction.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class DynamodbEventHandler implements Consumer<DynamodbEvent> {

    private final SnsClient snsClient;
    private final AwsProperties awsProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void accept(DynamodbEvent dynamodbEvent) {
        dynamodbEvent.getRecords()
                .stream()
                .filter(record -> "INSERT".equals(record.getEventName()))
                .map(record ->
                        News.of(
                                record.getDynamodb().getNewImage().get("Id").getS(),
                                record.getDynamodb().getNewImage().get("Title").getS(),
                                record.getDynamodb().getNewImage().get("PublishedAt").getS()))
                .forEach(news ->
                        snsClient.publish(
                                PublishRequest.builder()
                                        .topicArn(awsProperties.getSns().getTopicArn())
                                        .message(toJson(news))
                                        .build()));
    }

    private String toJson(News news) {
        try {
            return objectMapper.writeValueAsString(news);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

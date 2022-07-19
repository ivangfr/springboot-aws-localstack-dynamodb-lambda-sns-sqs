package com.ivanfranchin.dynamodblambdafunction.handler;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanfranchin.dynamodblambdafunction.event.NewsEvent;
import com.ivanfranchin.dynamodblambdafunction.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Map;
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
                .map(record -> {
                    Map<String, AttributeValue> image = record.getDynamodb().getNewImage();
                    if (image == null) {
                        image = record.getDynamodb().getOldImage();
                    }
                    return NewsEvent.of(
                            record.getEventName(),
                            NewsEvent.News.of(
                                    image.get("Id").getS(),
                                    image.get("Title").getS(),
                                    image.get("PublishedAt").getS()
                            )
                    );
                })
                .forEach(newsEvent ->
                        snsClient.publish(
                                PublishRequest.builder()
                                        .topicArn(awsProperties.getSns().getTopicArn())
                                        .message(toJson(newsEvent))
                                        .build()
                        )
                );
    }

    private String toJson(NewsEvent newsEvent) {
        try {
            return objectMapper.writeValueAsString(newsEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

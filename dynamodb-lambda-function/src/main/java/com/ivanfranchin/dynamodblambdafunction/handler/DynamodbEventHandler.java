package com.ivanfranchin.dynamodblambdafunction.handler;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.ivanfranchin.dynamodblambdafunction.event.NewsEvent;
import com.ivanfranchin.dynamodblambdafunction.publisher.NewsEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class DynamodbEventHandler implements Consumer<DynamodbEvent> {

    private final NewsEventPublisher newsEventPublisher;

    @Override
    public void accept(DynamodbEvent dynamodbEvent) {
        dynamodbEvent.getRecords()
                .stream()
                .map(this::toNewsEvent)
                .forEach(newsEventPublisher::publish);
    }

    private NewsEvent toNewsEvent(DynamodbEvent.DynamodbStreamRecord record) {
        Map<String, AttributeValue> image = record.getDynamodb().getNewImage();
        if (image == null) {
            image = record.getDynamodb().getOldImage();
        }
        return new NewsEvent(
                record.getEventName(),
                new NewsEvent.News(
                        image.get("id").getS(),
                        image.get("title").getS(),
                        image.get("publishedAt").getS()
                )
        );
    }
}

package com.ivanfranchin.dynamodblambdafunction.publisher;

import com.ivanfranchin.dynamodblambdafunction.event.NewsEvent;
import com.ivanfranchin.dynamodblambdafunction.properties.AwsProperties;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewsEventPublisher {

    private final SnsTemplate snsTemplate;
    private final AwsProperties awsProperties;

    public void publish(NewsEvent newsEvent) {
        snsTemplate.convertAndSend(awsProperties.getSns().getDestination(), newsEvent);
    }
}

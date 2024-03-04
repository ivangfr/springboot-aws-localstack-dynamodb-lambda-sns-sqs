package com.ivanfranchin.newsconsumer.listener;

import com.ivanfranchin.newsconsumer.event.NewsEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewsEventListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @SqsListener("${aws.sqs.destination}")
    public void sqsListener(NewsEvent newsEvent) {
        log.info("Received newsEvent: {}", newsEvent);
        simpMessagingTemplate.convertAndSend("/topic/news", newsEvent);
    }
}

package com.ivanfranchin.consumerservice.consumer;

import com.ivanfranchin.consumerservice.mapper.NewsMapper;
import com.ivanfranchin.consumerservice.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewsConsumer {

    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NewsMapper newsMapper;


    @Scheduled(fixedRate = 5000)
    public void scheduledConsumer() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .build();

        sqsClient.receiveMessage(receiveMessageRequest)
                .messages()
                .forEach(message -> {
                    log.info("Received message: {}", message);

                    simpMessagingTemplate.convertAndSend("/topic/news", newsMapper.toNewsEvent(message));

                    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(awsProperties.getSqs().getQueueUrl())
                            .receiptHandle(message.receiptHandle())
                            .build();

                    sqsClient.deleteMessage(deleteMessageRequest);
                });
    }
}

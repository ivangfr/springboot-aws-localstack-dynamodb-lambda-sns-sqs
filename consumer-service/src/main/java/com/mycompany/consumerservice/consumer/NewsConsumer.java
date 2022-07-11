package com.mycompany.consumerservice.consumer;

import com.mycompany.consumerservice.mapper.NewsMapper;
import com.mycompany.consumerservice.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewsConsumer {

    private final SqsAsyncClient sqsAsyncClient;
    private final AwsProperties awsProperties;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NewsMapper newsMapper;


    @Scheduled(fixedRate = 5000)
    public void scheduledConsumer() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .build();

        sqsAsyncClient.receiveMessage(receiveMessageRequest)
                .join()
                .messages()
                .forEach(message -> {
                    log.info("Received message: {}", message);

                    simpMessagingTemplate.convertAndSend("/topic/news", newsMapper.toNews(message));

                    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(awsProperties.getSqs().getQueueUrl())
                            .receiptHandle(message.receiptHandle())
                            .build();

                    sqsAsyncClient.deleteMessage(deleteMessageRequest);
                });
    }
}

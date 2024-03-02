package com.ivanfranchin.newsconsumer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanfranchin.newsconsumer.event.NewsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

@RequiredArgsConstructor
@Component
public class NewsMapper {

    private final ObjectMapper objectMapper;

    public NewsEvent toNewsEvent(Message message) {
        try {
            MessageBody messageBody = objectMapper.readValue(message.body(), MessageBody.class);
            return objectMapper.readValue(messageBody.message(), NewsEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private record MessageBody(@JsonProperty("Message") String message) {
    }
}

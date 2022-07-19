package com.ivanfranchin.newsconsumer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanfranchin.newsconsumer.domain.NewsEvent;
import lombok.Data;
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
            return objectMapper.readValue(messageBody.getMessage(), NewsEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    private static class MessageBody {

        @JsonProperty("Message")
        private String message;
    }
}

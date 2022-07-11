package com.mycompany.consumerservice.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.consumerservice.domain.News;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

@RequiredArgsConstructor
@Component
public class NewsMapper {

    private final ObjectMapper objectMapper;

    public News toNews(Message message) {
        try {
            MessageBody messageBody = objectMapper.readValue(message.body(), MessageBody.class);
            return objectMapper.readValue(messageBody.getMessage(), News.class);
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

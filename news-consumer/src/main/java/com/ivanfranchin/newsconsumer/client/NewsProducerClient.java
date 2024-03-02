package com.ivanfranchin.newsconsumer.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewsProducerClient {

    private final RestTemplate restTemplate;

    @Value("${news-producer.url}")
    private String newsProducerUrl;

    public List<News> getNews() {
        ParameterizedTypeReference<List<News>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        try {
            ResponseEntity<List<News>> responseEntity = restTemplate.exchange(
                    newsProducerUrl + "/api/news", HttpMethod.GET, null, parameterizedTypeReference);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("An exception happened while calling news-producer. Error message: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}

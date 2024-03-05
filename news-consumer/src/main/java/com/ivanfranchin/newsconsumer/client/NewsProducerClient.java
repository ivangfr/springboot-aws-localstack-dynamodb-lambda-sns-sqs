package com.ivanfranchin.newsconsumer.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/api/news")
public interface NewsProducerClient {

    @GetExchange
    List<NewsResponse> getNews();
}

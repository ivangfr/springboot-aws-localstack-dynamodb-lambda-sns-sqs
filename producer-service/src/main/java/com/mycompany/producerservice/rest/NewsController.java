package com.mycompany.producerservice.rest;

import com.mycompany.producerservice.event.News;
import com.mycompany.producerservice.publisher.NewsPublisher;
import com.mycompany.producerservice.rest.dto.CreateNewsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsPublisher newsPublisher;

    @PostMapping
    public News publishNews(@Valid @RequestBody CreateNewsRequest request) {
        News news = new News(UUID.randomUUID().toString(), request.getTitle());
        newsPublisher.publish(news);
        return news;
    }
}

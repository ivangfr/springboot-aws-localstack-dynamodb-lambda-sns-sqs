package com.mycompany.producerservice.rest;

import com.mycompany.producerservice.event.News;
import com.mycompany.producerservice.publisher.NewsPublisher;
import com.mycompany.producerservice.rest.dto.CreateNewsRequest;
import com.mycompany.producerservice.service.RandomNewsGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsPublisher newsPublisher;
    private final RandomNewsGenerator randomNewsGenerator;

    @PostMapping
    public News publishNews(@Valid @RequestBody CreateNewsRequest request) {
        News news = new News(UUID.randomUUID().toString(), request.getTitle(), ZonedDateTime.now());
        newsPublisher.publish(news);
        return news;
    }

    @PostMapping("/randomly")
    public News publishRandomNews() {
        News news = randomNewsGenerator.getRandomly();
        newsPublisher.publish(news);
        return news;
    }
}

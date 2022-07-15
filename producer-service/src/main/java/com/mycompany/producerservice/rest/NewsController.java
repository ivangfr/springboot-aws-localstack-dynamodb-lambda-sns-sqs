package com.mycompany.producerservice.rest;

import com.mycompany.producerservice.event.News;
import com.mycompany.producerservice.rest.dto.CreateNewsRequest;
import com.mycompany.producerservice.service.NewsService;
import com.mycompany.producerservice.service.RandomNewsGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final RandomNewsGenerator randomNewsGenerator;

    @PostMapping
    public News createNews(@Valid @RequestBody CreateNewsRequest request) {
        return newsService.saveNews(News.of(UUID.randomUUID().toString(), request.getTitle(), ZonedDateTime.now()));
    }

    @PostMapping("/randomly")
    public News createNewsRandomly() {
        return newsService.saveNews(randomNewsGenerator.getRandomly());
    }

    @GetMapping
    public List<News> getNews() {
        return newsService.getNews();
    }
}

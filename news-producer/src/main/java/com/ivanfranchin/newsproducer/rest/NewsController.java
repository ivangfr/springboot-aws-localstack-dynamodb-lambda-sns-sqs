package com.ivanfranchin.newsproducer.rest;

import com.ivanfranchin.newsproducer.event.News;
import com.ivanfranchin.newsproducer.rest.dto.CreateNewsRequest;
import com.ivanfranchin.newsproducer.service.NewsService;
import com.ivanfranchin.newsproducer.service.RandomNewsGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public News createNews(@Valid @RequestBody CreateNewsRequest request) {
        return newsService.saveNews(
                News.of(UUID.randomUUID().toString(), request.getTitle(), ZonedDateTime.now()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/randomly")
    public News createNewsRandomly() {
        return newsService.saveNews(randomNewsGenerator.getRandomly());
    }

    @GetMapping("/{id}")
    public News getNew(@PathVariable String id) {
        return newsService.validateAndGetNew(id);
    }

    @GetMapping
    public List<News> getNews() {
        return newsService.getNews();
    }

    @DeleteMapping("/{id}")
    public News deleteNews(@PathVariable String id) {
        News news = newsService.validateAndGetNew(id);
        newsService.deleteNews(id);
        return news;
    }
}

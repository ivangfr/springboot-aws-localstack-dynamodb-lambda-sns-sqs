package com.ivanfranchin.newsproducer.rest;

import com.ivanfranchin.newsproducer.model.News;
import com.ivanfranchin.newsproducer.rest.dto.CreateNewsRequest;
import com.ivanfranchin.newsproducer.service.NewsService;
import com.ivanfranchin.newsproducer.service.RandomNewsGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final RandomNewsGenerator randomNewsGenerator;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public News createNews(@Valid @RequestBody CreateNewsRequest request) {
        log.info("Create news {}", request);
        return newsService.saveNews(new News(request.getTitle()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/randomly")
    public News createNewsRandomly() {
        log.info("Create news randomly");
        return newsService.saveNews(randomNewsGenerator.getRandomly());
    }

    @GetMapping("/{id}")
    public News getNews(@PathVariable String id) {
        log.info("Get news with id {}", id);
        return newsService.validateAndGetNews(id);
    }

    @GetMapping
    public List<News> getNews() {
        return newsService.getNews();
    }

    @DeleteMapping("/{id}")
    public News deleteNews(@PathVariable String id) {
        log.info("Delete news with id {}", id);
        News news = newsService.validateAndGetNews(id);
        newsService.deleteNews(id);
        return news;
    }
}

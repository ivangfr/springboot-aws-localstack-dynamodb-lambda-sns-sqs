package com.ivanfranchin.newsproducer.service;

import com.ivanfranchin.newsproducer.exception.NewsNotFoundException;
import com.ivanfranchin.newsproducer.model.News;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final DynamoDbTemplate dynamoDbTemplate;

    @Override
    public News saveNews(News news) {
        return dynamoDbTemplate.save(news);
    }

    @Override
    public List<News> getNews() {
        PageIterable<News> news = dynamoDbTemplate.scanAll(News.class);
        return StreamSupport.stream(news.spliterator(), false)
                .flatMap(page -> page.items().stream())
                .sorted((n1, n2) -> n2.getPublishedAt().compareTo(n1.getPublishedAt()))
                .toList();
    }

    @Override
    public News validateAndGetNews(String id) {
        Key key = Key.builder().partitionValue(id).build();
        News news = dynamoDbTemplate.load(key, News.class);
        if (news == null) {
            throw new NewsNotFoundException("News with id %s not found".formatted(id));
        }
        return news;
    }

    @Override
    public void deleteNews(String id) {
        News news = validateAndGetNews(id);
        dynamoDbTemplate.delete(news);
    }
}

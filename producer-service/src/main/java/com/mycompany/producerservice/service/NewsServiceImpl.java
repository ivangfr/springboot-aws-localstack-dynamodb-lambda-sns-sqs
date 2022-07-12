package com.mycompany.producerservice.service;

import com.mycompany.producerservice.event.News;
import com.mycompany.producerservice.publisher.NewsPublisher;
import com.mycompany.producerservice.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsPublisher newsPublisher;
    private final NewsRepository newsRepository;

    @Override
    public News saveNews(News news) {
        News savedNews = newsRepository.save(news);
        newsPublisher.publish(savedNews);
        return savedNews;
    }

    @Override
    public List<News> getNews() {
        return newsRepository.findAll();
    }
}

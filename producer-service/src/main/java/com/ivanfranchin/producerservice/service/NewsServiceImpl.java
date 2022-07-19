package com.ivanfranchin.producerservice.service;

import com.ivanfranchin.producerservice.event.News;
import com.ivanfranchin.producerservice.exception.NewsNotFoundException;
import com.ivanfranchin.producerservice.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public List<News> getNews() {
        return newsRepository.findAll();
    }

    @Override
    public News validateAndGetNew(String id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("News with id '%s' not found", id)));
    }

    @Override
    public void deleteNews(String id) {
        newsRepository.delete(id);
    }
}

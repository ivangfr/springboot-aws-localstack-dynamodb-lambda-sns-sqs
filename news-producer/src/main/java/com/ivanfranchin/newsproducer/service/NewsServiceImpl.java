package com.ivanfranchin.newsproducer.service;

import com.ivanfranchin.newsproducer.event.News;
import com.ivanfranchin.newsproducer.exception.NewsNotFoundException;
import com.ivanfranchin.newsproducer.repository.NewsRepository;
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

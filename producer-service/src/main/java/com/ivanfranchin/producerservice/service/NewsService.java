package com.ivanfranchin.producerservice.service;

import com.ivanfranchin.producerservice.event.News;

import java.util.List;

public interface NewsService {

    News saveNews(News news);

    List<News> getNews();

    News validateAndGetNew(String id);

    void deleteNews(String id);
}

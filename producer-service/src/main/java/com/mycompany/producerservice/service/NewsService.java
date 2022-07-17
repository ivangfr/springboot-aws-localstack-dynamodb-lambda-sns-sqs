package com.mycompany.producerservice.service;

import com.mycompany.producerservice.event.News;

import java.util.List;

public interface NewsService {

    News saveNews(News news);

    List<News> getNews();

    News validateAndGetNew(String id);

    void deleteNews(String id);
}

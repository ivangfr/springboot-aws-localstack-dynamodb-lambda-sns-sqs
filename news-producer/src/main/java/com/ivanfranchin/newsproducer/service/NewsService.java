package com.ivanfranchin.newsproducer.service;

import com.ivanfranchin.newsproducer.event.News;

import java.util.List;

public interface NewsService {

    News saveNews(News news);

    List<News> getNews();

    News validateAndGetNew(String id);

    void deleteNews(String id);
}

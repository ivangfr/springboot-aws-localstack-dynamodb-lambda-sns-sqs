package com.ivanfranchin.newsproducer.service;

import com.ivanfranchin.newsproducer.model.News;

import java.util.List;

public interface NewsService {

    News saveNews(News news);

    List<News> getNews();

    News validateAndGetNews(String id);

    void deleteNews(String id);
}

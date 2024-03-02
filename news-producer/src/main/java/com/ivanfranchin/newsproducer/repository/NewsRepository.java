package com.ivanfranchin.newsproducer.repository;

import com.ivanfranchin.newsproducer.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsRepository {

    News save(News news);

    List<News> findAll();

    Optional<News> findById(String id);

    void delete(String id);
}

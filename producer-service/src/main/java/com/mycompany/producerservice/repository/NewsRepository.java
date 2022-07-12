package com.mycompany.producerservice.repository;

import com.mycompany.producerservice.event.News;

import java.util.List;

public interface NewsRepository {

    News save(News news);

    List<News> findAll();
}

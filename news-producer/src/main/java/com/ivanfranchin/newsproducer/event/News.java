package com.ivanfranchin.newsproducer.event;

import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class News {

    String id;
    String title;
    ZonedDateTime publishedAt;
}

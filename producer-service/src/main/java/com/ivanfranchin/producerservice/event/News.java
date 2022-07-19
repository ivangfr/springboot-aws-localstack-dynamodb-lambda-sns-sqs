package com.ivanfranchin.producerservice.event;

import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class News {

    String id;
    String title;
    ZonedDateTime publishedAt;
}

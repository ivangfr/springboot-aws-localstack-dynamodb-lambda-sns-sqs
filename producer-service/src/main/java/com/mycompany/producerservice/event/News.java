package com.mycompany.producerservice.event;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class News {

    String id;
    String title;
    ZonedDateTime publishedAt;
}

package com.mycompany.consumerservice.domain;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class News {

    private String id;
    private String title;
    private ZonedDateTime publishedAt;
}

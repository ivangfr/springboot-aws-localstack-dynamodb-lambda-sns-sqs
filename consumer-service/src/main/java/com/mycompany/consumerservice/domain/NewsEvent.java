package com.mycompany.consumerservice.domain;

import lombok.Data;

@Data
public class NewsEvent {

    private String action;
    private News news;
}

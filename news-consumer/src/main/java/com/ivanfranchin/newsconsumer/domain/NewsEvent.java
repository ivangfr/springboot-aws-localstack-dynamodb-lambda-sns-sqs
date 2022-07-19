package com.ivanfranchin.newsconsumer.domain;

import lombok.Data;

@Data
public class NewsEvent {

    private String action;
    private News news;
}

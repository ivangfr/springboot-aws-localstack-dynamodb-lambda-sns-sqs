package com.mycompany.dynamodblambdafunction.event;

import lombok.Value;

@Value(staticConstructor = "of")
public class NewsEvent {

    String action;
    News news;

    @Value(staticConstructor = "of")
    public static class News {

        String id;
        String title;
        String publishedAt;
    }
}

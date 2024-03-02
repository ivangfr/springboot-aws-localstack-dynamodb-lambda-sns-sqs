package com.ivanfranchin.newsconsumer.event;

import java.util.Date;

public record NewsEvent(String action, News news) {
    public record News(String id, String title, Date publishedAt) {
    }
}

package com.ivanfranchin.newsproducer.event;

import java.time.ZonedDateTime;

public record News(String id, String title, ZonedDateTime publishedAt) {
}

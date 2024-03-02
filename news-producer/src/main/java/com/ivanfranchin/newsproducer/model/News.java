package com.ivanfranchin.newsproducer.model;

import java.time.ZonedDateTime;

public record News(String id, String title, ZonedDateTime publishedAt) {
}

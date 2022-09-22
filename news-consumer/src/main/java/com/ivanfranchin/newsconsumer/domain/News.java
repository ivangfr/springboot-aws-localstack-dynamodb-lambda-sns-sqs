package com.ivanfranchin.newsconsumer.domain;

import java.util.Date;

public record News(String id, String title, Date publishedAt) {
}

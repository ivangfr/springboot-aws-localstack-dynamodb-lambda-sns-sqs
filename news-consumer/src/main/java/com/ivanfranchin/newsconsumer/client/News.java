package com.ivanfranchin.newsconsumer.client;

import java.util.Date;

public record News(String id, String title, Date publishedAt) {
}

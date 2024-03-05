package com.ivanfranchin.newsconsumer.client;

import java.util.Date;

public record NewsResponse(String id, String title, Date publishedAt) {
}

package com.ivanfranchin.dynamodblambdafunction.event;

public record NewsEvent(String action, News news) {
    public record News(String id, String title, String publishedAt) {
    }
}

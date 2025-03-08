package com.ivanfranchin.dynamodblambdafunction.news;

public record NewsEvent(String action, News news) {
    public record News(String id, String title, String publishedAt) {
    }
}

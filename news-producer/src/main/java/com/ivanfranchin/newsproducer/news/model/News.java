package com.ivanfranchin.newsproducer.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@DynamoDbBean
public class News {
    private String id;
    private String title;
    private Instant publishedAt;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public News(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.publishedAt = Instant.now();
    }
}

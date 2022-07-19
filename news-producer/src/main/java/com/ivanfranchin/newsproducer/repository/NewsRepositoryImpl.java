package com.ivanfranchin.newsproducer.repository;

import com.ivanfranchin.newsproducer.event.News;
import com.ivanfranchin.newsproducer.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class NewsRepositoryImpl implements NewsRepository {

    private final DynamoDbClient dynamoDbClient;
    private final AwsProperties awsProperties;

    @Override
    public News save(News news) {
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(awsProperties.getDynamoDB().getTableName())
                .item(toMap(news))
                .build();

        try {
            PutItemResponse putItemResponse = dynamoDbClient.putItem(putItemRequest);
            log.info("Record inserted successfully! The response from DynamoDB is: {}", putItemResponse);
            return news;
        } catch (Exception e) {
            log.error("An error occurred! Error message: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<News> findAll() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(awsProperties.getDynamoDB().getTableName())
                .build();

        try {
            ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
            return scanResponse.items()
                    .stream()
                    .map(this::toNews)
                    .sorted((n1, n2) -> n2.getPublishedAt().compareTo(n1.getPublishedAt()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("An error occurred! Error message: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<News> findById(String id) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(awsProperties.getDynamoDB().getTableName())
                .key(Map.of("Id", AttributeValue.builder().s(id).build()))
                .build();

        try {
            Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();
            log.info("item = {}", item);
            return item == null || item.isEmpty() ? Optional.empty() : Optional.of(toNews(item));
        } catch (Exception e) {
            log.error("An error occurred! Error message: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName(awsProperties.getDynamoDB().getTableName())
                .key(Map.of("Id", AttributeValue.builder().s(id).build()))
                .build();

        try {
            DeleteItemResponse deleteItemResponse = dynamoDbClient.deleteItem(deleteItemRequest);
            log.info("Record deleted successfully! The response from DynamoDB is: {}", deleteItemResponse);
        } catch (DynamoDbException e) {
            log.error("An error occurred! Error message: {}", e.getMessage());
            throw e;
        }
    }

    private Map<String, AttributeValue> toMap(News news) {
        return Map.of(
                "Id", AttributeValue.builder().s(news.getId()).build(),
                "Title", AttributeValue.builder().s(news.getTitle()).build(),
                "PublishedAt", AttributeValue.builder().s(news.getPublishedAt().format(DTF)).build());
    }

    private News toNews(Map<String, AttributeValue> map) {
        return News.of(map.get("Id").s(), map.get("Title").s(), ZonedDateTime.parse(map.get("PublishedAt").s(), DTF));
    }

    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
}

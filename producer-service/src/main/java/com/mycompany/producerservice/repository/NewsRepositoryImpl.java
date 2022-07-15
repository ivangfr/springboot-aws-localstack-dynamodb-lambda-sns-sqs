package com.mycompany.producerservice.repository;

import com.mycompany.producerservice.event.News;
import com.mycompany.producerservice.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class NewsRepositoryImpl implements NewsRepository {

    private final DynamoDbClient dynamoDbClient;
    private final AwsProperties awsProperties;

    @Override
    public News save(News news) {
        String tableName = awsProperties.getDynamoDB().getTableName();
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(toMap(news))
                .build();

        try {
            PutItemResponse putItemResponse = dynamoDbClient.putItem(request);
            log.info("Record inserted successfully! The response from DynamoDB is: {}", putItemResponse);
            return news;
        } catch (ResourceNotFoundException e) {
            log.error("The Amazon DynamoDB table \"{}\" can't be found.", tableName);
            throw e;
        } catch (DynamoDbException e) {
            log.error("An error occurred! Error message: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<News> findAll() {
        String tableName = awsProperties.getDynamoDB().getTableName();
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        try {
            ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
            return scanResponse.items()
                    .stream()
                    .map(this::toNews)
                    .sorted((n1, n2) -> n2.getPublishedAt().compareTo(n1.getPublishedAt()))
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            log.error("The Amazon DynamoDB table \"{}\" can't be found.", tableName);
            throw e;
        } catch (DynamoDbException e) {
            log.error("An error occurred! Error message: {}", e.getMessage());
            throw e;
        }
    }

    private Map<String, AttributeValue> toMap(News news) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("Id", AttributeValue.builder().s(news.getId()).build());
        map.put("Title", AttributeValue.builder().s(news.getTitle()).build());
        map.put("PublishedAt", AttributeValue.builder().s(news.getPublishedAt().format(DTF)).build());
        return map;
    }

    private News toNews(Map<String, AttributeValue> map) {
        return News.of(map.get("Id").s(), map.get("Title").s(), ZonedDateTime.parse(map.get("PublishedAt").s(), DTF));
    }

    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
}

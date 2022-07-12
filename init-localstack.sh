#!/usr/bin/env bash

echo
echo "Creating news-topic in SNS"
echo "--------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns create-topic --name news-topic

echo
echo "Creating news-consumer-service-queue in SQS"
echo "-------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name news-consumer-service-queue

echo
echo "Subscribing news-consumer-service-queue to news-topic"
echo "-----------------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns subscribe \
  --topic-arn arn:aws:sns:eu-west-1:000000000000:news-topic \
  --protocol sqs \
  --notification-endpoint arn:aws:sqs:eu-west-1:000000000000:news-consumer-service-queue

echo
echo "Verifying SNS subscriptions and SQS queues"
echo "------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns list-subscriptions
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sqs list-queues

echo
echo "Create News table in DynamoDB"
echo "-----------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name News \
  --attribute-definitions AttributeName=Id,AttributeType=S AttributeName=Title,AttributeType=S \
  --key-schema AttributeName=Id,KeyType=HASH AttributeName=Title,KeyType=RANGE \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

echo
echo "Verifying tables in DynamoDB"
echo "----------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb list-tables

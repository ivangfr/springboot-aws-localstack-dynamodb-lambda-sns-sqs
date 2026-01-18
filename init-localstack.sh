#!/usr/bin/env bash

if ! [[ $(docker ps -q -f name=localstack) ]]; then
  echo "WARNING: The localstack Docker container is not running. Please, start it first."
  exit 1
fi

echo
echo "Initializing LocalStack"
echo "======================="

echo
echo "Installing jq"
echo "-------------"
docker exec -t localstack apt-get -y install jq

echo
echo "Creating news-topic in SNS"
echo "--------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns create-topic --name news-topic

echo
echo "Creating news-consumer-queue in SQS"
echo "-----------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name news-consumer-queue

echo
echo "Subscribing news-consumer-queue to news-topic"
echo "---------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns subscribe \
  --topic-arn arn:aws:sns:eu-west-1:000000000000:news-topic \
  --protocol sqs \
  --attributes '{"RawMessageDelivery":"true"}' \
  --notification-endpoint arn:aws:sqs:eu-west-1:000000000000:news-consumer-queue

echo
echo "Creating news table in DynamoDB"
echo "-------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name news \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
  --stream-specification StreamEnabled=true,StreamViewType=NEW_AND_OLD_IMAGES

echo
echo "Getting news table DynamoDB Stream ARN"
echo "--------------------------------------"
NEWS_TABLE_DYNAMODB_STREAM_ARN=$(docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb describe-table --table-name news | jq -r '.Table.LatestStreamArn')
echo "NEWS_TABLE_DYNAMODB_STREAM_ARN=${NEWS_TABLE_DYNAMODB_STREAM_ARN}"

echo
echo "Creating Lambda Function called ProcessDynamoDBEvent"
echo "----------------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda create-function \
  --function-name ProcessDynamoDBEvent \
  --runtime java25 \
  --memory-size 512 \
  --handler org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest \
  --zip-file fileb:///shared/dynamodb-lambda-function-java25-aws.jar \
  --environment "Variables={AWS_REGION=eu-west-1,AWS_ACCESS_KEY_ID=key,AWS_SECRET_ACCESS_KEY=secret}" \
  --role arn:aws:iam::000000000000:role/service-role/irrelevant \
  --timeout 60

echo
echo "Creating a mapping between news table DynamoDB event source and ProcessDynamoDBEvent lambda function"
echo "----------------------------------------------------------------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda create-event-source-mapping \
  --function-name ProcessDynamoDBEvent \
  --event-source $NEWS_TABLE_DYNAMODB_STREAM_ARN \
  --starting-position LATEST

echo
echo "LocalStack initialized successfully"
echo "==================================="
echo
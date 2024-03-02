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
  --notification-endpoint arn:aws:sqs:eu-west-1:000000000000:news-consumer-queue

echo
echo "Creating News table in DynamoDB"
echo "-------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name News \
  --attribute-definitions AttributeName=Id,AttributeType=S \
  --key-schema AttributeName=Id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
  --stream-specification StreamEnabled=true,StreamViewType=NEW_AND_OLD_IMAGES

echo
echo "Getting News table DynamoDB Stream ARN"
echo "--------------------------------------"
NEWS_TABLE_DYNAMODB_STREAM_ARN=$(docker exec -t localstack aws --endpoint-url=http://localhost:4566 dynamodb describe-table --table-name News | jq -r '.Table.LatestStreamArn')
echo "NEWS_TABLE_DYNAMODB_STREAM_ARN=${NEWS_TABLE_DYNAMODB_STREAM_ARN}"

echo
echo "Creating Lambda Function called ProcessDynamoDBEvent"
echo "----------------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda create-function \
  --function-name ProcessDynamoDBEvent \
  --runtime java17 \
  --memory-size 512 \
  --handler org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest \
  --zip-file fileb:///dynamodb-lambda-function/shared/dynamodb-lambda-function-java17-aws.jar \
  --environment "Variables={AWS_ACCESS_KEY=key,AWS_SECRET_ACCESS_KEY=secret}" \
  --role arn:aws:iam::000000000000:role/service-role/irrelevant \
  --timeout 60

echo
echo "Creating a mapping between News Table DynamoDB event source and ProcessDynamoDBEvent lambda function"
echo "----------------------------------------------------------------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda create-event-source-mapping \
  --function-name ProcessDynamoDBEvent \
  --event-source $NEWS_TABLE_DYNAMODB_STREAM_ARN \
  --starting-position LATEST

echo
echo "Waiting for ProcessDynamoDBEvent lambda function to change the state from Pending to Active"
echo "-------------------------------------------------------------------------------------------"
TIMEOUT=$((7 * 60))  # set timeout to 7 minutes
WAIT_INTERVAL=1
for ((i=0; i<TIMEOUT; i+=WAIT_INTERVAL)); do
  VAR=$(docker exec -t localstack aws --endpoint-url=http://localhost:4566 lambda get-function --function-name ProcessDynamoDBEvent --query 'Configuration.[State, LastUpdateStatus]' | jq -r ".[0]")
  if [ "$VAR" = "Active" ] ; then
    echo
    echo "ProcessDynamoDBEvent lambda function is Active!"
    break
  fi
  if [ $i -ge $TIMEOUT ] ; then
    echo
    echo "Waiting for ProcessDynamoDBEvent lambda function to become Active... TIMEOUT"
    break
  fi
  printf "."
  sleep $WAIT_INTERVAL
done

echo
echo "LocalStack initialized successfully"
echo "==================================="
echo
#!/usr/bin/env bash

echo
echo "Packaging jar file"
echo "------------------"
./mvnw clean package --projects dynamodb-lambda-function -DskipTests

echo
echo "Copying to tmp/localstack folder"
echo "--------------------------------"
mkdir -p dynamodb-lambda-function/shared
cp dynamodb-lambda-function/target/dynamodb-lambda-function-1.0.0-aws.jar dynamodb-lambda-function/shared

echo "Done!"
echo
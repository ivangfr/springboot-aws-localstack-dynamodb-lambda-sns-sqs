#!/usr/bin/env bash

echo
echo "Packaging jar file"
echo "------------------"
./mvnw clean package --projects dynamodb-lambda-function -DskipTests

echo
echo "Copying to dynamodb-lambda-function/shared folder"
echo "-------------------------------------------------"
mkdir -p dynamodb-lambda-function/shared
cp dynamodb-lambda-function/target/dynamodb-lambda-function-java21-aws.jar dynamodb-lambda-function/shared

echo "Done!"
echo
#!/usr/bin/env bash

DOCKER_IMAGE_PREFIX="ivanfranchin"
APP_VERSION="1.0.0"

NEWS_PRODUCER_APP_NAME="news-producer"
NEWS_CONSUMER_APP_NAME="news-consumer"
NEWS_PRODUCER_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${NEWS_PRODUCER_APP_NAME}:${APP_VERSION}"
NEWS_CONSUMER_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${NEWS_CONSUMER_APP_NAME}:${APP_VERSION}"

SKIP_TESTS="true"

./mvnw clean spring-boot:build-image \
  --projects "$NEWS_PRODUCER_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dspring-boot.build-image.imageName="$NEWS_PRODUCER_DOCKER_IMAGE_NAME"

./mvnw clean spring-boot:build-image \
  --projects "$NEWS_CONSUMER_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dspring-boot.build-image.imageName="$NEWS_CONSUMER_DOCKER_IMAGE_NAME"

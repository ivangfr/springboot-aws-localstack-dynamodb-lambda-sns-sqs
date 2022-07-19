#!/usr/bin/env bash

./mvnw clean compile jib:dockerBuild --projects news-producer
./mvnw clean compile jib:dockerBuild --projects news-consumer

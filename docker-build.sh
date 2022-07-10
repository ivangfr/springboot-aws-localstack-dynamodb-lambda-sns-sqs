#!/usr/bin/env bash

./mvnw clean compile jib:dockerBuild --projects producer-service
./mvnw clean compile jib:dockerBuild --projects consumer-service

#!/usr/bin/env sh

./gradlew clean assemble
docker build -t eu.wojciechzurek/custom-prometheus:0.0.1 -t eu.wojciechzurek/custom-prometheus:latest -f docker/prometheus/Dockerfile .
docker build -t eu.wojciechzurek/spring-boot-example-metric:0.0.1 -t eu.wojciechzurek/spring-boot-example-metric:latest -f docker/app/Dockerfile .
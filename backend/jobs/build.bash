#!/usr/bin/env bash

mvn clean package -DskipTests

docker-compose -f docker/docker-compose.yml build

docker image prune --force
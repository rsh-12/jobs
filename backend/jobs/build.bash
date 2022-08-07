#!/usr/bin/env bash

eval $(minikube docker-env)

mvn clean package -DskipTests

docker-compose -f docker/docker-compose.yml build

docker image prune --force
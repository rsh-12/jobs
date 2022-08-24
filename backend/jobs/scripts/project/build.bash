#!/usr/bin/env bash

eval $(minikube docker-env)

mvn clean package -DskipTests

docker-compose build

docker image prune --force
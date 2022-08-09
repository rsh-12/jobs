#!/usr/bin/env bash

docker-compose -f docker/docker-compose.yml up -d db keycloak postgres rabbitmq zipkin

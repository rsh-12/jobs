#!/usr/bin/env bash

docker-compose -f docker/auth/auth-server.yml up -d

docker-compose -f docker/docker-compose.yml up -d postgres rabbitmq

#!/usr/bin/env bash

docker-compose -f auth/auth-server.yml up -d

docker-compose up -d

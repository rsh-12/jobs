#!/usr/bin/env bash

eval $(minikube docker-env)

docker tag hands-on/job-service hands-on/job-service:v1
docker tag hands-on/resume-service hands-on/resume-service:v1
docker tag hands-on/company-service hands-on/company-service:v1
docker tag hands-on/composite-service hands-on/composite-service:v1
docker tag hands-on/config-server hands-on/config-server:v1
docker tag hands-on/gateway hands-on/gateway:v1
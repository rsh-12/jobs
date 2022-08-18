#!/usr/bin/env bash

eval $(minikube docker-env)

helm install hands-on-prod-env kubernetes/helm/environments/prod-env \
  -n hands-on \
  --create-namespace

kubectl config set-context $(kubectl config current-context) \
  --namespace=hands-on
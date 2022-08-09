#!/usr/bin/env bash

eval $(minikube docker-env)

helm install hands-on-dev-env kubernetes/helm/environments/dev-env \
  -n hands-on \
  --create-namespace

kubectl config set-context $(kubectl config current-context) \
  --namespace=hands-on
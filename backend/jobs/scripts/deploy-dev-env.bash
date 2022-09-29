#!/usr/bin/env bash

kubectl apply -f kubernetes/hands-on-namespace.yml

bash scripts/project/build.bash

docker-compose up -d keycloak

bash scripts/helm/dep_up_and_render.bash

bash scripts/helm/install_dev-env.bash

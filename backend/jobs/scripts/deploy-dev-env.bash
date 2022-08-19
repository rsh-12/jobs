#!/usr/bin/env bash

bash scripts/project/build.bash

docker-compose -f docker/docker-compose.yml up -d keycloak

bash scripts/helm/dep_up_and_render.bash

bash scripts/helm/install_dev-env.bash

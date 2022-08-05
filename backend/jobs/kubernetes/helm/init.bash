#!/bin/bash

DEPLOYMENT_FILE='deployment.yaml'
SERVICE_FILE='service.yaml'

DEPLOYMENT_TEMPLATE='{{- template "common.deployment" . -}}'
SERVICE_TEMPLATE='{{- template "common.service" . -}}'

function write_to_file() {
  for file in ./components/*/templates/"$1"; do
    echo "$2" > "$file"
  done
}

write_to_file $SERVICE_FILE "$SERVICE_TEMPLATE"
write_to_file $DEPLOYMENT_FILE "$DEPLOYMENT_TEMPLATE"

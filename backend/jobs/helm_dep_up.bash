#!/bin/bash

for f in ./kubernetes/helm/components/*; do
  helm dep up "$f"
done

for f in ./kubernetes/helm/environments/*; do
  helm dep up "$f"
  helm template --output-dir ./kubernetes/helm/rendered/ "$f"

  helm dep ls ./kubernetes/helm/environments/dev-env/
done


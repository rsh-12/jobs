#!/usr/bin/env bash

bash scripts/project/build.bash

bash scripts/project/ext_resources.bash

bash scripts/project/tag.bash

bash scripts/helm/dep_up_and_render.bash

bash scripts/helm/install_prod-env.bash

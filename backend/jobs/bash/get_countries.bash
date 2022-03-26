#!/usr/bin/env bash

curl https://restcountries.com/v3.1/all | jq .[].name.common > data.txt
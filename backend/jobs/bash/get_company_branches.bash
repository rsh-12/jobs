#!/usr/bin/env bash

curl https://api.hh.ru/industries?locale=EN | jq .[].name > data.txt
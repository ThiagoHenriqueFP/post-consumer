#!/bin/zsh

base_url="http://localhost:8080/posts"


for i in {1..100}; do
  curl -X POST "$base_url/$i"
done
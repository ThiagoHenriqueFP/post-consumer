#!/bin/zsh

base_url="http://localhost:8080/posts"

start=$(date +%s)
echo "Fetching 100 posts"
echo "."
echo ".."
echo "..."
for i in {1..100}; do
  curl -X POST -s "$base_url/$i" > /dev/null
  clear
done
end=$(date +%s)

printf "Time to fetch all 100 requests: %i seconds" "$(echo "${end} - ${start}" | bc -l)"
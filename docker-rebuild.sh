#!/bin/bash

echo "build jar..."
./gradlew :module-api:bootJar

echo "Stopping containers..."
docker-compose -f docker-compose-local.yml down --remove-orphans

echo "Building with no cache..."
docker-compose -f docker-compose-local.yml build --no-cache

echo "Starting containers..."
docker-compose -f docker-compose-local.yml up -d

echo "Logs (Ctrl+C to exit)..."
docker-compose -f docker-compose-local.yml logs -f
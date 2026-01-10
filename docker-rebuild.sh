#!/bin/bash

echo "Stopping containers..."
docker-compose down --remove-orphans

echo "Building with no cache..."
docker-compose build --no-cache

echo "Starting containers..."
docker-compose up -d

echo "Logs (Ctrl+C to exit)..."
docker-compose logs -f
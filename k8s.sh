#!/bin/sh
docker build -f Dockerfile -t csvoboda182/fiap-fastfood:latest .
docker login
docker push csvoboda182/fiap-fastfood:latest
minikube start
kubectl apply -f k8s/fastfood-deployment.yaml,k8s/fastfood-service.yaml,k8s/mongo-deployment.yaml,k8s/mongo-service.yaml,k8s/mongodb-data-persistentvolumeclaim.yaml
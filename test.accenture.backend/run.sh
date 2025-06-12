
# Detener y eliminar el contenedor si existe
docker stop test-accenture-backend
docker rm test-accenture-backend

# Eliminar recursos anteriores
kubectl delete deployment test-accenture-backend 
kubectl delete service test-accenture-backend 
kubectl delete hpa test-accenture-backend 

# Esperar a que los recursos se eliminen completamente
kubectl wait --for=delete deployment/test-accenture-backend --timeout=60s 
kubectl wait --for=delete service/test-accenture-backend --timeout=60s 
kubectl wait --for=delete hpa/test-accenture-backend --timeout=60s 

# Eliminar la imagen anterior si existe
docker rmi test-accenture-backend 

# Construir la imagen
docker build -t test-accenture-backend .  

# Ejecutar el contenedor
docker run -d -p 5000:5000 --name test-accenture-backend test-accenture-backend

# Aplicar las configuraciones
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/hpa.yaml

kubectl port-forward service/test-accenture-backend-service 5000:80

# Verificar el estado
# Verificar estado del deployment
kubectl get deployments -l app=test-accenture-backend 

# Verificar pods
kubectl get pods -l app=test-accenture-backend 

# Verificar servicio
kubectl get services -l app=test-accenture-backend 
kubectl get hpa -l app=test-accenture-backend 

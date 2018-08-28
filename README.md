# ContinouseDeliveryPipeline
Docker-files for the CD Pipeline

#Stopp all aktive containers
docker stop $(docker ps)

# Delete all Containers
docker rm $(docker ps -a -q)

#Delete all images
docker rmi $(docker images -q)



#Install and Run Jenkins (alone)
docker build -t jenkins_cd .

docker run -d -p=8081:8080 jenkins_cd

#install and run Nexus (alone)
docker build -t nexus .

docker run -d -p=8082:8081 nexus

#Install and run the Live Server Webapp part(without database, has to be crated before)
docker build -t LiveServer .

docker run -d -p=8080:8000 LiveServer

# build and run Docker Compose (full CD Stack) 
docker-compose up


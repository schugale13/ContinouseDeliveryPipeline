# ContinouseDeliveryPipeline
Docker-files for the CD Pipeline


 #!/bin/bash
 # Delete all containers
 docker rm $(docker ps -a -q)
 # Delete all images
 docker rmi $(docker images -q)
	#Delete all Volumes 
	docker volume rm $(docker volume ls)
	#Stoppe alle aktiven Dockercontainer
	docker stop $(docker ps -a)

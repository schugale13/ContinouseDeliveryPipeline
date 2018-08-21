
Jenkins Docker Container

Usage:

$ docker build -t jenkins_cd .

# Port 8080 is needet for the Applikation
$ docker run -d -p=8081:8080 jenkins_cd

Once Jenkins is up and running go to http://yourcurrentip:8080

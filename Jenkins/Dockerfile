FROM jenkins/jenkins:lts
MAINTAINER Alexander Schug <alexander.schug@jipp.it>

USER root
RUN apt-get update \
      && apt-get install -y sudo curl\
      && apt-get install -y libltdl7\
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

USER jenkins

# installing specific list of plugins. see: https://github.com/jenkinsci/docker#preinstalling-plugins
#COPY plugins.txt /usr/share/jenkins/plugins.txt
#RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt


############################################
# Configure Jenkins
############################################

# Jenkins defaut settings
#COPY config/config.xml /usr/share/jenkins/ref/config.xml

############################################
# Configure Jenkins Jobs
############################################
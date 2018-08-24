FROM jenkins/jenkins:lts
MAINTAINER Alexander Schug <alexander.schug@jipp.it>

USER root
RUN apt-get update \
      && apt-get install -y sudo curl\
      && apt-get install -y libltdl7\
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

USER jenkins


############################################
# Configure Jenkins
############################################

# Disable the setup wizard
ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false

# installing specific list of plugins. see: https://github.com/jenkinsci/docker#preinstalling-plugins
COPY Plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# Jenkins Settings, i.e. Maven, 
COPY config/hudson.tasks.Maven.xml /usr/share/jenkins/ref/hudson.tasks.Maven.xml

#Jenkins user configuration
COPY user/security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

############################################
# Configure Jenkins Jobs
############################################

# Adding the specific config for the PSE Project Job in xml 
#COPY jobs/PSEProject/config.xml /usr/share/jenkins/ref/jobs/PSEProject/config.xml


#better to do this with groovy scripts that can be checked into a repo
# Copy seed job
COPY jobs/*.groovy /usr/share/jenkins/ref/init.groovy.d/

# Copy job generation scripts
#COPY jobs/job-dsl/*.groovy /usr/share/jenkins/ref/init.groovy.d/job-dsl/
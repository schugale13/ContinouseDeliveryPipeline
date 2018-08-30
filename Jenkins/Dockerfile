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
COPY config/mavensettings.xml /usr/share/jenkins/.m2/settings.xml

#Jenkins config such as user configuration
COPY config/jenkinsconfig.groovy /usr/share/jenkins/ref/init.groovy.d/jenkinsconfig.groovy
#defalut credentials (for nexus)
COPY config/credentials.xml /usr/share/jenkins/ref/credentials.xml

############################################
# Configure Jenkins Jobs
############################################

# Adding the specific config for the PSE Project Seed Job in xml this job ads the rpo for job creation jobs
COPY jobs/PSEProjectSeeder/config.xml /usr/share/jenkins/ref/jobs/_PSEProjectSeeder/config.xml
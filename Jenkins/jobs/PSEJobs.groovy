String basePath = 'PSEProject'
String repo = 'schugale13/PSE-Project2017'

folder(basePath) {
    description 'This Jobs are Running all PSE Project Jobs'
}



//Does the Docu Job--also java doc ??

job("$basePath/01_Maven-PSE-Documentation") {
    description 'This Job generates documentation From the Pom File'
    scm {
        github repo
    }
    triggers {
        //scm 'H/15 * * * *'
    }
    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('site')
        }
//TODO: Javadoc mit generieren lassen

    }

}

//Maven Package (inkl unit tests)

job("$basePath/02_Maven-PSE-build") {
    description 'This Job gbuilds the application and does the Unit Testing.'
    scm {
        github repo
    }
    triggers {
        //scm 'H/15 * * * *'
    }
    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean package')
         }

        publishers {
            archiveJunit('**/target/surefire-reports/*.xml')
        }
        //TODO: auswertung der Unit tests schön darstellen
        //TODO: analyse mit sonarqube einbauen ??
    }
}


//publish war file to nexus for testing

job("$basePath/03_Maven-PSE-build-Release for TestEnvironment") {
    description 'This Job skip testing to upload a snapshot to nexus-for further testing '
    scm {
        github repo
    }

    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('versions:set -DnewVersion=$BUILD_NUMBER-forUITests')

        }
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('deploy -Dmaven.test.skip=true')

        }



    }
}

//publish war file to nexus for Preprod

job("$basePath/04_Maven-PSE-build-Release for PreProd") {
    scm {
        github repo
    }

    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('versions:set -DnewVersion=$BUILD_NUMBER-Preprod')

        }
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('deploy -Dmaven.test.skip=true')



        }




    }
}
//publish war file to nexus for Production

job("$basePath/05_Maven-PSE-build-Release for Live System") {
    scm {
        github repo
    }

    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('versions:set -DnewVersion=$BUILD_NUMBER-Prod')

        }
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('deploy -Dmaven.test.skip=true')


        }


    }
}

pipelineJob('PipelineJobtest') {
    definition {
        cps {
            sandbox()
            script("""
node {
    def mvnHome
    stage('Preparation') {
        github repo
        mvnHome = tool 'M3'
    }
    stage('Build') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"

    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
    stage('Publish') {
        nexusPublisher nexusInstanceId: 'localNexus', nexusRepositoryId: 'releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'war/target/jenkins.war']], mavenCoordinate: [artifactId: 'jenkins-war', groupId: 'org.jenkins-ci.main', packaging: 'war', version: '2.23']]]
    }
}
      """.stripIndent())
        }
    }
}


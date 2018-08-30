String basePath = 'PSEProject'
String repo = 'schugale13/PSE-Project2017'

folder(basePath) {
    description 'This Jobs are Running all PSE Project Jobs'
}



//Does the Docu Job--also java doc ??

job("$basePath/01_Maven-PSE-Documentation") {
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
        //TODO: auswertung der Unit tests schön darstellen



    }
}


//publish war file to nexus for testing

job("$basePath/03_Maven-PSE-build-Release for TestEnvironment") {
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
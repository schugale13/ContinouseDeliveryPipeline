String basePath = 'PSEProject'
String repo = 'schugale13/PSE-Project2017'

folder(basePath) {
    description 'This Jobs are Running all PSE Project Jobs'
}

//Does the Docu Job--also java doc ??

job("$basePath/Maven-PSE-Documentation") {
    scm {
        github repo
    }
    triggers {
        scm 'H/15 * * * *'
    }
    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('site')
        }


    }

}

//Maven Package (inkl unit tests)

job("$basePath/Maven-PSE-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/15 * * * *'
    }
    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean package')
         }
        //TODO: auswertung der Unit tests schön darstellen



    }
}

//Maven Relese to Nexus

job("$basePath/Maven-PSE-build") {
    scm {
        github repo
    }

    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean')
            goals('versions:set')
            goals('deploy')
            

        }
        //TODO: auswertung der Unit tests schön darstellen



    }
}
String basePath = 'PSEProject'
String repo = 'schugale13/PSE-Project2017'

folder(basePath) {
    description 'This Jobs are Running all PSE Project Jobs'
}

job("$basePath/Maven-PSE-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        maven {
            mavenInstallation('Maven 3.3.3')
            goals('clean package')
         }


    }
}


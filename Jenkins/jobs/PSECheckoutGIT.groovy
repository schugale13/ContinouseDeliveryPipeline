String basePath = 'PSEProject'
String repo = 'https://github.com/schugale13/PSE-Project2017.git'

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
        maven('clean package')
    }
}

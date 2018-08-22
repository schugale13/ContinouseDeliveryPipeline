// Repository with the PSE Project
repo = 'https://github.com/schugale13/PSE-Project2017.git'

// Create environment folder and views
def createEnvFoldersAndViews(String env) {
    // Create environment folder
    folder(env)

    // Create environment views
    listView("${env}/PSE-Projekt") {
        jobs {
            regex(/PSE-Maven-Build.*/)
        }
        columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
        }
    }

    }


// Create PSE-Projekt Jobs
def createPSEProjectJobs(String env) {
    pipelineJob("${env}/Terraform") {
        definition {
            cps {
                sandbox(false)
                pipeline = readFileFromWorkspace('terraform.pipeline')
                script(pipeline.replaceAll("%%ENV%%", env.toLowerCase()))
            }
        }
    }
}



// Iterate over the environments and create views, folders andjobs.
environments.each { env ->
    createEnvFoldersAndViews("${env}")

    types[env].each { type ->
        createAnsibleRunJobs(env, type)
    }
    createTerraformJobs(env)
    createMarathonJobs(env)
}
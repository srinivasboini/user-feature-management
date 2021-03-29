pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'gitsrinivas', url: 'https://github.com/srinivasboini/user-feature-management.git']]])
            }
           }
           stage('Build') {
                      steps {
                              sh "mvn -Dmaven.test.failure.ignore=true clean package"
                           }
                   }
    }
}

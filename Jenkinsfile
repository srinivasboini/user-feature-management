pipeline {
    agent any



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

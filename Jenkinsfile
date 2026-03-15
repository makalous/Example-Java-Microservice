pipeline {
    agent any

    stages {

        stage('Clone repo') {
            steps {
                git 'https://github.com/makalous/Example-Java-Microservice.git'
            }
        }

        stage('Build jar') {
            steps {
                sh 'gradle build'
            }
        }

        stage('Deploy') {
            steps {
                sh 'ansible-playbook -i inventory deploy.yml'
            }
        }

    }
}
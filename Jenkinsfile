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
                sh './gradlew build'
            }
        }

        stage('Deploy') {
            steps {
                sh '/opt/homebrew/bin/ansible-playbook -i inventory deploy.yml'
            }
        }

    }
}
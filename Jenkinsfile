pipeline {
    agent any
    
    parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: 'Branch to build')
    }
    
    stages {

        stage('Clone repo') {
            steps {
                git branch: params.BRANCH,
                    url: 'https://github.com/makalous/Example-Java-Microservice.git'
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

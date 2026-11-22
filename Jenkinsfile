pipeline {
    agent any
    environment {
        JAVA_HOME = '/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
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
                sh 'whoami; which java; java --version; echo $JAVA_HOME'
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

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout..'
                sh 'git checkout'
            }
        }
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn clean compile'
            }
        }

         stage('Test') {
            steps {
                echo 'Testing....'
                sh 'mvn verify'
            }
        }

         stage('StaticCodeAnalysis') {
            steps {
                echo 'Spot bugs....'
                sh 'mvn spotbugs:check'
            }
         }
    }
}
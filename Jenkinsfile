pipeline {
    agent any

    tools {
        maven 'Maven-3.9.6'
        jdk 'AdoptOpenJDK-17'
    }

    environment {
        DOCKER_IMAGE_NAME = "springboot-app"
        DOCKER_IMAGE_TAG = "latest"
    }

    stages {

        stage('Check Minikube Version') {
            steps {
                bat '"C:\\minikube\\minikube.exe" version'
            }
        }

        stage('Check Tools') {
            steps {
                bat 'docker --version'
                bat 'kubectl version --client'
            }
        }

        stage('Build') {
            steps {
                bat 'mvnw.cmd clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvnw.cmd package -DskipTests'
            }
        }

        stage('Build Docker Image in Minikube Docker') {
            steps {
                bat '''
                    C:\\minikube.exe docker-env --shell=cmd > minikube-env.cmd
                    call minikube-env.cmd
                    docker build -t springboot-app:latest .
                '''
            }
        }

        stage('Deploy to Minikube') {
            steps {
                bat 'kubectl apply -f k8s/deployment.yaml'
                bat 'kubectl apply -f k8s/service.yaml'
                bat 'kubectl rollout status deployment/springboot-app-deployment --timeout=300s'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline successfully completed!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}

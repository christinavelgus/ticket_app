pipeline {
    agent any

    tools {
        maven 'Maven-3.9.6'
        jdk 'AdoptOpenJDK-17'
    }

    environment {
        DOCKER_IMAGE = 'khrystyyyyna/ticket-app:latest'
        DOCKER_CREDENTIALS_ID = 'docker-hub-creds'
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

        stage('Set Docker Environment for Minikube') {
            steps {
                bat '''
            call minikube -p minikube docker-env | Invoke-Expression
        '''
    }
}


        stage('Check Docker Status') {
            steps {
                bat 'docker info'
            }
        }

        stage('Check Minikube Status') {
            steps {
                bat 'minikube status'
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
                    docker build -t springboot-app:latest .
                '''
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker tag springboot-app:latest khrystyyyyna/ticket-app:latest
                        docker push khrystyyyyna/ticket-app:latest
                    '''
                }
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

        stage('Clean Up') {
            steps {
                bat 'docker system prune -f'
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

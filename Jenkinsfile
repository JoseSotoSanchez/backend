pipeline {
    agent any
    stages{
        stage('Build backend'){
            steps{
                tool 'maven'
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JoseSotoSanchez/backend']])
                bat 'mvn clean'
                bat 'mvn package -DskipTests'
            }
        }

         stage('Build frontend') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JoseSotoSanchez/FrontEnd']])
                bat 'cd C:\\Users\\Jose Soto Sanchez\\OneDrive\\Documentos\\Mingeso 1-2024\\EvaluacionUno\\FrontEnd'
                bat 'npm run build'
            }
         }

        stage('Build backend docker image'){
            steps{
                script{
                    bat 'docker build -t josesotosa/autofix-backend:latest .'
                }
            }
        }

        stage('Build frontend docker image'){
            steps{
                script{
                    bat 'docker build -t josesotosa/autofix-frontend:latest .'
                }
            }
        }

        stage('Push image frontend to Docker Hub'){
            steps{
                script{
                   bat 'docker login -u josesotosa -p %asd123asd%'
                   bat 'docker push josesotosa/autofix-frontend:latest'
                }
            }
        }

         stage('Push image backend to Docker Hub'){
                    steps{
                        script{
                           bat 'docker login -u josesotosa -p %asd123asd%'
                           bat 'docker push josesotosa/autofix-backend:latest'
                        }
                    }
                }
    }
}
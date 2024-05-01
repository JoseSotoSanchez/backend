pipeline {
    agent any
     tools{
            maven 'maven_3_8_1'
        }
    stages{
        stage('Build backend'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JoseSotoSanchez/backend']])
                bat 'mvn clean package'
            }
        }

         stage('Build frontend') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JoseSotoSanchez/FrontEnd']])
                bat 'npm install'
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
#!groovy
properties([pipelineTriggers([pollSCM('H/2 * * * *')])])
pipeline {
    agent any
    tools {
        maven 'maven3.6.3'
    }
    stages {
        stage('チェックアウト') {
            steps {
                checkout scm
            }
        }
        stage('frontendビルド') {
            steps {
                dir('frontend') {
                    nodejs(nodeJSInstallationName: 'NodeJS LTS') {
                        sh 'npm install --silent'
                        sh 'npm run build'
                    }
                }
            }
        }
        stage('backendビルド') {
            steps {
                script {
//                    // Wait until mysql service is up
//                    sh './todo-backend/wait-for-it.sh -t 30 todo-mysql:3306'
//                    // Run Backend UT
//                    //sh 'mvn clean jacoco:prepare-agent test jacoco:report -f todo-backend'
                      sh 'mvn clean package -f backend'
                }
            }
        }
        stage('静的解析') {
            steps {
//                withSonarQubeEnv('default') {
//                    sh """
//                      ${tool 'sonarqube-scanner'}/bin/sonar-scanner \
//                        -Dsonar.projectKey=workshop:frontend \
//                        -Dsonar.projectName=team2-frontend \
//                        -Dsonar.projectVersion=1 \
//                        -Dsonar.javascript.lcov.reportPaths=frontend/tests/unit/coverage/lcov.info \
//                        -Dsonar.sources=frontend/src 
//                    """
//                }
                sh """
                  mvn sonar:sonar \
                    -f backend \
                    -Dsonar.host.url=http://sonar:9000 \
                    -Dsonar.projectKey=workshop:backend \
                    -Dsonar.projectName=backend
                """
            }
        }
        stage('frontendデプロイ') {
            steps {
                dir('frontend') {
                    nodejs(nodeJSInstallationName: 'NodeJS LTS') {
                        sh 'docker build -t frontend:latest'
                        sh 'docker run --rm frontend'
                    }   
                }
            }
        }
        stage('backendデプロイ') {
            steps {
                dir('backend') {
                    sh 'docker build -t backend:latest'
                    sh 'docker run --rm backend'
                }
            }
        }
        stage('受け入れテスト') {
            steps {
                sh '_JAVA_OPTIONS=-Dfile.encoding=UTF-8 xvfb-run mvn clean test -f at -Dselenide.baseUrl=http://nginx -Dselenide.browser=chrome -Dat.db.url=jdbc:mysql://mariadb:3306/backlog'
            }
        }
    }
    post {
        always {
            // save test JUnit format reports
            junit allowEmptyResults: true, testResults: 'test/**/*.xml'
        }
        failure {
            echo 'FAILURE!!!'
        }
    }
}

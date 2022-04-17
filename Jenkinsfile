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
                      sh 'mvn clean package -f backend'
                }
            }
        }
        stage('静的解析') {
            steps {
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
                        sh 'rm -r /usr/share/nginx/html/*'
                        sh 'cp -r dist/* /usr/share/nginx/html'
                    }   
                }
            }
        }
        stage('backendデプロイ') {
            steps {
                dir('backend') {
                    sh 'mvn spring-boot:stop'
                    sh 'mvn spring-boot:start -Dspring-boot.run.profiles=jenkins'
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

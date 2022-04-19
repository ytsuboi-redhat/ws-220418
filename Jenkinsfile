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
        stage('backend単体テスト') {
            steps {
                dir('backend') {
                   sh 'mvn clean jacoco:prepare-agent test jacoco:report -Dspring.profiles.active="container"'
                }
            }
        }
        stage('静的解析') {
            steps {
                dir('frontend') {
                    withSonarQubeEnv('default') {
                        sh """
                            ${tool 'sonarqube-scanner'}/bin/sonar-scanner \
                            -Dsonar.projectKey=workshop:frontend \
                            -Dsonar.projectName=frontend \
                            -Dsonar.projectVersion=1 \
                            -Dsonar.javascript.lcov.reportPaths=tests/unit/coverage/lcov.info \
                            -Dsonar.sources=src 
                        """
                    }
                }
                dir('backend') {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.host.url=http://sonar:9000 \
                        -Dsonar.projectKey=workshop:backend \
                        -Dsonar.projectName=backend
                    """
                }
            }
        }
        stage('backendビルド') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests=true'
                }
            }
        }
        stage('frontendビルド') {
            steps {
                dir('frontend') {
                    nodejs(nodeJSInstallationName: 'NodeJS LTS') {
                        sh 'npm install'
                        sh 'npm run build'
                    }
                }
            }
        }
        stage('backendデプロイ') {
            steps {
                dir('backend') {
                    sh 'docker build . -t backend:latest'
                    sh 'docker rm -f backend'
                    sh 'docker run -d --name backend --net=ci_default -p 8080:8080 backend'
                }
            }
        }
        stage('frontendデプロイ') {
            steps {
                dir('frontend') {
                    sh 'docker build . -t frontend:latest'
                    sh 'docker rm -f frontend'
                    sh 'docker run -d --name frontend --net=ci_default -p 80:80 frontend'
                }
            }
        }
        stage('受け入れテスト') {
            steps {
                dir('at') {
                    sh '_JAVA_OPTIONS=-Dfile.encoding=UTF-8 xvfb-run mvn clean test -Dselenide.baseUrl=http://frontend -Dselenide.browser=chrome -Dat.db.host=mariadb:3306 -Dat.db.schema=backlog'
                }
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

pipeline {
    agent any
    
    environment {
        MAVEN_HOME = tool 'MAVEN_HOME'
    }
    
    stages {
        stage('Clone') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    git branch: 'main',
                        credentialsId: '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',
                        url: 'https://github.com/WilbertAlex/-Ambiente-de-Pruebas.git'
                }
            }
        }
        
        stage('Build') {
            steps {
                timeout(time: 8, unit: 'MINUTES') {
                    sh "${MAVEN_HOME}/bin/mvn -DskipTests clean package -f turismo-spring-boot/pom.xml"
                }
            }
        }
        
        stage('Test') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    sh "${MAVEN_HOME}/bin/mvn test -f turismo-spring-boot/pom.xml"
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    withSonarQubeEnv('SonarQube') {
                        withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                            sh """
                                ${MAVEN_HOME}/bin/mvn clean verify sonar:sonar \
                                  -Dsonar.projectKey=turismo-spring-boot \
                                  -Dsonar.projectName='Turismo Spring Boot' \
                                  -Dsonar.host.url=http://localhost:9000 \
                                  -Dsonar.login=\$SONAR_TOKEN \
                                  -f turismo-spring-boot/pom.xml
                            """
                        }
                    }
                }
            }
        }
        
        stage('Quality gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        
        stage('Deploy') {
            steps {
                timeout(time: 8, unit: 'MINUTES') {
                    sh "${MAVEN_HOME}/bin/mvn spring-boot:run -f turismo-spring-boot/pom.xml"
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}
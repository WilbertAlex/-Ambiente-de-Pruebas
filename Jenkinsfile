pipeline {
    agent any

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
                    sh "${tool 'MAVEN_HOME'}/bin/mvn -DskipTests clean package -f turismo-spring-boot/pom.xml"
                }
            }
        }

        stage('Test') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    sh "${tool 'MAVEN_HOME'}/bin/mvn clean install -f turismo-spring-boot/pom.xml"
                }
            }
        }

        stage('SonarQube Analysis') {
    steps {
        withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
            sh '''
                ${MAVEN_HOME}/bin/mvn clean verify sonar:sonar \
                  -Dsonar.projectKey=turismo-spring-boot \
                  -Dsonar.host.url=http://docker.sonar:9000 \
                  -Dsonar.login=$SONAR_TOKEN
            '''
        }
    }
}

        stage('Quality gate') {
            steps {
                sleep 10
                timeout(time: 4, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy') {
            steps {
                timeout(time: 8, unit: 'MINUTES') {
                    sh "${tool 'MAVEN_HOME'}/bin/mvn spring-boot:run -f turismo-spring-boot/pom.xml"
                }
            }
        }
    }
}

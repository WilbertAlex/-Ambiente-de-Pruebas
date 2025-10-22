pipeline {
  agent any

  environment {
    MAVEN_HOME = tool 'MAVEN_HOME'
  }

  options {
    // Opcional: falla más rápido si hay etapas colgadas
    timeout(time: 30, unit: 'MINUTES')
  }

  stages {
    stage('Clone') {
      steps {
        timeout(time: 2, unit: 'MINUTES') {
          git branch: 'main',
              credentialsId: 'github-token',
              url: 'https://github.com/WilbertAlex/-Ambiente-de-Pruebas.git'
        }
      }
    }

    stage('Build') {
      steps {
        timeout(time: 8, unit: 'MINUTES') {
          sh "${MAVEN_HOME}/bin/mvn -B -DskipTests clean package -f turismo-spring-boot/pom.xml"
        }
      }
    }

    stage('Test') {
      steps {
        timeout(time: 10, unit: 'MINUTES') {
          // verify para generar reportes (incl. JaCoCo si está configurado)
          sh "${MAVEN_HOME}/bin/mvn -B clean verify -f turismo-spring-boot/pom.xml"
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          // Usa el server configurado en Manage Jenkins > SonarQube servers (nombre exacto)
          withSonarQubeEnv('SonarQube') {
            sh """
              ${MAVEN_HOME}/bin/mvn -B -f turismo-spring-boot/pom.xml \
                org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar
            """
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
      when {
        expression { return env.CHANGE_ID == null } // opcional: evita deploy en PRs
      }
      steps {
        timeout(time: 8, unit: 'MINUTES') {
          sh "${MAVEN_HOME}/bin/mvn -B spring-boot:run -f turismo-spring-boot/pom.xml"
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
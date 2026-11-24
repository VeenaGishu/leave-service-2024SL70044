
pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        disableConcurrentBuilds()
    }

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_PROJECT_KEY = 'leave-service-2024SL70044'
	IMAGE_NAME = 'leave-service-2024sl70044'
	STAGING_CONTAINER = 'leave-service-staging-2024sl70044'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean verify'
            }

            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Code Inspection') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'sonar-token',
                        variable: 'SONAR_TOKEN'
                    )
                ]) {
                    sh '''
                        set +x
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.11.0.3922:sonar \
                          -Dsonar.projectKey="leave-service-2024SL70044" \
                          -Dsonar.host.url="http://localhost:9000" \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                          -Dsonar.qualitygate.wait=true
                    '''
                }
            }
        }
	stage('Build Docker Image') {
    steps {
        sh '''
            docker build \
              -t "$IMAGE_NAME:$BUILD_NUMBER" .

            docker images "$IMAGE_NAME"
        '''
    }
}
	stage('Deploy to Staging') {
    steps {
        sh '''
            docker rm -f "$STAGING_CONTAINER" \
              2>/dev/null || true

            docker run -d \
              --name "$STAGING_CONTAINER" \
              -p 8085:8080 \
              -e APP_ENV=staging \
              "$IMAGE_NAME:$BUILD_NUMBER"

            docker ps
        '''
    }
}
	stage('Verify Staging') {
    steps {
        sh '''
            response=$(curl -fsS \
              --retry 10 \
              --retry-connrefused \
              --retry-delay 2 \
              http://localhost:8085)

            printf '%s\\n' "$response"

            printf '%s\\n' "$response" | \
              grep -F "Environment: staging"
        '''
    }
}
    }
}

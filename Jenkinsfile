pipeline {
    agent any
    stages {
        stage ('Lint') {
            steps {
                sh 'mvn checkstyle:check'
            }
        }
        stage ('Initialize with clean target dir') {
            steps {
                sh 'mvn clean'
            }
        }
        stage ('Compile Sources') {
            steps {
                sh 'mvn compile'
            }
        }
        stage ('Compile Test Sources') {
            steps {
                sh 'mvn test-compile'
            }
        }
        stage ('Run unit tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage ('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage ('Deploy to AWS') {
            steps {
            withAWS(region:'us-east-1',credentials:'aws-static') {
                s3Upload(pathStyleAccessEnabled: true, payloadSigningEnabled: true, workingDir:"target", includePathPattern:"*.jar", path:"chat-app/branch/", bucket:"scgrk.jenkins.pipeline")
                }
            }
        }
        stage ('Clean-up target dir') {
            steps {
                sh 'mvn clean'
            }
        }
    }
}

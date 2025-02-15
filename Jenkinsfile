pipeline {
    agent any
    tools { 
        maven 'maven 3.8.6' 
    }
    parameters {
        string(name: 'TAG', description: 'Docker image tag (e.g., 1.2)')
        string(name: 'BRANCH', description: 'Git branch to build from (e.g., main)')
    }


    stages {
        stage('Checkout Code') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: params.BRANCH]],
                        extensions: [],
                        userRemoteConfigs: [[
                            url: "https://github.com/98izh/length.git"
                        ]]
                    ])
                }
            }
        }

        stage('Maven Build') {
            steps {
                sh "mvn clean install -Dmaven.test.skip=true"
            }
        }

        stage('Prepare Docker Artifacts') {
            steps {
                script {
                    // Ensure target directory exists
                    sh "mkdir -p target"
                    
                    // Copy JAR to target if not already there
                    sh """
                        ls target/length.jar || cp target/*.jar target/length.jar
                        cp target/length.jar docker/
                    """
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    dir('docker') {
                        // Build and push Docker image
                        sh """
                            docker login -u 98izh -p zRSr20ssiWXwHCr docker.io
                            docker build -t 98izh/length:${TAG} .
                            docker push 98izh/length:${TAG}
                        """
                    }
                }
            }
        }
    }
}
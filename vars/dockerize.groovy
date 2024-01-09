def buildDockerImage(String DOCKER_REGISTRY, String DOCKER_IMAGE, String BUILD_NUMBER) {
    script {
        docker.build("${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}")
    }
}

def pushDockerImage(String DOCKER_REGISTRY, String DOCKER_IMAGE, String BUILD_NUMBER, String credentialsId) {
    script {
        withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_REGISTRY_USERNAME', passwordVariable: 'DOCKER_REGISTRY_PASSWORD')]) {
            sh "echo \${DOCKER_REGISTRY_PASSWORD} | docker login -u \${DOCKER_REGISTRY_USERNAME} --password-stdin"
            docker.image("${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}").push()
        }
    }
}
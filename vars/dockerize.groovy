def getCommitHash() {
    return sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
}

def buildDockerImage(String DOCKER_REGISTRY, String DOCKER_IMAGE) {
    script {
        def COMMIT_HASH = getCommitHash()
        docker.build("${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${COMMIT_HASH}")
    }
}

def pushDockerImage(String DOCKER_REGISTRY, String DOCKER_IMAGE, String credentialsId) {
    script {
        def COMMIT_HASH = getCommitHash()
        withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_REGISTRY_USERNAME', passwordVariable: 'DOCKER_REGISTRY_PASSWORD')]) {
            sh "echo \${DOCKER_REGISTRY_PASSWORD} | docker login -u \${DOCKER_REGISTRY_USERNAME} --password-stdin"
            docker.image("${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${COMMIT_HASH}").push()
        }
    }
}
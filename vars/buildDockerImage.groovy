def buildDockerImage(String DOCKER_REGISTRY, String DOCKER_IMAGE, String BUILD_NUMBER) {
    script {
        docker.build("${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}")
    }
}
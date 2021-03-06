/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bmuschko.gradle.docker.tasks.container

import com.bmuschko.gradle.docker.response.ResponseHandler
import com.bmuschko.gradle.docker.response.container.InspectContainerResponseHandler

class DockerInspectContainer extends DockerExistingContainer {
    private ResponseHandler<Void, Object> responseHandler = new InspectContainerResponseHandler()

    @Override
    void runRemoteCommand(dockerClient) {
        logger.quiet "Inspecting container with ID '${getContainerId()}'."
        def container = dockerClient.inspectContainerCmd(getContainerId()).exec()

        if (onNext) {
            onNext.call(container)
        } else if (responseHandler) {
            responseHandler.handle(container)
        }
    }

    /**
     * Deprecated. Use {@link #onNext} instead.
     * @param responseHandler
     */
    @Deprecated
    void setResponseHandler(ResponseHandler<Void, Object> responseHandler) {
        this.responseHandler = responseHandler
    }
}

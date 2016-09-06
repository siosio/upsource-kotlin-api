package com.github.siosio.upsource.exception

class ProjectNotFoundException(val projectId: String) : RuntimeException("project id: $projectId") {
}

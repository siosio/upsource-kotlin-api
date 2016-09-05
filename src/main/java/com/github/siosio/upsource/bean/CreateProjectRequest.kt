package com.github.siosio.upsource.bean

data class CreateProjectRequest(
    val newProjectId: String,
    val settings: ProjectSettings
)


package com.github.siosio.upsource.bean

data class ProjectModel(
    val type: String,
    val pathToModel: String? = null,
    val defaultJdkId: String? = null
)

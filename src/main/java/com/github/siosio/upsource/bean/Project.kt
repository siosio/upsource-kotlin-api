package com.github.siosio.upsource.bean

data class Project(
    val projectId: String,
    val projectName: String,
    val headHash: String,
    val isReady: Boolean,
    val lastCommitDate: Long? = null,
    val lastCommitAuthorName: String? = null
)

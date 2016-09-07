package com.github.siosio.upsource.bean

data class CreateReviewRequest(
    val projectId: String,
    val title: String,
    val revisions: List<String> = emptyList(),
    val branch: String? = null
)

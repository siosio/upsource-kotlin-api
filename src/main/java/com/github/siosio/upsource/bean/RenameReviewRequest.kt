package com.github.siosio.upsource.bean

data class RenameReviewRequest(
    val reviewId: ReviewId,
    val text: String
)
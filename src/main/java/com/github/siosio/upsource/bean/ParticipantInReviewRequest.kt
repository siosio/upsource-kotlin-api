package com.github.siosio.upsource.bean

data class ParticipantInReviewRequest(
    val reviewId: ReviewId,
    val participant: ParticipantInReview
)

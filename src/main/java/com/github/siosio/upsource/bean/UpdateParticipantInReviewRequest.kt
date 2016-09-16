package com.github.siosio.upsource.bean

data class UpdateParticipantInReviewRequest(
    val reviewId: ReviewId,
    val state: ParticipantStateEnum
)

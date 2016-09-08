package com.github.siosio.upsource.bean

data class ParticipantInReview(
    val userId: String,
    val role: RoleInReviewEnum,
    val state: ParticipantStateEnum? = null
)

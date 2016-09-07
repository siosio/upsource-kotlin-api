package com.github.siosio.upsource.bean

data class ReviewDescriptor(
    val reviewId: ReviewId,
    val title: String,
    val participants: List<ParticipantInReview>?,
    val state: ReviewStateEnum,
    val isUnread: Boolean?,
    val priority: PriorityClassEnum?,
    val branch: String?,
    val issue: List<String>?,
    val isRemoved: Boolean?,
    val createdBy: String,
    val createdAt: Long,
    val updatedAt: Long,
    val completionRate: CompletionRate
)

data class CompletionRate(
    val completedCount: Int,
    val reviewersCount: Int
)


package com.github.siosio.upsource.bean

import com.fasterxml.jackson.annotation.*

data class ReviewDescriptor(
    @JsonProperty("reviewId") val review: ReviewId,
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
) {

  @JsonIgnore
  val projectId: String
  @JsonIgnore
  val reviewId: String

  init {
    projectId = review.projectId
    reviewId = review.reviewId
  }
}

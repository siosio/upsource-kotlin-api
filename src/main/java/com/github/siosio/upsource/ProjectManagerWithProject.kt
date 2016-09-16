package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.exception.*
import com.github.siosio.upsource.internal.*

class ProjectManagerWithProject internal constructor(val projectId: String, val upsourceApi: UpsourceApi) {

  /**
   * create review
   */
  operator fun CreateReviewHolder.unaryPlus(): ReviewDescriptor {
    val review = upsourceApi.send(CreateReviewCommand(this.createReviewRequest))
    this.block?.invoke(review)
    return review
  }

  fun review(
      title: String,
      revisions: List<String> = emptyList(),
      branch: String? = null,
      block: (ReviewDescriptor.() -> Unit)? = null) = CreateReviewHolder(CreateReviewRequest(projectId, title, revisions, branch), block)

  data class CreateReviewHolder(val createReviewRequest: CreateReviewRequest, val block: (ReviewDescriptor.() -> Unit)? = null)

  /**
   * delete review
   */
  operator fun ReviewDescriptor.unaryMinus() {
    upsourceApi.send(RemoveReviewCommand(this.review))
  }

  fun review(reviewId: String, block: (ReviewDescriptor.() -> Unit)? = null): ReviewDescriptor {
    val review = upsourceApi.send(GetReviewDetailsCommand(ReviewId(projectId, reviewId))) ?: throw ReviewNotFoundException(projectId, reviewId)
    block?.invoke(review)
    return review
  }

  /**
   * add reviewer/watcher
   */
  operator fun ParticipantInReviewRequest.unaryPlus() {
    upsourceApi.send(AddParticipantToReviewCommand(this))
  }

  /**
   * remove reviewer/watcher
   */
  operator fun ParticipantInReviewRequest.unaryMinus() {
    upsourceApi.send(RemoveParticipantFromReviewCommand(this))
  }

  fun ReviewDescriptor.reviewer(userId: String): ParticipantInReviewRequest =
      ParticipantInReviewRequest(review, ParticipantInReview(userId, RoleInReviewEnum.Reviewer))

  fun ReviewDescriptor.watcher(userId: String): ParticipantInReviewRequest =
      ParticipantInReviewRequest(review, ParticipantInReview(userId, RoleInReviewEnum.Watcher))


}


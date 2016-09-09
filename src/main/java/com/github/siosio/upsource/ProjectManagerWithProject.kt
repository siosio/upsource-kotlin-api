package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
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
      block: (ReviewDescriptor.() -> Unit)? = null) =
      CreateReviewHolder(CreateReviewRequest(projectId, title, revisions, branch), block)

  data class CreateReviewHolder(val createReviewRequest: CreateReviewRequest, val block: (ReviewDescriptor.() -> Unit)? = null)

  /**
   * delete review
   */
  operator fun ReviewId.unaryMinus() {
    upsourceApi.send(RemoveReviewCommand(this))
  }

  fun review(reviewId: String) = ReviewId(projectId, reviewId)

  /**
   * add reviewer
   */
  fun ReviewDescriptor.reviewer(userId: String): Unit {
    upsourceApi.send(AddParticipantToReviewCommand(
        ParticipantInReviewRequest(review, ParticipantInReview(userId, RoleInReviewEnum.Reviewer))))
  }

  /**
   * add watcher
   */
  fun ReviewDescriptor.watcher(userId: String): Unit {
    upsourceApi.send(AddParticipantToReviewCommand(
        ParticipantInReviewRequest(review, ParticipantInReview(userId, RoleInReviewEnum.Watcher))))
  }
}


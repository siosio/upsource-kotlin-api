package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.internal.*

class ReviewManagerWithReview internal constructor(
    private val reviewId: ReviewId,
    private val upsourceApi: UpsourceApi
) {

  operator fun ParticipantInReviewRequest.unaryPlus() {
    upsourceApi.send(AddParticipantToReviewCommand(this))
  }

  fun reviewer(userId: String): ParticipantInReviewRequest {
    return ParticipantInReviewRequest(reviewId, ParticipantInReview(userId, RoleInReviewEnum.Reviewer))
  }
}

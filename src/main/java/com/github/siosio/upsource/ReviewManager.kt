package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.internal.*

class ReviewManager internal constructor(val upsourceApi: UpsourceApi) {

  operator fun ParticipantInReviewRequest.unaryPlus() {
    upsourceApi.send(AddParticipantToReviewCommand(this))
  }

  fun reviewer(reviewId: ReviewId, userId: String): ParticipantInReviewRequest {
    return ParticipantInReviewRequest(reviewId, ParticipantInReview(userId, RoleInReviewEnum.Reviewer))
  }
}
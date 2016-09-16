package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class UpdateParticipantInReviewCommand(private val request: UpdateParticipantInReviewRequest) : Command<UpdateParticipantInReviewRequest, VoidMessage> {
  override val name: String = "updateParticipantInReview"

  override val responseType: Class<VoidMessage> = VoidMessage::class.java

  override fun getRequest(): UpdateParticipantInReviewRequest = request
}


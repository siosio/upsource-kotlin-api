package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class RemoveParticipantFromReviewCommand(private val participantInReviewRequest: ParticipantInReviewRequest) : Command<ParticipantInReviewRequest, VoidMessage> {

  override val name: String = "removeParticipantFromReview"

  override val responseType: Class<VoidMessage> = VoidMessage::class.java

  override fun getRequest(): ParticipantInReviewRequest = participantInReviewRequest
}

package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class RemoveReviewCommand(private val review: ReviewId) : Command<ReviewId, VoidMessage> {

  override val name: String = "removeReview"

  override val responseType: Class<VoidMessage> = VoidMessage::class.java

  override fun getRequest() = review
}


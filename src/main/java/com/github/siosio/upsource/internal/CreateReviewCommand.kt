package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class CreateReviewCommand(private val createReviewRequest: CreateReviewRequest) :
    Command<CreateReviewRequest, ReviewDescriptor> {

  override val name: String = "createReview"

  override val responseType: Class<ReviewDescriptor> = ReviewDescriptor::class.java

  override fun getRequest() = createReviewRequest
}


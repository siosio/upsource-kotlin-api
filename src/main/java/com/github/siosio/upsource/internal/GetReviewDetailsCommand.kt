package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class GetReviewDetailsCommand(private val reviewId: ReviewId) : Command<ReviewId, ReviewDescriptor> {

  override val name: String = "getReviewDetails"

  override val responseType: Class<ReviewDescriptor> = ReviewDescriptor::class.java

  override fun getRequest(): ReviewId = reviewId

}

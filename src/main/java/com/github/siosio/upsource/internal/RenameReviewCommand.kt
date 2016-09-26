package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

internal class RenameReviewCommand(private val renameReviewRequest: RenameReviewRequest) : Command<RenameReviewRequest, RenameReviewResponse> {

  override val name: String = "renameReview"

  override val responseType: Class<RenameReviewResponse> = RenameReviewResponse::class.java

  override fun getRequest(): RenameReviewRequest = renameReviewRequest
}


package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class GetReviewDetailsCommandTest : UpsourceApiTestSupport() {

  @Test
  fun getReview() {
    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/getReviewDetails_result.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val review = sut.send(GetReviewDetailsCommand(ReviewId("test-pj", "review-1")))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), `is`("http://testserver/~rpc/getReviewDetails"))
    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("projectId", equalTo("test-pj")),
            hasJsonPath("reviewId", equalTo("review-1"))
        )
    )

    assertThat(review, `is`(
        ReviewDescriptor(
            review = ReviewId("test-pj", "TEST-1"),
            title = "レビュー",
            participants = listOf(ParticipantInReview("7107595a-cb57-4139-a1eb-76afa61668ad", RoleInReviewEnum.Author, ParticipantStateEnum.Unread)),
            state = ReviewStateEnum.Open,
            isUnread = null,
            priority = null,
            branch = "feature/1",
            issue = null,
            isRemoved = false,
            createdBy = "7107595a-cb57-4139-a1eb-76afa61668ad",
            createdAt = 1473230747402,
            updatedAt = 1473230747403,
            completionRate = CompletionRate(0, 0)
        )
    ))
  }
}

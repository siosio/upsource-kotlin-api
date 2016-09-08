package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class CreateReviewTest : UpsourceApiTestSupport() {
  @Test
  fun createReview() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/createReview_result.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val review = sut.send(CreateReviewCommand(CreateReviewRequest(
        projectId = "test-pj",
        title = "レビュー",
        branch = "feature/1")))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), `is`("http://testserver/~rpc/createReview"))
    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("projectId", equalTo("test-pj")),
            hasJsonPath("title", equalTo("レビュー")),
            hasJsonPath("branch", equalTo("feature/1"))
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

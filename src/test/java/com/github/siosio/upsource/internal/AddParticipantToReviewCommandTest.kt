package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class AddParticipantToReviewCommandTest : UpsourceApiTestSupport() {

  @Test
  fun addParticipantToReview() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/voidMessageResult.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    sut.send(AddParticipantToReviewCommand(
        ParticipantInReviewRequest(ReviewId("prj", "review-1"), ParticipantInReview("user1", RoleInReviewEnum.Reviewer))))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), `is`("http://testserver/~rpc/addParticipantToReview"))
    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("reviewId.projectId", equalTo("prj")),
            hasJsonPath("reviewId.reviewId", equalTo("review-1")),
            hasJsonPath("participant.userId", equalTo("user1")),
            hasJsonPath("participant.role", equalTo(2)),
            hasNoJsonPath("participant.state")
        )
    )
  }
}

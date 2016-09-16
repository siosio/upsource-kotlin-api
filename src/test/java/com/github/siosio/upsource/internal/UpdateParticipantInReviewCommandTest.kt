package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class UpdateParticipantInReviewCommandTest : UpsourceApiTestSupport() {

  @Test
  fun updateParticipantInReview() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/voidMessageResult.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    sut.send(UpdateParticipantInReviewCommand(
        UpdateParticipantInReviewRequest(ReviewId("prj", "review-1"), ParticipantStateEnum.Accepted)))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), `is`("http://testserver/~rpc/updateParticipantInReview"))
    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("reviewId.projectId", equalTo("prj")),
            hasJsonPath("reviewId.reviewId", equalTo("review-1")),
            hasJsonPath("state", equalTo(3))
        )
    )
  }

}

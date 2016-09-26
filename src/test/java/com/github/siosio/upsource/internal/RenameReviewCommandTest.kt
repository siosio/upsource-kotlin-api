package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class RenameReviewCommandTest : UpsourceApiTestSupport() {

  @Test
  fun renameTitle() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/renameReviewResult.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val result = sut.send(RenameReviewCommand(RenameReviewRequest(ReviewId("demo", "demo-1"), "新しいタイトル")))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), `is`("http://testserver/~rpc/renameReview"))
    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("reviewId.projectId", equalTo("demo")),
            hasJsonPath("reviewId.reviewId", equalTo("demo-1")),
            hasJsonPath("text", equalTo("新しいタイトル"))
        )
    )

    assertThat(result, `is`(RenameReviewResponse(1)))
  }
}
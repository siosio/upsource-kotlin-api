package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class RemoveReviewCommandTest : UpsourceApiTestSupport() {
  @Test
  fun removeReview() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/voidMessageResult.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val result = sut.send(RemoveReviewCommand(ReviewId("demo", "demo-2")))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), `is`("http://testserver/~rpc/removeReview"))
    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("projectId", equalTo("demo")),
            hasJsonPath("reviewId", equalTo("demo-2"))
        )
    )

    assertThat(result, `is`(instanceOf(VoidMessage::class.java)))

  }
}

package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class DeleteProjectCommandTest : UpsourceApiTestSupport() {
  @Test
  fun deleteProject() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/deleteProject_result.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val voidMessage = sut.send(DeleteProjectCommand(ProjectId("kotlin-sql")))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), CoreMatchers.`is`("http://testserver/~rpc/deleteProject"))

    assertThat(voidMessage, CoreMatchers.`is`(VoidMessage()))

    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("projectId", equalTo("kotlin-sql"))
        )
    )
  }

}

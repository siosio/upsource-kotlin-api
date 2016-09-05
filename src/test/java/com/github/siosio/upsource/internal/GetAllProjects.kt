package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import org.apache.http.*
import org.apache.http.client.methods.*
import org.apache.http.message.*
import org.hamcrest.*
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.*
import org.mockito.*
import org.mockito.ArgumentCaptor.*

internal class GetAllProjects : UpsourceApiTestSupport() {

  @Test
  fun testGetAllProjects() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/getAllProject_result.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val sut = UpsourceApi("http://testserver", UpsourceAccount("user", "password"), mockHttpClient)
    val project = sut.send(GetAllProjectCommand())

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), CoreMatchers.`is`("http://testserver/~rpc/getAllProjects"))

    assertThat(project.project, Matchers.contains(
        Project(
            "sample-project",
            "サンプルプロジェクト",
            "123321",
            false,
            1472252558519,
            "siosio"
        ),
        Project(
            "project2",
            "プロジェクト2",
            "abcabc",
            true
        )
    ))
  }
}

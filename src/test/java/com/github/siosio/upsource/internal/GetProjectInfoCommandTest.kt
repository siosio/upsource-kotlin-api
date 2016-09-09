package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import org.apache.http.*
import org.apache.http.client.methods.*
import org.apache.http.message.*
import org.hamcrest.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class GetProjectInfoCommandTest : UpsourceApiTestSupport() {

  @Test
  fun testGetProjectInfo() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/getProjectInfo_result.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val sut = UpsourceApi("http://testserver", UpsourceAccount("user", "password"), mockHttpClient)
    val project = sut.send(GetProjectInfoCommand("sample-project"))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), CoreMatchers.`is`("http://testserver/~rpc/getProjectInfo"))

    assertThat(project, CoreMatchers.`is`(
        ProjectInfo(
            projectId = "sample-project",
            projectName = "サンプルプロジェクト",
            headHash = "abc123",
            codeReviewIdPattern = "sample-{}",
            externalLinks = listOf(
                ExternalLink("http://hoge.com", "issue-"),
                ExternalLink("http://hoge.com", "hotfix-")
            ),
            issueTrackerConnections = listOf(
                ExternalLink("http://hogehoge.com", "hogehoge:")
            ),
            projectModelType = "Gradle",
            defaultEffectiveCharset = "utf-8",
            defaultBranch = "develop",
            isConnectedToGithub = false
        )
    ))
  }
}

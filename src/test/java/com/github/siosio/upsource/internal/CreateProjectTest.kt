package com.github.siosio.upsource.internal

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.jayway.jsonpath.matchers.JsonPathMatchers.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.*

internal class CreateProjectTest : UpsourceApiTestSupport() {

  @Test
  fun createProject() {

    // -------------------------------------------------- setup
    Mockito.`when`(mockResponse.entity).thenReturn(
        TestData("testdata/upsourceapi/createProject_result.json").getStringEntity()
    )

    // -------------------------------------------------- execute
    val voidMessage = sut.send(CreateProjectCommand(
        CreateProjectRequest("kotlin-sql", ProjectSettings(
            "kotlin-sql-project",
            Vcs.git.value,
            600L,
            ProjectModel(
                ProjectType.Gradle.value
            ),
            "kot-{}"
        ))
    ))

    // -------------------------------------------------- assert
    assertThat(httpPost.value.uri.toASCIIString(), CoreMatchers.`is`("http://testserver/~rpc/createProject"))

    assertThat(voidMessage, CoreMatchers.`is`(VoidMessage()))

    assertThat(httpPost.value.entity.content.readText(),
        allOf(
            hasJsonPath("newProjectId", equalTo("kotlin-sql")),
            hasJsonPath("settings.projectName", equalTo("kotlin-sql-project")),
            hasJsonPath("settings.vcsSettings", equalTo("git")),
            hasJsonPath("settings.checkIntervalSeconds", equalTo(600)),
            hasJsonPath("settings.projectModel.type", equalTo("gradle")),
            hasJsonPath("settings.codeReviewIdPattern", equalTo("kot-{}")),
            hasNoJsonPath("settings.issueTrackerConnections")
        )
    )
  }
}


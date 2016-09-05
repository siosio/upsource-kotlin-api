package com.github.siosio.upsource.integration

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import org.junit.*


class IntegrationTest {

  val sut = UpsourceClient("http://localhost:8080/", "siosio", "siosio")

  @Test
  fun getAllProject() {
    sut.getProjectManager().getAllProjects().forEach {
      println(it)
    }

    sut.getProjectManager().getAllProjects() {
      println("it = ${it}")
    }
  }

  @Test
  fun getProjectInfo() {
    val projectManager = sut.getProjectManager()
    projectManager.getAllProjects {
      val projectInfo = projectManager.getProjectInfo(it.projectId)
      println("projectInfo = $projectInfo")
    }
  }

  @Test
  fun createProject() {
    sut.getProjectManager().createProject(
        projectId = "kotlin-sql",
        projectName = "kotlinのSQL",
        projectType = ProjectType.Gradle,
        pathToModel = "src",
        codeReviewIdPattern = "kot-{}",
        vcs = {
          +repository(id = "kotlin-sql", vcs = Vcs.git, url = "https://github.com/siosio/kotlin-sql")
        },
        checkIntervalSeconds = 600,
        runInspections = false,
        links = {
          +link("https://github.com/siosio/kotlin-sql/issues/{}", "#{}")
        },
        createUserGroups = true,
        defaultEncoding = "utf-8",
        defaultBranch = "master"
    )
  }
}

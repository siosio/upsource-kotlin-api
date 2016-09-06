package com.github.siosio.upsource.integration

import com.github.siosio.upsource.*
import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.exception.*
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import java.util.concurrent.*


class IntegrationTest {

  val sut = UpsourceClient("http://localhost:8080/", "siosio", "siosio")

  @Test
  fun getAllProject() {
    sut.project {
      allProjects {
        println("it = ${it}")
      }
    }
  }

  @Test
  fun createProject() {
    sut.project {
      +project(
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

      for (i in (1..5)) {
        try {
          val project = this["kotlin-sql"]
          assertThat(project.projectId, `is`("kotlin-sql"))
          break
        } catch (e: ProjectNotFoundException) {
          println("e.message = ${e.message}")
          TimeUnit.SECONDS.sleep(5)
        }
      }


      -"kotlin-sql"
    }
  }
}

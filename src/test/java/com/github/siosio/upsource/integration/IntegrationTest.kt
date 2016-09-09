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

  @Test
  fun createReview() {
    sut.project {
      val review = +review(
          projectId = "demo",
          title = "Hello Kotlinをば",
          branch = "feature/2"
      )
      println("----------------------------------------------------------------------------------------------------")
      println(review)
      println("----------------------------------------------------------------------------------------------------")

      assertThat(review.branch, `is`("feature/2"))

      review(review.projectId, review.reviewId) {
        +reviewer("81db1f0d-bcb2-4ae4-9174-08fff2fc7a4f")
        +reviewer("8a4f008c-ef07-4d2a-91d1-58324e71b107")
      }

      println("******************** delete review: ${review.reviewId}")
      -review(review.projectId, review.reviewId)
    }
  }
}

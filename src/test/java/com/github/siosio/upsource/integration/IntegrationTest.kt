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
    sut.project {
      val project = get("domasupport")
      println("project = ${project}")

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
    sut.project("demo") {

      -review("DM-CR-37")

      val reviewId = (+review(
          title = "Hello Kotlinをば",
          branch = "feature/2"
      ) {

        assertThat(branch, `is`("feature/2"))

        +reviewer("81db1f0d-bcb2-4ae4-9174-08fff2fc7a4f")
        +reviewer("8a4f008c-ef07-4d2a-91d1-58324e71b107")

        +watcher("0aa10d06-13f1-4f96-bfb4-789bb2041571")

      }).reviewId

      UpsourceClient("http://localhost:8080/", "reviewer1", "reviewer1").project("demo") {
        review(reviewId) {
          title("Hello Kotlin( ･ิω･ิ)")
          state(ParticipantStateEnum.Accepted)
        }
      }

      review(reviewId) {
        assertThat(this.participants?.find { it.userId == "81db1f0d-bcb2-4ae4-9174-08fff2fc7a4f" }?.state, `is`(ParticipantStateEnum.Accepted))
        -reviewer("8a4f008c-ef07-4d2a-91d1-58324e71b107")
        -watcher("0aa10d06-13f1-4f96-bfb4-789bb2041571")
      }

      val review = review(reviewId)

      println("----------------------------------------------------------------------------------------------------")
      println(review)
      println("----------------------------------------------------------------------------------------------------")

      println("******************** delete review: ${review.reviewId}")
      -review(reviewId)
    }
  }
}

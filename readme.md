# Upsource Client

## Sample Code

### get all project
```kotlin
val upsource = UpsourceClient("http://localhost:8080/", "siosio", "password")


upsource.project {
  allProjects {
    println("it = ${it}")
  }
}
```

### create project
```kotlin
val usource = UpsourceClient("http://localhost:8080/", "siosio", "password")

usource.project {
  +project(
    projectId = "kotlin-sql",
    projectName = "kotlinのSQL",
    projectType = ProjectType.Gradle,
    codeReviewIdPattern = "kot-{}",
    vcs = {
      +repository(id = "kotlin-sql", vcs = Vcs.git, url = "https://github.com/siosio/kotlin-sql")
    }
  )
}
```

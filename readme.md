# Upsource Client

## Sample Code

### get all project
```kotlin
val client = UpsourceClient("http://localhost:8080/", "siosio", "password")

client.projectManager().allProjects {
  println("it = ${it}")
}
```

### create project
```kotlin
val client = UpsourceClient("http://localhost:8080/", "siosio", "password")

client.projectManager() {
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
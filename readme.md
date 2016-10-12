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

### create review/delete review
```kotlin
val usource = UpsourceClient("http://localhost:8080/", "siosio", "password")

// create review
usource.project("demo") {
  +review(
      title = "Hello Kotlinをば",
      branch = "feature/2"
  ) {
    // add reviewer and watcher
    +reviewer("81db1f0d-bcb2-4ae4-9174-08fff2fc7a4f")
    +reviewer("8a4f008c-ef07-4d2a-91d1-58324e71b107")
    +watcher("0aa10d06-13f1-4f96-bfb4-789bb2041571")
  }
}

// delete review
upsource.project("demo") {
  -review("demo-1")
}
```

### add reviewer(watcher)/remove reviewer(watcher)
```kotliin
val usource = UpsourceClient("http://localhost:8080/", "siosio", "password")
upsourc.project("demo") {
  review("demo-1") {
    // add reviewer and watcher
    +reviewer("81db1f0d-bcb2-4ae4-9174-08fff2fc7a4f")
    +reviewer("8a4f008c-ef07-4d2a-91d1-58324e71b107")
    +watcher("0aa10d06-13f1-4f96-bfb4-789bb2041571")
    
    // delete reviewer and watcher
    -reviewer("8a4f008c-ef07-4d2a-91d1-58324e71b107")
    -watcher("0aa10d06-13f1-4f96-bfb4-789bb2041571")
  }
}
```

### change review status

```kotliin
val usource = UpsourceClient("http://localhost:8080/", "siosio", "password")
upsourc.project("demo") {
  review("demo-1") {
    // rename review
    title("new revew title")
  
    // change status
    state(ParticipantStateEnum.Accepted)
  }
}
```

package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.internal.*
import org.apache.http.impl.client.*

class UpsourceClient(
    server: String,
    user: String,
    password: String
) {

  private val upsourceApi: UpsourceApi

  init {
    val upsourceAccount: UpsourceAccount = UpsourceAccount(user, password)
    val httpClient: CloseableHttpClient = HttpClients.createDefault()
    upsourceApi = UpsourceApi(server, upsourceAccount, httpClient)
  }

  fun project(init: ProjectManager.() -> Unit): ProjectManager {
    val projectManager = ProjectManager(upsourceApi)
    projectManager.init()
    return projectManager
  }

  fun review(init: ReviewManager.() -> Unit): ReviewManager {
    val review = ReviewManager(upsourceApi)
    review.init()
    return review
  }
}


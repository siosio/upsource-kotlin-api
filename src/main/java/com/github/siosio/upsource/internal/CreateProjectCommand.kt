package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class CreateProjectCommand(val createProjectRequest: CreateProjectRequest) : Command<CreateProjectRequest, VoidMessage> {

  override val name: String = "createProject"
  override val responseType: Class<VoidMessage> = VoidMessage::class.java

  override fun getRequest(): CreateProjectRequest = createProjectRequest
}

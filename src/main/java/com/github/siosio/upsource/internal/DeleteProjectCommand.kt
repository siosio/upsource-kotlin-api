package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

class DeleteProjectCommand(private val projectId: ProjectId) : Command<ProjectId, VoidMessage> {

  override val name: String = "deleteProject"

  override val responseType: Class<VoidMessage> = VoidMessage::class.java

  override fun getRequest(): ProjectId = projectId
}
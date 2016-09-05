package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

internal class GetProjectInfoCommand(private val projectId: String) : Command<ProjectId, ProjectInfo> {

  override val name: String = "getProjectInfo"

  override val responseType: Class<ProjectInfo> = ProjectInfo::class.java

  override fun getRequest(): ProjectId {
    return ProjectId(projectId)
  }
}


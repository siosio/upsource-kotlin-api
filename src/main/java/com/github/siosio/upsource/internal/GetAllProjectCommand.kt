package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*

internal class GetAllProjectCommand() : Command<VoidMessage, ProjectInfoList> {

  companion object {
    private val voidMessage: VoidMessage = VoidMessage()
  }

  override val responseType: Class<ProjectInfoList> = ProjectInfoList::class.java

  override fun getRequest(): VoidMessage = voidMessage

  override val name: String = "getAllProjects"

}

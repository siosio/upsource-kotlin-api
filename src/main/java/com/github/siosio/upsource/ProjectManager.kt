package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.internal.*

class ProjectManager internal constructor(private val upsourceApi: UpsourceApi) {

  fun getAllProjects(): List<Project> {
    val projectInfoList = upsourceApi.send(GetAllProjectCommand())

    return projectInfoList.project
  }

  fun getAllProjects(block: (Project) -> Unit) {
    getAllProjects().forEach {
      block(it)
    }
  }

  fun getProjectInfo(projectId: String): ProjectInfo {
    return upsourceApi.send(GetProjectInfoCommand(projectId))
  }

  fun createProject(
      projectId: String,
      projectName: String,
      projectType: ProjectType,
      pathToModel: String? = null,
      defaultJdkId: String? = null,
      codeReviewIdPattern: String,
      checkIntervalSeconds: Long = 300L,
      vcs: VcsSettings.() -> Unit,
      runInspections: Boolean = true,
      mavenSettings: String? = null,
      mavenProfiles: String? = null,
      mavenJdkName: String? = null,
      links: ExternalLinks.() -> Unit,
      createUserGroups: Boolean? = null,
      userManagementUrl: String? = null,
      defaultEncoding: String? = null,
      defaultBranch: String? = null,
      autoAddRevisionsToReview: Boolean? = null
  ) {

    val vcsSettings = VcsSettings()
    vcsSettings.vcs()
    val externalLinks = ExternalLinks()
    externalLinks.links()

    upsourceApi.send(CreateProjectCommand(
        CreateProjectRequest(
            newProjectId = projectId,
            settings = ProjectSettings(
                projectName = projectName,
                codeReviewIdPattern = codeReviewIdPattern,
                checkIntervalSeconds = checkIntervalSeconds,
                vcsSettings = toJson(mapOf("mappings" to vcsSettings.vcsSettings)),
                runInspections = runInspections,
                projectModel = ProjectModel(projectType.value, pathToModel, defaultJdkId),
                mavenSettings = mavenSettings,
                mavenProfiles = mavenProfiles,
                mavenJdkName = mavenJdkName,
                externalLinks = externalLinks.externalLinks,
                createUserGroups = createUserGroups,
                userManagementUrl = userManagementUrl,
                defaultBranch = defaultBranch,
                defaultEncoding = defaultEncoding,
                autoAddRevisionsToReview = autoAddRevisionsToReview
            )
        )
    ))
  }

  private fun toJson(obj: Any): String {
    return ObjectMapperCreator.create().writeValueAsString(obj)
  }
}

class VcsSettings {

  val vcsSettings: MutableList<VcsSetting> = mutableListOf()

  fun repository(id: String, vcs: Vcs, url: String) = VcsSetting(id, vcs.value, url)

  operator fun VcsSetting.unaryPlus() {
    vcsSettings.add(this)
  }
}

data class VcsSetting(val id: String, val vcs: String, val url: String)

class ExternalLinks {
  val externalLinks: MutableList<ExternalLink> = mutableListOf()

  fun link(url: String, prefix: String) = ExternalLink(url, prefix)

  operator fun ExternalLink.unaryPlus() {
    externalLinks.add(this)
  }
}


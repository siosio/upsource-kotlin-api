package com.github.siosio.upsource

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.exception.*
import com.github.siosio.upsource.internal.*

class ProjectManager internal constructor(private val upsourceApi: UpsourceApi) {

  fun allProjects(): List<Project> {
    val projectInfoList = upsourceApi.send(GetAllProjectCommand())
    return projectInfoList.project
  }

  fun allProjects(block: (Project) -> Unit) {
    allProjects().forEach {
      block(it)
    }
  }

  /**
   * get project
   */
  operator fun get(projectId: String) = getProjectInfo(projectId)

  fun getProjectInfo(projectId: String): ProjectInfo {
    return upsourceApi.send(GetProjectInfoCommand(projectId)) ?: throw ProjectNotFoundException(projectId)
  }

  /**
   * create project
   */
  operator fun CreateProjectRequest.unaryPlus() {
    upsourceApi.send(CreateProjectCommand(this))
  }

  fun project(
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
      links: ExternalLinks.() -> Unit = {},
      createUserGroups: Boolean? = null,
      userManagementUrl: String? = null,
      defaultEncoding: String? = null,
      defaultBranch: String? = null,
      autoAddRevisionsToReview: Boolean? = null
  ): CreateProjectRequest {

    val vcsSettings = VcsSettings()
    vcsSettings.vcs()
    val externalLinks = ExternalLinks()
    externalLinks.links()

    return CreateProjectRequest(
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
  }

  /**
   * delete project
   */
  operator fun String.unaryMinus() {
    upsourceApi.send(DeleteProjectCommand(ProjectId(this)))
  }

  /**
   * create review
   */
  operator fun CreateReviewRequest.unaryPlus(): ReviewDescriptor {
    return upsourceApi.send(CreateReviewCommand(this))
  }

  fun review(projectId: String, title: String, revisions: List<String> = emptyList(), branch: String? = null): CreateReviewRequest {
    return CreateReviewRequest(projectId, title, revisions, branch)
  }

  /**
   * delete review
   */
  operator fun ReviewId.unaryMinus() {
    upsourceApi.send(RemoveReviewCommand(this))
  }

  private fun toJson(obj: Any): String {
    return ObjectMapperCreator.create().writeValueAsString(obj)
  }

  fun review(projectId: String, reviewId: String, init: ReviewManagerWithReview.() -> Unit) {
    val review = ReviewManagerWithReview(ReviewId(projectId, reviewId), upsourceApi)
    review.init()
  }

  fun review(projectId: String, reviewId:String) = ReviewId(projectId, reviewId)
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


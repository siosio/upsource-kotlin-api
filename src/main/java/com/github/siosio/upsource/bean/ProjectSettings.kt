package com.github.siosio.upsource.bean

data class ProjectSettings(
    val projectName: String,
    val vcsSettings: String? = null,
    val checkIntervalSeconds: Long? = null,
    val projectModel: ProjectModel,
    val codeReviewIdPattern: String,
    val runInspections: Boolean? = null,
    val mavenSettings: String? = null,
    val mavenProfiles: String? = null,
    val mavenJdkName: String? = null,
    val externalLinks: List<ExternalLink> = emptyList(),
    val createUserGroups: Boolean? = null,
    val issueTrackerProviderSettings: IssueTrackerProviderSettings? = null,
    val userManagementUrl: String? = null,
    val defaultEncoding: String? = null,
    val defaultBranch: String? = null,
    val autoAddRevisionsToReview: Boolean? = null,
    val ignoredFilesInReview: List<String> = emptyList()
)


package com.github.siosio.upsource.bean

data class ProjectInfo(
    val projectId: String,
    val projectName: String,
    val headHash: String,
    val codeReviewIdPattern: String,
    val externalLinks: List<ExternalLink> = emptyList(),
    val issueTrackerConnections: List<ExternalLink>? = emptyList(),
    val projectModelType: String,
    val defaultEffectiveCharset: String,
    val defaultBranch: String? = null,
    val issueTrackerDetails: IssueTrackerProjectDetails? = null,
    val isConnectedToGithub: Boolean
)


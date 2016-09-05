package com.github.siosio.upsource.bean

data class IssueTrackerProviderSettings(
    val providerKey: String,
    val settings: List<IssueTrackerProviderSetting>
)

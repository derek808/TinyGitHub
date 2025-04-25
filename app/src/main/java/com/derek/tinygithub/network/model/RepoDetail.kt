package com.derek.tinygithub.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepoDetail>
)

@Serializable
data class RepoDetail(
    val id: Int,
    val name: String,
    val full_name: String,
    val description: String?,
    val stargazers_count: Int,
    val forks_count: Int,
    val language: String?
)
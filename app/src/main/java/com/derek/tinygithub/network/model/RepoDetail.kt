package com.derek.tinygithub.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    val items: List<RepoDetail>
)

@Serializable
data class RepoDetail(
    val id: Int,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val description: String?,
    @SerialName("stargazers_count") val starsCount: Int,
    @SerialName("forks_count") val forksCount: Int,
    val language: String?,
    val owner: Owner?,
)

@Serializable
data class Owner(
    val login: String,
    val id: Int,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("html_url") val htmlUrl: String
)
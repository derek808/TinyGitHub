package com.derek.tinygithub.ui

import com.derek.tinygithub.network.model.RepoDetail

/**
 *
 * @author: derekzchen@2025/4/26
 */
sealed interface RepoUiState {
    data class Success(val repos: List<RepoDetail>) : RepoUiState
    data class Error(val message: String? = "") : RepoUiState
    data object Loading : RepoUiState
}
package com.derek.tinygithub.data

import com.derek.tinygithub.ui.RepoUiState
import kotlinx.coroutines.flow.Flow

interface RepoRepository {

    fun fetchPopularReposFlow(): Flow<RepoUiState>

}
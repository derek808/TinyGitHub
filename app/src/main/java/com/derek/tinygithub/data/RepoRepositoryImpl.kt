package com.derek.tinygithub.data

import com.derek.tinygithub.network.model.RepoDetail
import com.derek.tinygithub.ui.RepoUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 *
 * @author: derekzchen@2025/4/26
 */
class RepoRepositoryImpl : RepoRepository {

    private val repoDataSource = RemoteRepoDataSource

    override fun fetchPopularReposFlow(): Flow<RepoUiState> {
        return flow<RepoUiState> {
            val repos = fetchPopularRepos()
            emit(RepoUiState.Success(repos))
        }.catch { e ->
            emit(RepoUiState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun fetchPopularRepos(): List<RepoDetail> {
        return repoDataSource.fetchPopularRepos()
    }

}